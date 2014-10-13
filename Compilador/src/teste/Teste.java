package teste;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import pcs.compiladores.AnalisadorLexico;
import pcs.compiladores.AutomatoPilha;
import pcs.compiladores.Logger;
import pcs.compiladores.Constantes;

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
	public static void sintaxe(String automato, String palavrasReservadas, 
			String pathCodFonte){
		
		String rootPath = "/home/erico/poli_workspace/compilador/Compilador";
		
		String[] definicoesLex = {automato, palavrasReservadas};
		
		// Léxico
		Logger.cabecalho("Analisador Léxico");
		Logger.definicoes(definicoesLex);
		Logger.pathCodigoFonte(pathCodFonte);
		AnalisadorLexico al = new AnalisadorLexico(rootPath + automato, rootPath + palavrasReservadas);
		System.out.println("- Transições:");
		Vector<String[]> tabelaTokens = al.tabelaTokens(rootPath + pathCodFonte);
		
		Logger.tabelaTokens(tabelaTokens);
		
		// Sintático
		// String[] rotinasSemanticas = new String[0];
		String[] definicoesSint = {"", "", "", "", ""};
		
		definicoesSint[0] = rootPath + "/definicoes/sintatico/program";
		definicoesSint[1] = rootPath + "/definicoes/sintatico/boolean_expression";
		definicoesSint[2] = rootPath + "/definicoes/sintatico/command_section";
		definicoesSint[3] = rootPath + "/definicoes/sintatico/declaration_section";
		definicoesSint[4] = rootPath + "/definicoes/sintatico/numeric_expression";
		
		Logger.cabecalho("Analisador Sintático");
		Logger.definicoes(definicoesSint);
		
		AutomatoPilha ap = new AutomatoPilha(definicoesSint);
		
		System.out.println(ap.avaliaTokens(tabelaTokens));
		
		System.out.printf("Teste concluído.\n");
	}
	
	/**
	 * 
	 * @param inputPath Pasta na qual os arquivos de entrada se encontram
	 */
	public static void sintaxe(String inputPath, String fonte){
		
		if(inputPath.isEmpty()){
			inputPath = System.getProperty("user.dir").concat(Constantes.DEFAULT_INPUT_DIR);
		}
		
		fonte = inputPath + File.separator + fonte;
		
		// Inicialização default
		String lexico = "doge2.lx";
		String palavrasReservadas = "doge2.pr";
		String sintaticoRaiz = "";
		ArrayList<String> sintatico = new ArrayList<String>();
		
		File dir = new File(inputPath);
		for(String f : dir.list()){
			
			if(f.endsWith(Constantes.LEXICO)){
				lexico = inputPath + File.separator + f;
			} else if(f.endsWith(Constantes.PALAVRAS_RESERV)){
				palavrasReservadas = inputPath + File.separator + f;
			} else if(f.endsWith(Constantes.SINTATICO_RAIZ)){
				sintaticoRaiz = inputPath + File.separator + f;
			} else if(f.endsWith(Constantes.SINTATICO)){
				sintatico.add(inputPath + File.separator + f);
			}
		}		
		
		if(sintaticoRaiz.isEmpty()){
			System.err.println("Sintatico Raiz não encontrado. O arquivo "
					+ "deve ter extensão " + Constantes.SINTATICO_RAIZ +".");
			return ;
		}
		sintatico.add(0, sintaticoRaiz);
		
		String[] definicoesLex = {lexico, palavrasReservadas};
		
		// Léxico
		Logger.cabecalho("Analisador Léxico");
		Logger.definicoes(definicoesLex);
		Logger.pathCodigoFonte(fonte);
		AnalisadorLexico al = new AnalisadorLexico(lexico, palavrasReservadas);
		System.out.println("- Transições:");
		Vector<String[]> tabelaTokens = al.tabelaTokens(fonte);
		
		Logger.tabelaTokens(tabelaTokens);
		
		// Sintático
		
		Logger.cabecalho("Analisador Sintático");
		Logger.definicoes(sintatico);
		AutomatoPilha ap = new AutomatoPilha(sintatico);
		
		// System.out.println(ap.avaliaTokens(tabelaTokens));
		Logger.resultadoSintatico(ap.avaliaTokens(tabelaTokens));
		
		System.out.printf("Teste concluído.\n");
	}
}
