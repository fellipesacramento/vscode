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
import java.util.List;
import java.util.UUID;

public class OfxBuilder {

    // Ajuste se o CSV tiver outro separador ou formato de data
    private static final String SEPARATOR = ",";
    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter OFX_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Uso: java Csv2Ofx entrada.csv saida.ofx
     */
    public void createOFX(List<OfxEntity> lista) throws Exception {       
       
        Path ofxFile = Path.of("D:/banco/saida.ofx");

        // Cabeçalho fixo OFX
        StringBuilder ofx = new StringBuilder("""
            OFXHEADER:100
            DATA:OFXSGML
            VERSION:102
            SECURITY:NONE
            ENCODING:UTF-8
            COMPRESSION:NONE
            OLDFILEUID:NONE
            NEWFILEUID:NONE

            <OFX>
              <SIGNONMSGSRSV1>
                <SONRS>
                  <STATUS><CODE>0</CODE><SEVERITY>INFO</SEVERITY></STATUS>
                  <DTSERVER>%s</DTSERVER>
                  <LANGUAGE>POR</LANGUAGE>
                </SONRS>
              </SIGNONMSGSRSV1>
              <BANKMSGSRSV1>
                <STMTTRNRS>
                  <TRNUID>%s</TRNUID>
                  <STATUS><CODE>0</CODE><SEVERITY>INFO</SEVERITY></STATUS>
                  <STMTRS>
                    <CURDEF>BRL</CURDEF>
                    <BANKACCTFROM><ACCTID>000000000</ACCTID><ACCTTYPE>CHECKING</ACCTTYPE></BANKACCTFROM>
                    <BANKTRANLIST>
            """.formatted(now(), UUID.randomUUID()));       
         
          for (OfxEntity registro : lista) {	              

                LocalDate date = registro.getData();
                String name = registro.getNome().replace("\"", "");
                String categoria = registro.getCategoria().replace("\"", "");
                String tipo = registro.getTipo().replace("\"", "");
                //String strValor = cols[4].trim()+","+cols[5].trim(); 
                               
                BigDecimal valor = registro.getValor();
              
                String trnType;
                if(tipo.toLowerCase().contains("compra") || tipo.toLowerCase().contains("parcela")) {
                	  trnType = "DEBIT";
                }else {
                	trnType = "CREDIT";
                }
               
                if ("DEBIT".equals(trnType)) valor = valor.negate();

                ofx.append("""
                          <STMTTRN>
                            <TRNTYPE>%s</TRNTYPE>
                            <DTPOSTED>%s000000</DTPOSTED>
                            <TRNAMT>%s</TRNAMT>
                            <FITID>%s</FITID>
                            <NAME>%s</NAME>
                            <MEMO>%s | %s
                          </STMTTRN>
                        """.formatted(trnType,
                                       date.format(OFX_DATE),
                                       valor,
                                       UUID.randomUUID(),
                                       sanitize(tipo),
                                       sanitize(name),
                                       sanitize(categoria+" - "+tipo)));
            }
        

        // Fecha a árvore OFX
        ofx.append("""
                    </BANKTRANLIST>
                  </STMTRS>
                </STMTTRNRS>
              </BANKMSGSRSV1>
            </OFX>
            """);

        // Grava em disco
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ofxFile.toFile(), StandardCharsets.UTF_8))) {
            bw.write(ofx.toString());
        }
        System.out.println("OFX gerado em: " + ofxFile.toAbsolutePath());
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

