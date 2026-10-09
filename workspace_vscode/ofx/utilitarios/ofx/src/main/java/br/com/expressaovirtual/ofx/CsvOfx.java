package br.com.expressaovirtual.ofx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvOfx {

    // Ajuste se o CSV tiver outro separador ou formato de data
    private static final String SEPARATOR = ",";
    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter OFX_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Uso: java Csv2Ofx entrada.csv saida.ofx
     */
    public static void main(String[] args) throws Exception {
        Integer count = 2;
        Path csvFile = Path.of("D:/banco/entrada.csv");
        List<OfxEntity> lista = new ArrayList<OfxEntity>();
        OfxBuilder ofx = new OfxBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile.toFile(), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // pula header
            while ((line = br.readLine()) != null) {
            	OfxEntity registro = new OfxEntity();
                String[] cols = line.split(SEPARATOR, -1);
                if (cols.length < 5) continue;   // ignora linhas incompletas

                LocalDate date = LocalDate.parse(cols[0].replace("\"", ""), CSV_DATE);
                registro.setData(date);
                
                String name = cols[1].replace("\"", "");
                registro.setNome(name);
                
                String categoria = cols[2].replace("\"", "");
                registro.setCategoria(categoria);
                
                String tipo = cols[3].replace("\"", "");
                registro.setTipo(tipo);
                
                String strValor = cols[4].trim()+","+cols[5].trim();                                
                BigDecimal valor =  parseValor(strValor);
                registro.setValor(valor);   
                
                count ++;
                lista.add(registro);           
                System.out.println(count); 
            }
            
            ofx.createOFX(lista);
            
        }catch (Exception e) {
			throw(e);
		}
    
    }

    /* ---------- auxiliares ---------- */
    private static String now() {
        return java.time.ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    private static BigDecimal parseValor(String raw) {
        String str = raw.replace("R$", "")
        		                  
                                 .replace("\"", "")
                                 .replace(".", "")
                                 .replace(",", ".")
                                 .replaceAll("^[\\s\\u00A0]+", "")
                                 .trim();
      String strValidada = str.trim().replace(" ", "");
      return new BigDecimal(str.trim());
    }
    private static String sanitize(String s) {
        return s.replaceAll("[&<>]", ""); // evita caracteres proibidos em SGML
    }
}

