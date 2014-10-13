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
	
/*	public AutomatoPilha(String[] nomeArquivos, String[] nomeArquivos2){
		int i = 0;
		Submaquina submaquina;
		String id;
		
		submaquinas = new HashMap<String, Submaquina>();
		while(i < nomeArquivos.length){
			submaquina = new Submaquina(nomeArquivos[i], nomeArquivos2[i]);
			id = submaquina.getId();
			
			if(i == 0)
				// A primeira máquina carregada é a principal
				this.submaquinaPrincipal = id;
			if(!submaquinas.containsKey(id))
				submaquinas.put(id, submaquina);
			i++;
		}
		
		// Inicialização
		pilha = new Stack<String[]>();
		submaquinaAtual = submaquinas.get(submaquinaPrincipal);
	}*/
	
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
			// System.out.format("<%1s> %3s %3s      \n", submaquinaAtual.getId(),submaquinaAtual.getEstadoAtual(), token[0]);
			
			resultTransicao = submaquinaAtual.fazTransicao(token[1]);
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
