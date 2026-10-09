package br.com.expressaovirtual.ofx;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcelReader {

    // Nomes das colunas (comparação case-insensitive)
    private static final String COL_DATA      = "data";
    private static final String COL_HORA      = "hora";           // não mapeia em OfxEntity
    private static final String COL_NOME      = "nome";
    private static final String COL_VALOR     = "valor";
    private static final String COL_CATEGORIA = "categoria";
    private static final String COL_TIPO      = "tipo";

    private static final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Lê a primeira aba do XLSX e retorna a lista de OfxEntity */
    public List<OfxEntity> read(String xlsxPath) throws IOException {
        List<OfxEntity> items = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(xlsxPath);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) return items;

            // Cabeçalho → índice por nome (lowercase)
            Map<String, Integer> col = readHeaderIndexes(sheet.getRow(0));
            validateRequiredColumns(col, Arrays.asList(COL_DATA, COL_NOME, COL_VALOR));

            // Linhas de dados
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                // Strings cruas
                String rawData   = getCellString(row.getCell(col.get(COL_DATA))).trim();
                String rawDesc   = getCellString(row.getCell(col.get(COL_NOME))).trim();
                String rawValor  = getCellString(row.getCell(col.get(COL_VALOR))).trim();

                if (rawData.isEmpty() && rawDesc.isEmpty() && rawValor.isEmpty()) continue;

                LocalDate data = parseData(row.getCell(col.get(COL_DATA)), rawData);
                BigDecimal valor = parseValor(rawValor);

                // Categoria e Tipo: exatamente como no Excel, se as colunas existirem
                String categoria = getOptional(col, COL_CATEGORIA)
                        .map(i -> getCellString(row.getCell(i)).trim())
                        .orElse(null);
                String tipo = getOptional(col, COL_TIPO)
                        .map(i -> getCellString(row.getCell(i)).trim())
                        .orElse(null);

                OfxEntity ent = new OfxEntity();
                ent.setData(data);
                ent.setNome(rawDesc);
                ent.setValor(valor);
                ent.setCategoria(isEmpty(categoria) ? null : categoria);
                ent.setTipo(isEmpty(tipo) ? null : tipo);

                items.add(ent);
            }
        }
        return items;
    }

    // ------------ utilitários ------------

    private static Map<String, Integer> readHeaderIndexes(Row header) {
        Map<String, Integer> colIndex = new HashMap<>();
        if (header == null) return colIndex;
        for (Cell c : header) {
            String name = getCellString(c).toLowerCase(Locale.ROOT).trim();
            if (!name.isEmpty()) colIndex.put(name, c.getColumnIndex());
        }
        return colIndex;
    }

    private static void validateRequiredColumns(Map<String, Integer> col, List<String> required) {
        List<String> missing = new ArrayList<>();
        for (String k : required) if (!col.containsKey(k)) missing.add(k);
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Colunas obrigatórias ausentes: " + missing);
        }
    }

    private static Optional<Integer> getOptional(Map<String, Integer> col, String key) {
        return col.containsKey(key) ? Optional.of(col.get(key)) : Optional.empty();
    }

    private static boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    LocalDate d = cell.getDateCellValue()
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return ISO.format(d);
                } else {
                    return new DecimalFormat("#.############")
                            .format(cell.getNumericCellValue());
                }
            case BOOLEAN: return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                try { return cell.getStringCellValue(); }
                catch (IllegalStateException e) {
                    return new DecimalFormat("#.############")
                            .format(cell.getNumericCellValue());
                }
            default: return "";
        }
    }

    private static LocalDate parseData(Cell cell, String raw) {
        // Data como serial do Excel
        if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String s = raw.trim();
        if (!s.isEmpty()) {
            try { return LocalDate.parse(s, ISO); } catch (Exception ignore) {}
            String[] fmts = {"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy"};
            for (String f : fmts) {
                try { return LocalDate.parse(s, DateTimeFormatter.ofPattern(f)); } catch (Exception ignore) {}
            }
        }
        return null;
    }

    private static BigDecimal parseValor(String raw) {
        if (raw == null) return BigDecimal.ZERO;
        String s = raw.trim().replace("R$", "").replace("\u00A0", " ").trim();

        // Tenta padrão BR (vírgula decimal)
        try {
            DecimalFormatSymbols br = new DecimalFormatSymbols(new Locale("pt", "BR"));
            br.setDecimalSeparator(',');
            br.setGroupingSeparator('.');
            DecimalFormat df = new DecimalFormat("#,##0.##", br);
            df.setParseBigDecimal(true);
            return (BigDecimal) df.parse(s);
        } catch (Exception ignore) {}

        // Fallback: ponto decimal
        try { return new BigDecimal(s.replace(",", ".")); }
        catch (Exception e) { return BigDecimal.ZERO; }
    }
}
