package pcs.compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class AnalisadorLexico extends TransdutorFinito{
	private Vector<String> tabelaPalavrasReservadas;
	private HashMap<String, String[]> tabelaSimbolos;
	
	public AnalisadorLexico(String nomeArquivo1, String nomeArquivo2){
		super(nomeArquivo1); 					// Preenche a estrutura de autômato finito
		carregaPalavrasReservadas(nomeArquivo2);
		
	}
	
	private void carregaPalavrasReservadas(String nomeArquivo){
		BufferedReader inputStream = null;
		String[] palavras;
		tabelaPalavrasReservadas = new Vector<String>();
		
		try{
			inputStream = new BufferedReader(new FileReader(nomeArquivo));
			
			palavras = Auxiliar.getPalavrasLinha(inputStream.readLine());
			for(int i = 0; i < palavras.length; i++){
				tabelaPalavrasReservadas.add(palavras[i]);
			}
			
		} catch(IOException e) {
			System.out.println("Erro 001: Abertura do arquivo " + nomeArquivo);
			e.getMessage();
		}
	}
	
	public void carregaTabelaSimbolos(String fonteArq){
		
		Vector<String[]> tokens = this.extraiTokensArq(fonteArq);
		int k = 0;
		String[] temp;
		tabelaSimbolos = new HashMap<String, String[]>();
		
		for(int i = 0; i < tokens.size(); i++){
			temp = tokens.elementAt(i);
			String[] info = new String[2];
			if(!tabelaPalavrasReservadas.contains(temp[0]) &&
					!tabelaSimbolos.containsKey(temp[0])){
				if(temp[1].equals("ID")){
					info[0] = temp[1];
					info[1] = String.valueOf(k);
					tabelaSimbolos.put(temp[0], info);
					k++;
				} else if(temp[1].equals("NUM")){
					info[0] = temp[1];
					try{
						int valor = Integer.parseInt(temp[0]);
						info[1] = Integer.toHexString(valor);
						tabelaSimbolos.put(temp[0], info);
					} catch (NumberFormatException e){
						System.out.println("Erro 009: Parse nao foi possivel (" + temp[0] + ")");
					}
				} else {
					info[0] = temp[1];
					info[1] = temp[0];
					tabelaSimbolos.put(temp[0], info);
				}
			} else if(tabelaPalavrasReservadas.contains(temp[0]) &&
					!tabelaSimbolos.containsKey(temp[0])){
				info[0] = "RESERV " + temp[0];
				info[1] = "";
				tabelaSimbolos.put(temp[0], info);
			}
		}
	}
	
	public void imprime(){
		// super.imprime();
		// System.out.println("tabelaPalavrasReservadas: " + tabelaPalavrasReservadas.toString());
		Set<String> keys = tabelaSimbolos.keySet();
		for(Iterator<String> it = keys.iterator(); it.hasNext();){
			String elem = it.next();
			System.out.format("%10s: (%15s,%10s)\n", elem, tabelaSimbolos.get(elem)[0], tabelaSimbolos.get(elem)[1]);
		}
	}
	
	/*Imprime conteúdo da tabela de símbolos em arquivo
	 */
	public void imprime(String path){

		System.out.printf("Imprimindo tabela de simbolos em %s...\n", path);
		
		try{
			File file = new File(path);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			Set<String> keys = tabelaSimbolos.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();){
				String elem = it.next();
				bw.write(System.out.format("%10s: (%15s,%10s)\n", elem, tabelaSimbolos.get(elem)[0], tabelaSimbolos.get(elem)[1]).toString());
			}
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Concluido!\n");
	}
}
