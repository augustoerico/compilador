package teste;

import java.util.Vector;

import pcs.compiladores.AnalisadorLexico;
import pcs.compiladores.AutomatoPilha;

public final class Teste {
 
	/*	Cria um analisador léxico com base nos arquivos de definição (definição de máquina e
	 *		definiçao de palavras reservadas), processa um arquivo de entrada (dado pelo path)
	 *		e grava o resultado no arquivo de saída.
	 */
	public static void analisadorLexico(String defMaqPath, String defPalavraRes, 
			String inPath, String outPath){
		
		System.out.printf("Teste: Analisador Léxico\n");
		System.out.printf("... %s\n", defMaqPath);
		System.out.printf("... %s\n", defPalavraRes);
		System.out.printf("... %s\n", inPath);
		System.out.printf("... %s\n", outPath);
		
		AnalisadorLexico al = new AnalisadorLexico(defMaqPath, defPalavraRes);
		al.carregaTabelaSimbolos(inPath);
		al.imprime(outPath);
		
		System.out.printf("Teste concluído.\n");
	}
	
	/*	Cria analisadores léxico e sintático, processa um arquivo de entrada (dado pelo path)
	 *		e grava o resultado no arquivo de saída.
	 */
	public static void sintaxe(String defMaqPath, String defPalavraRes, 
			String inPath, String outPath){
		
		System.out.printf("Teste: Analisador Sintático\n");
		System.out.printf("... %s\n", defMaqPath);
		System.out.printf("... %s\n", defPalavraRes);
		System.out.printf("... %s\n", inPath);
		System.out.printf("... %s\n", outPath);
		
		// Léxico
		AnalisadorLexico al = new AnalisadorLexico(defMaqPath, defPalavraRes);
		al.carregaTabelaSimbolos(inPath);
		al.imprime(outPath);
		
		Vector<String[]> tabelaTokens = al.extraiTokensArq(inPath);
		
		// Sintático
		String[] definicoes = new String[5];
		String[] rotinasSemanticas = new String[0];
		
		definicoes[0] = "/home/kiko/test_workspace/Compilador/definicoes/sintatico/program";
		definicoes[1] = "/home/kiko/test_workspace/Compilador/definicoes/sintatico/boolean_expression";
		definicoes[2] = "/home/kiko/test_workspace/Compilador/definicoes/sintatico/command_section";
		definicoes[3] = "/home/kiko/test_workspace/Compilador/definicoes/sintatico/declaration_section";
		definicoes[4] = "/home/kiko/test_workspace/Compilador/definicoes/sintatico/numeric_expression";
		
		AutomatoPilha ap = new AutomatoPilha(definicoes, rotinasSemanticas);
		
		System.out.println(ap.avaliaTokens(tabelaTokens));
		
		System.out.printf("Teste concluído.\n");
	}
}
