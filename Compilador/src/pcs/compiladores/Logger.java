package pcs.compiladores;

import java.util.ArrayList;
import java.util.Vector;

public class Logger {
	
	public static void cabecalho(String cabecalho){
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(cabecalho);
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println();
	}
	
	public static void definicoes(ArrayList<String> definicoes){
		System.out.println("- Arquivos de definição: ");
		for(String d : definicoes){
			System.out.println("\t" + d);
		}
		System.out.println();
	}
	
	public static void definicoes(String[] definicoes){
		System.out.println("- Arquivos de definição: ");
		for(String d : definicoes){
			System.out.println("\t" + d);
		}
		System.out.println();
	}

	public static void pathCodigoFonte(String path){
		System.out.println("- Cógido fonte: " + path);
		System.out.println();
	}
	
	public static void transicao(String origem, String simbolo, String destino){
		System.out.println("\t(" + origem + ", " + simbolo + ") -> " + destino);
	}
	
	public static void tabelaTokens(Vector<String[]> tokens){
		System.out.println("- Tokens: ");
		
		for(String[] t : tokens){
			System.out.println("\t(" + t[0] + ", " + t[1] + ")");
		}
		
		System.out.println();
	}
	
	public static void newLine(){
		System.out.println();
	}
	
	public static void chamadaSubmaquina(String id){
		System.out.println("\t--- Chamada de Submaquina: " + id);
	}
	
	public static void retornoSubmaquina(String id){
		System.out.println("\t--- Retorno de Submaquina: " + id);
	}
	
	public static void resultadoSintatico(boolean resultado){
		System.out.println("\nLinguagem " + ( resultado ? "" : "não " ) + "reconhecida.");
	}
}
