package teste;

import java.util.Vector;

import pcs.compiladores.AnalisadorLexico;
import pcs.compiladores.AutomatoPilha;

public final class Teste {
	
	private static String path = "/home/erico/poli_workspace/compilador/Compilador/";
 
	/*	Cria um analisador léxico com base nos arquivos de definição (definição de máquina e
	 *		definiçao de palavras reservadas), processa um arquivo de entrada (dado pelo path)
	 *		e grava o resultado no arquivo de saída.
	 */
	public static void testeAnalisadorLexico(String defMaqPath, String defPalavraRes, 
			String inPath){
		
		System.out.printf("Teste: Analisador Léxico\n");
		System.out.printf("... %s\n", defMaqPath);
		System.out.printf("... %s\n", defPalavraRes);
		System.out.printf("... %s\n", inPath);
		
		AnalisadorLexico al = new AnalisadorLexico(path + defMaqPath, path + defPalavraRes);
		al.tabelaTokens(path + inPath);
		
		al.imprime();
		
		System.out.printf("Teste concluído.\n");
	}
	
	/*	Cria analisadores léxico e sintático, processa um arquivo de entrada (dado pelo path)
	 *		e grava o resultado no arquivo de saída.
	 */
	public static void sintaxe(String defMaqPath, String defPalavraRes, 
			String inPath, String outPath){
		
		String rootPath = "/home/erico/poli_workspace/compilador/Compilador";
		
		System.out.printf("Teste: Analisador Sintático\n");
		System.out.printf("... %s\n", defMaqPath);
		System.out.printf("... %s\n", defPalavraRes);
		System.out.printf("... %s\n", inPath);
		System.out.printf("... %s\n", outPath);
		
		// Léxico
		AnalisadorLexico al = new AnalisadorLexico(rootPath + defMaqPath, rootPath + defPalavraRes);
		Vector<String[]> tabelaTokens = al.tabelaTokens(rootPath + inPath);
		
		// Sintático
		String[] definicoes = new String[5];
		// String[] rotinasSemanticas = new String[0];
		
		definicoes[0] = rootPath + "/definicoes/sintatico/program";
		definicoes[1] = rootPath + "/definicoes/sintatico/boolean_expression";
		definicoes[2] = rootPath + "/definicoes/sintatico/command_section";
		definicoes[3] = rootPath + "/definicoes/sintatico/declaration_section";
		definicoes[4] = rootPath + "/definicoes/sintatico/numeric_expression";
		
		AutomatoPilha ap = new AutomatoPilha(definicoes);
		
		System.out.println(ap.avaliaTokens(tabelaTokens));
		
		System.out.printf("Teste concluído.\n");
	}
}
