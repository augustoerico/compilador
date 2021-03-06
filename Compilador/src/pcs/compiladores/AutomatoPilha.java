package pcs.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AutomatoPilha {
	protected HashMap<String, Submaquina> submaquinas;
	protected Stack<String[]> pilha;
	private String submaquinaPrincipal;
	protected Submaquina submaquinaAtual;
	
	public AutomatoPilha(String[] nomeArquivos){
		
		Submaquina submaquina;
		String id;
		submaquinas = new HashMap<String, Submaquina>();
		
		boolean first = true;
		for(String f : nomeArquivos){
			submaquina = new Submaquina(f);
			
			id = submaquina.getId();
			if(first){
				// A primeira maquina carregada é a principal
				this.submaquinaPrincipal = id;
				first = !first;
			}
			if (!submaquinas.containsKey(id)){
				submaquinas.put(id, submaquina);
			}
		}
		
		// Inicialização
		pilha = new Stack<String[]>();
		submaquinaAtual = submaquinas.get(submaquinaPrincipal);
	}
	
public AutomatoPilha(ArrayList<String> nomeArquivos){
		
		Submaquina submaquina;
		String id;
		submaquinas = new HashMap<String, Submaquina>();
		
		boolean first = true;
		for(String f : nomeArquivos){
			submaquina = new Submaquina(f);
			
			id = submaquina.getId();
			// FIXME ao invés de usar a flag "first", retirar o primeiro elemento (pop) e depois fazer o loop
			if(first){
				// A primeira maquina carregada é a principal
				/// FIXME poderia ser a última (usando pop)
				this.submaquinaPrincipal = id;
				first = !first;
			}
			if (!submaquinas.containsKey(id)){
				submaquinas.put(id, submaquina);
			}
		}
		
		// Inicialização
		pilha = new Stack<String[]>();
		submaquinaAtual = submaquinas.get(submaquinaPrincipal);
	}
	
	public AutomatoPilha(String idSintaticoRaiz, ArrayList<DefinicaoSubmaquina> defSubmaquina){ 
		
		this.submaquinas = new HashMap<String, Submaquina>();
		this.submaquinaPrincipal = idSintaticoRaiz;

		Submaquina submaquina;
		String id;
		
		for(DefinicaoSubmaquina ds : defSubmaquina){
			
			submaquina = new Submaquina(ds);	
			id = submaquina.getId();
			
			if(!submaquinas.containsKey(id)){
				submaquinas.put(id, submaquina);
			}
		}
		
		// Inicialização
		pilha = new Stack<String[]>();
		submaquinaAtual = submaquinas.get(submaquinaPrincipal);
	}
	
	public Submaquina getSubmaquinaAtual(){
		return submaquinaAtual;
	}
	
	public boolean avaliaTokens(Vector<String[]> tokens){
		boolean resultTransicao;
		String[] elemPilha;
		int i = 0;
		int size = tokens.size();
		
		while(i < size){
			String[] token = tokens.get(i); // token[0]: valor -> auxilia o Analisador Semântico
											// token[1]: tipo -> trabalha com o Analisador Sintático
			
			resultTransicao = submaquinaAtual.fazTransicao(token);
			if(!resultTransicao){
				elemPilha = new String[3];
				elemPilha = submaquinaAtual.chamaSubmaquina();	// elemPilha[0]: ID da submáquina corrente
																// elemPilha[1]: Estado de Retorno
																// elemPilha[2]: ID da submaquina a ser chamada
				if(!elemPilha[0].isEmpty()){
					// Empilha
					pilha.push(elemPilha);
					
					Logger.chamadaSubmaquina(elemPilha[2]);
					
					// Chamada de Submaquina
					submaquinaAtual = submaquinas.get(elemPilha[2]);
					
					submaquinaAtual.inicializaAutomato();
				} else {
					// Não é possível fazer a chamada de submáquina
					if((submaquinaAtual.getEstadosFinais()).contains(submaquinaAtual.getEstadoAtual())){
						elemPilha = new String[3];
						
						// Desempilha
						elemPilha = pilha.pop();
						submaquinaAtual = submaquinas.get(elemPilha[0]);
						submaquinaAtual.setEstadoAtual(elemPilha[1]);
						
						Logger.retornoSubmaquina(elemPilha[2]);
					} else {
						// Não é possível desempilhar => estado de rejeição
						return false;
					}
				}
			} else {
				// Transição OK, pegue o próximo token
				i++;
			}
		}
		
		// Verifica se há um último elemento para fazer transição
		while(!pilha.isEmpty()){
			if(submaquinaAtual.getEstadosFinais().contains(submaquinaAtual.getEstadoAtual())){
				// Desempilha
				elemPilha = pilha.pop();
				submaquinaAtual = submaquinas.get(elemPilha[0]);
				submaquinaAtual.setEstadoAtual(elemPilha[1]);
			} else {
				// Está em estado não final e não possui símbolos para transição
				return false;
			}
		}
		
		return submaquinaAtual.getEstadosFinais().contains(submaquinaAtual.getEstadoAtual());
	}
}
