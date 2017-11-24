package br.gov.dataprev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class ContadorGit {

	protected void linhasAlteradasEntreComiits(String repositorio, String commitA, String commitB) throws Exception {
		System.out.println("*******linhas alteradas entre commits*******");
		int linesAdded = 0;
		int linesDeleted = 0;
		int filesChanged = 0;		
	    
		File file = new File(repositorio);
		FileRepository repo = new FileRepository(file);
	    RevWalk rw = new RevWalk(repo);	    
	    
	    RevCommit commit = rw.parseCommit(repo.resolve(commitA)); // Any ref will work here (HEAD, a sha1, tag, branch)
	    //RevCommit parent = rw.parseCommit(commit.getParent(0).getId());
	    RevCommit parent = rw.parseCommit(repo.resolve(commitB));
	    
	    DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
	    df.setRepository(repo);
	    df.setDiffComparator(RawTextComparator.DEFAULT);
	    df.setDetectRenames(true);
	    
	    List<DiffEntry> diffs;
	    diffs = df.scan(parent.getTree(), commit.getTree());
	    filesChanged = diffs.size();
	    for (DiffEntry diff : diffs) {
	        for (Edit edit : df.toFileHeader(diff).toEditList()) {
	            linesDeleted += edit.getEndA() - edit.getBeginA();
	            linesAdded += edit.getEndB() - edit.getBeginB();
	        }
	    }
	    
	    //Nomes das tags/comitts usadas no cálculo
	    System.out.println("A: " + commitA );
	    System.out.println("B: " + commitB);	    
	    System.out.println("FilesChanged: " + filesChanged);
	    System.out.println("linesAdded: " + linesAdded);
	    System.out.println("linesDeleted: " + linesDeleted);	    
	    
	    //qdo se modifica uma linha existente é 1 add e 1 del, resultando em 0 na diferença e em 2 na |soma|	    
	    //Módulo da soma de linhas adiciondas e removidas
	    int somaLinesAlteradas = linesAdded+linesDeleted;
	    System.out.println("|Soma| de Linhas Alteradas (não entendo): " + somaLinesAlteradas);	    
	    //Subtração das linhas adicionadas e removidas, dando a diferença entre as duas tags
	    int diferencaAlteracoesEntreTags = linesAdded - linesDeleted;
	    
	    System.out.println("Diferença Alterações Entre Tags: " + diferencaAlteracoesEntreTags);	    
	    System.out.println("*******FIM - linhas alteradas entre commits*******");
	}
	

	public void salvarEmArquivo(String linha) throws FileNotFoundException {
		FileOutputStream arquivo = new FileOutputStream("arquivo.txt");
		
	}
	
	
//	protected int linhasDeCodigoNaRelease(String repositorio, String nomeRelease) throws Exception {
//	System.out.println("*******linhas de código em uma Release*******");
//	int linhasDeCodigo = -1;
//	
//	
//	File file = new File(repositorio);
//	FileRepository repo = new FileRepository(file);
//    RevWalk rw = new RevWalk(repo);	    
//    
//    //RevTag tag = rw.parseTag(repo.resolve(nomeRelease)); // Any ref will work here (HEAD, a sha1, tag, branch)
//    RevCommit commit = rw.parseCommit(repo.resolve(nomeRelease)); // Any ref will work here (HEAD, a sha1, tag, branch)
//    
//	
//	System.out.println("*******FIM - linhas de código em uma Release*******");
//	return linhasDeCodigo;		
//}
	

}
