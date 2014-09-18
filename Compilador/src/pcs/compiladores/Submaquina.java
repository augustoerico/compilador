package pcs.compiladores;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

// public class Submaquina extends TransdutorFinito{
public class Submaquina extends TransdutorFinito{
	private String id; // nome único da submáquina
	
	// Submaquina comum, sem rotinas semânticas
	public Submaquina(String nomeArquivo){
		super(nomeArquivo);
		int posicaoPonto = nomeArquivo.indexOf('.');
		
		if(posicaoPonto != -1){
			this.id = new File(nomeArquivo).getName().substring(0, posicaoPonto);
		} else {
			this.id = new File(nomeArquivo).getName();
		}
	}

//	// Submaquina com rotinas semânticas
//	public Submaquina(String nomeArquivo1, String nomeArquivo2){
//		super(nomeArquivo1, nomeArquivo2);
//		
//		int posicaoPonto = nomeArquivo1.indexOf('.');
//		
//		if(posicaoPonto != -1){
//			this.id = nomeArquivo1.substring(0, posicaoPonto);
//		} else {
//			this.id = nomeArquivo1;
//		}
//
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String[] chamaSubmaquina(){
		HashMap<String, String> transicoes = new HashMap<String, String>();
		Set<String> keys;
		String[] result = new String[3];
		
		result[0] = result[1] = result[2] = "";
		
		if(this.getTransicoes().containsKey(this.getEstadoAtual())){
			transicoes = this.getTransicoes().get(this.getEstadoAtual());
			keys = transicoes.keySet();
			
			// Verifica todas as possíveis transições a partir do estado atual
			for(Iterator<String> it = keys.iterator(); it.hasNext();){
				String elem = it.next();
				
				// "<XYZ>" é a notação de chamada da submaquina com ID "XYZ"
				if(elem.startsWith("<") && elem.endsWith(">")){
					// System.out.println(elem.substring(1, elem.length() - 1));
					result[0] = id;										// Submaquina corrente
					result[1] = transicoes.get(elem);					// Estado de Retorno
					result[2] = elem.substring(1, elem.length() - 1); 	// Submaquina a ser chamada
					
					return result;
				}
			}
			// Não há chamada de submáquina para o estado atual
			return result;
		} else {
			// Não há nenhuma transição para o estado atual
			return result;
		}
	}
}
