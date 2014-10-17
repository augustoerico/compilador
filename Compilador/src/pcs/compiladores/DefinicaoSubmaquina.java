package pcs.compiladores;

import java.util.ArrayList;
import java.util.Collections;

public class DefinicaoSubmaquina {
	
	String definicaoSintatica;
	String definicaoSemantica;
	
	public DefinicaoSubmaquina(String sint, String sem){
		this.definicaoSintatica = sint;
		this.definicaoSemantica = sem;
	}
	
	public String getDefinicaoSintatica() {
		return definicaoSintatica;
	}
	
	public void setDefinicaoSintatica(String definicaoSintatica) {
		this.definicaoSintatica = definicaoSintatica;
	}
	
	public String getDefinicaoSemantica() {
		return definicaoSemantica;
	}
	
	public void setDefinicaoSemantica(String definicaoSemantica) {
		this.definicaoSemantica = definicaoSemantica;
	}
	
	public static ArrayList<DefinicaoSubmaquina> getListaDefinicaoSubmaquina(
			ArrayList<String> sintatico, ArrayList<String> semantico){
		
		ArrayList<DefinicaoSubmaquina> definicaoSubmaquinas = 
				new ArrayList<DefinicaoSubmaquina>();
		
		Collections.sort(sintatico);
		Collections.sort(semantico);
		
//		System.out.println(sintatico.toString()); // TODO remove-me
//		System.out.println(semantico.toString()); // TODO remove-me
		
		int i = 0;
		for(String sint : sintatico){
			
			String sem = semantico.get(i);
			sem = sem.substring(0, sem.length() - Constantes.ROTINA_SEMANTICA.length());
			
			if(sint.startsWith(sem)){
				definicaoSubmaquinas.add(new DefinicaoSubmaquina(sint,
						sem.concat(Constantes.ROTINA_SEMANTICA)));
				i++;
			} else {
				definicaoSubmaquinas.add(new DefinicaoSubmaquina(sint, ""));
			}
		}
		
		// System.out.println(definicaoSubmaquinas.toString()); // TODO remove-me
		
		return definicaoSubmaquinas;
	}

	@Override
	public String toString() {
		return "DefinicaoSubmaquina [definicaoSintatica=" + definicaoSintatica
				+ ", definicaoSemantica=" + definicaoSemantica + "]";
	}

}
