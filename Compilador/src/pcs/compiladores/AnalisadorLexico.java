package pcs.compiladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class AnalisadorLexico extends TransdutorFinito{
	private Vector<String> palavrasReservadas;
	
	public AnalisadorLexico(String nomeArquivo1, String nomeArquivo2){
		super(nomeArquivo1); 					// Preenche a estrutura de autômato finito
		carregaPalavrasReservadas(nomeArquivo2);
		
	}
	
	private void carregaPalavrasReservadas(String nomeArquivo){
		BufferedReader inputStream = null;
		String[] palavras;
		palavrasReservadas = new Vector<String>();
		
		try{
			inputStream = new BufferedReader(new FileReader(nomeArquivo));
			
			palavras = Auxiliar.getPalavrasLinha(inputStream.readLine());
			for(int i = 0; i < palavras.length; i++){
				palavrasReservadas.add(palavras[i]);
			}
			
		} catch(IOException e) {
			System.out.println("Erro 001: Abertura do arquivo " + nomeArquivo);
			e.getMessage();
		}
	}
	
	public Vector<String[]> tabelaTokens(String fonteArq){
		
		Vector<String[]> tokens = this.extraiTokensArq(fonteArq);
		Vector<String[]> toRemove = new Vector<String[]>();
		
		Logger.newLine();
		
		for(String[] e : tokens){
			// System.out.println(e[0] + ", " + e[1]);
			if(e[1].equals(Constantes.COMENTARIO)){
				// Remove comment
				// System.out.println("Comentario ignorado: " + e[0]);
				toRemove.add(e);
			} else {
				if(palavrasReservadas.contains(e[0])){
					// Classify reserved words
					e[1] = e[0];
				} else if(e[1].equals(Constantes.DECIMAL)){
					// Dec to hex
					try{
						int valor = Integer.parseInt(e[0]);
						e[0] = Integer.toHexString(valor);
					} catch(NumberFormatException nfe){
						System.out.println("Erro 009: Parse nao foi possivel (" + e[0] + ")");
					}
					e[1] = Constantes.NUMERO;
					
				} else if(e[1].equals(Constantes.HEXADECIMAL)){
					// Hex to hex (WAT?)
					try{
						int valor = Integer.parseInt(e[0].substring(1), 16);
						e[0] = Integer.toHexString(valor);
					} catch(NumberFormatException nfe){
						System.out.println("Erro 009: Parse nao foi possivel (" + e[0].substring(1) + ")");
					}
					e[1] = Constantes.NUMERO;
				} 
				// System.out.println(e[0] + ", " + e[1]);
			}
			
		}
		
		tokens.removeAll(toRemove);
		
		return tokens;
	}
}
