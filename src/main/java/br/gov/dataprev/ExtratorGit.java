/**
 * 
 */
package br.gov.dataprev;

/**
 * @author rodrigo.beninca
 *
 */
public class ExtratorGit {
	
	static ContadorGit contador = new ContadorGit();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		String repo = "C:\\dev\\eclipse\\workspace\\GitExtrator\\.git";
//		String tagA = "v1.0";
//		String tagB = "v1.1";

		/**
		 * Escoial-eventos
		 */
//		String repo = "C:\\dev\\eSocialEventos\\esocial-eventos-transformacao\\.git";
//		String tagA = "esocial-transformacao-producao-1.0.4-20170315";
//		String tagB = "esocial-transformacao-homologacao-1.0.5-20170614";	
		
		/**
		 * SIRC
		 */
//		String repo = "C:\\dev\\sirc\\sirc-ee-intranet\\.git";
//		String tagA = "v5.4.4";
//		String tagB = "HEAD";
				
//		String repo = "C:\\dev\\sirc\\sirc-ee-negocio\\.git";
//		String tagA = "3.4.5";
//		String tagB = "v3.6.0";
		
		
		/**
		 * BG INSS CONFIGURAÇÃO
		 */
		String repo = "C:\\dev\\bg-inss\\bg-inss-configuracao\\.git";
		String tagA = "446909784d1957de9d8ded18ed6af8bcf75c6964";
		String tagB = "bginss_configuracao_20171025_v001";	
		
		
		contador.linhasAlteradasEntreComiits(repo, tagA, tagB);		
	}
}
