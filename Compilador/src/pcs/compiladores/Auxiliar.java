package pcs.compiladores;
import java.util.ArrayList;

public class Auxiliar {
	
	public static String[] getPalavrasLinha(String linha){
		ArrayList<String> palavras = new ArrayList<String>();
		int i = 0;
		int inicio, fim;
		
		while(i < linha.length()){
			if(!Character.isWhitespace(linha.charAt(i))){
				inicio = i;
				while(i < linha.length() && !Character.isWhitespace(linha.charAt(i)))
					i++;
				fim = i;
				palavras.add(linha.substring(inicio, fim));
			}
			i++;
		}
		
		return palavras.toArray(new String[palavras.size()]);
	}
	
	public static boolean linhaVazia(String linha){
		int i = 0;
		boolean vazia = true;
		
		while(i < linha.length()){
			if(!Character.isWhitespace(linha.charAt(i))){
				vazia = false;
			}
			i++;
		}
		return vazia;
	}
}
