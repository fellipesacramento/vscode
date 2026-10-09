package br.com.expressaovirtual.ofx;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class XlsOfx {

    // Ajuste se o CSV tiver outro separador ou formato de data
    private static final String SEPARATOR = ",";
    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter OFX_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Uso: java Csv2Ofx entrada.csv saida.ofx
     */
    public static void main(String[] args) throws Exception {
       
    	OfxBuilder ofx = new OfxBuilder();
        ExcelReader reader = new ExcelReader();
        List<OfxEntity> lista = reader.read("D:/banco/sodexo.xlsx");
        ofx.createOFX(lista);
        
        
        
    }

   
}

