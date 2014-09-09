package pcs.compiladores;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AutomatoPilha implements IAutomatoPilha {
	protected HashMap<String, Submaquina> submaquinas;
	protected Stack<String[]> pilha;
	private String submaquinaPrincipal;
	protected Submaquina submaquinaAtual;
	
	
	public AutomatoPilha(String[] nomeArquivos, String[] nomeArquivos2){
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
					
					System.out.println("Chamada de Submaquina");
					
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
						
						System.out.println("Desempilha");
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
	
//	public boolean avaliaTokensSimples2(Vector<String> tokens){
//		boolean resultTransicao;
//		String[] elemPilha;
//		int i = 0;
//		int size = tokens.size();
//		
//		while(i < size){
//			String elem = tokens.get(i);
//			// System.out.format("<%1s> %3s %3s      \n", submaquinaAtual.getId(),submaquinaAtual.getEstadoAtual(), elem);
//			
//			resultTransicao = submaquinaAtual.fazTransicao(elem);
//			if(!resultTransicao){
//				elemPilha = submaquinaAtual.chamaSubmaquina();
//				if(!elemPilha[0].isEmpty()){
//					// System.out.println("Chamada de Submaquina");
//					// Chamada de Submaquina
//					pilha.push(elemPilha);
//					submaquinaAtual = submaquinas.get(elemPilha[0]);
//					
//					submaquinaAtual.inicializaAutomato();
//				} else {
//					// Não é possível fazer a chamada de submáquina
//					if((submaquinaAtual.getEstadosFinais()).contains(submaquinaAtual.getEstadoAtual())){
//						// System.out.println("Desempilha");
//						// Desempilha
//						elemPilha = pilha.pop();
//						submaquinaAtual = submaquinas.get(elemPilha[0]);
//						submaquinaAtual.setEstadoAtual(elemPilha[1]);
//					} else {
//						// Não é possível desempilhar => estado de rejeição
//						return false;
//					}
//				}
//			} else {
//				// Transição OK, pegue o próximo token
//				i++;
//			}
//		}
//		
//		// Verifica se há um último elemento para fazer transição
//		while(!pilha.isEmpty()){
//			if(submaquinaAtual.getEstadosFinais().contains(submaquinaAtual.getEstadoAtual())){
//				// Desempilha
//				elemPilha = pilha.pop();
//				submaquinaAtual = submaquinas.get(elemPilha[0]);
//				submaquinaAtual.setEstadoAtual(elemPilha[1]);
//			} else {
//				// Está em estado não final e não possui símbolos para transição
//				return false;
//			}
//		}
//		
//		return submaquinaAtual.getEstadosFinais().contains(submaquinaAtual.getEstadoAtual());
//	}
}
