/**
 * 
 */
package br.gov.dataprev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/** 
 * @author rodrigo.beninca
 *
 */
public class Indicadores {
	
	
	private static FileWriter csvInvalidos;
	
	
	
	public static void main(String[] args) throws Exception {		
		csvInvalidos = new FileWriter("C:\\dev\\gate_dvts\\indicadores_projetos_alm_producao\\Qualifica��o de Dados - Relat�rios\\invalidos.csv");
		csvInvalidos.append("Project Area");
        csvInvalidos.append(';');
        csvInvalidos.append("N�mero DTP");
        csvInvalidos.append(';');
        csvInvalidos.append("Id Item Backlog");
        csvInvalidos.append(';');
        csvInvalidos.append("Nome Item Backlog");
        csvInvalidos.append('\n');  
		
        
        processaItensBacklog();		
	}
	
	
	/**
	 * Abrir o arquivo excel
	 * localizar a coluna do nome_projeto, itemBacklog e a coluna do NroDtp
	 * correr as linhas do excel e para cada NroDtp localizado (n�o estar em branco):
	 * 		add prefixo 'DTP.' se necess�rio
	 * 		colocar em upcase 
	 * 		ver se o nroDtp � v�lido isNroDtpValido()
	 * 		imprimir o nome da PA e o Nro Dtp inv�lidos
	 * @throws IOException 
	 */
	private static void processaItensBacklog () throws IOException {
		//abre o arquivo e pega a sheet dos dados excel
		InputStream arquivoExcel = new FileInputStream(new File("C:\\dev\\gate_dvts\\indicadores_projetos_alm_producao\\Qualifica��o de Dados - Relat�rios\\q_Rela��o_de__Nro_DTP__e__Liberado_em__por_Project_Area.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(arquivoExcel);
        HSSFSheet sheet = wb.getSheetAt(1);        
        
        //totalizadores
        int countInvalido = 0;
        int countTotalViaIB = 0;
        
        //pega todos os NroDtp disponibilizados no Painel (via planilha excel exportada)
        ArrayList<String> numerosDtpCadstradosClarity = retornaNumerosDtpCadastradosClarity();        

        //loop para identificar Item de Backlog com NroDtp n�o disponibilizados na planilha excel via Painel 
        Iterator<Row> linhas = sheet.rowIterator();
        linhas.next(); //os NroDTP come�am na linha 2 (ap�s o cabe�alho)       
        while (linhas.hasNext()) {
        	HSSFRow linha = (HSSFRow) linhas.next();        	            
            
        	String nroDtp = formataNroDtp( linha.getCell(5).getStringCellValue() );
            String pa = linha.getCell(0).getStringCellValue();
            String ib = linha.getCell(1).getStringCellValue();
            String nomeIB = linha.getCell(2).getStringCellValue();
            
            if ( !nroDtp.isEmpty() ){
            	countTotalViaIB++;
            	if (!isNumeroDtpValido(nroDtp, numerosDtpCadstradosClarity)) {
                	populaCsvInvalidos(nroDtp, pa, ib, nomeIB);
                	countInvalido++;
                	//System.out.println("Rows: "+ worksheetInvalidos.getSheet("Invalidos").getLastRowNum());
                }
            }                                
        }//loop while        
        
        //print de totalizadores
        System.out.println("Total de NroDTP cadastrado nos IB (painel): " + countTotalViaIB);
        System.out.println("Total de NroDTP Inexistentes nos IB       : " + countInvalido);
		
        //salva o csv
        csvInvalidos.flush();
        csvInvalidos.close();
        
        //fecha o arquivo excel
        wb.close(); 
        arquivoExcel.close();		
	}

	/**
	 * adiciona os nrosDtp n�o localizados em um cvs
	 */
	private static void populaCsvInvalidos(String nroDtp, String pa, String ib, String nomeIB) throws IOException {
		System.out.println("NroDtp Inv�lido!: " + nroDtp + " PA: " + pa + " IB: " + ib + " Nome IB: " + nomeIB);
		
		if (csvInvalidos == null) 
			csvInvalidos = new FileWriter("C:\\dev\\gate_dvts\\indicadores_projetos_alm_producao\\Qualifica��o de Dados - Relat�rios\\invalidos.csv");

        csvInvalidos.append(pa);
        csvInvalidos.append(';');
        csvInvalidos.append(nroDtp);
        csvInvalidos.append(';');
        csvInvalidos.append(ib);
        csvInvalidos.append(';');
        csvInvalidos.append(nomeIB);
        csvInvalidos.append('\n');        		
	}

	
	/**
	 * abrir o arquivo excel
	 * identificar a coluna no NroDto
	 * Correr as linhas adicionando na Lista o NroDtp 
	 */
	private static ArrayList<String> retornaNumerosDtpCadastradosClarity() throws IOException {
		
		//array que manter� todos os NroDtp presentes na planilha excel exportada do Painel
		ArrayList<String> numerosDtpClarity = new ArrayList<String>();
		
		//Abre a planilha e aba do arquivo excel que cont�m os NroDtp
		InputStream arquivoExcel = new FileInputStream(new File("C:\\dev\\gate_dvts\\indicadores_projetos_alm_producao\\Qualifica��o de Dados - Relat�rios\\NumerosDTP_do_Painel.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(arquivoExcel);
        HSSFSheet sheet = wb.getSheetAt(0);

        Iterator<Row> linhas = sheet.rowIterator();
        linhas.next();linhas.next(); //os NroDTP come�am na linha 3 (cabe�alho + linha em branco)
        
        while (linhas.hasNext()) {
        	HSSFRow linha = (HSSFRow) linhas.next();
        	HSSFCell celula = linha.getCell(0); //a primeira c�lula tem o NroDTP
            String nroDtp = celula.getStringCellValue();              
            numerosDtpClarity.add(nroDtp);               
        }
		
        //fecha o arquivo
        wb.close();
        arquivoExcel.close();
        
        //imprime o totalizador
        System.out.println("total de NroDtp via planilha do painel: " + numerosDtpClarity.size());
		
        return numerosDtpClarity;
	}
	
	
	/*
	 * Adiciona o prefixo 'DTP.' ao N�mero DTP se esse n�o estiver em branco
	 */
	private static String formataNroDtp(String nroDtp) {
		CharSequence c = "DTP.";		
		
		if (!nroDtp.contains(c)) {
			if (!nroDtp.isEmpty()) {
				nroDtp = "DTP."+nroDtp;				
				//System.out.println("CORRIGIDO!!!!   NroDtp: " + nroDtp);	    	
			}
		}

		return nroDtp;
	}
	
	/**
	 * verifica se o NroDtp existe na rela��o de NrosDtpCadastrados
	 */
	public static boolean isNumeroDtpValido(String nroDtp, ArrayList<String> numerosDtpCadstradosClarity) {
		boolean existe = false;
				
		Iterator<String> i = numerosDtpCadstradosClarity.iterator();		
		while (i.hasNext()) {
			String nroClarity = i.next();
			if (nroDtp.equals(nroClarity)) {
				existe = true;
			}
		} //loop while		
		
		return existe;		
	}

}
