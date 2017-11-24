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
		contador.linhasAlteradasEntreComiits("C:\\dev\\git\\aula\\local2\\.git", "9231c4fac757fd5b78be9b95369a06608488de48", "3d40968f236c20594b796842ab56fa3c8dcc124f");
		//contador.linhasDeCodigoNaRelease("C:\\dev\\git\\aula\\local2\\.git", "tag1");
		contador.salvarEmArquivo("sss");
	}
}
