package pcs.compiladores;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TransdutorFinitoRS extends TransdutorFinito{
	
	/**
	 * Tabela de transições e rotinas semânticas correspondentes
	 * (Estado Atual, Próximo Estado): ID da Rotina Semântica
	 */
	private HashMap<String, HashMap<String, String>> transRotinas;
	
	public TransdutorFinitoRS(String nomeArquivo1, String nomeArquivo2){
		super(nomeArquivo1);
		if(!nomeArquivo2.isEmpty()){			
			carregaRotinas(nomeArquivo2);
		}
	}
	
	// Construtor para TransdutorFinito comum, sem rotinas semânticas
	public TransdutorFinitoRS(String nomeArquivo1){
		super(nomeArquivo1);
		this.transRotinas = null;
	}
	
	private void carregaRotinas(String nomeArquivo){
		BufferedReader inputStream = null;
		String linha = null;
		
		this.transRotinas = new HashMap<String, HashMap<String, String>>();
		
		try{
			inputStream = new BufferedReader(new FileReader(nomeArquivo));
			
			linha = inputStream.readLine();
			while(linha != null && !(linha.isEmpty())){
				carregaTransRotina(linha);
				linha = inputStream.readLine();
			}
			inputStream.close();
		} catch(IOException e) {
			System.out.println("Erro 001: Abertura do arquivo \"" + nomeArquivo + "\"");
			e.getMessage();
		}
	}
	
	private void carregaTransRotina(String linha){
		String[] args = Auxiliar.getPalavrasLinha(linha);
		
		if(this.transRotinas.containsKey(args[0])){
			this.transRotinas.get(args[0]).put(args[1], args[2]);
		} else {
			HashMap<String, String> novaTransicao = new HashMap<String, String>();
			novaTransicao.put(args[1], args[2]);
			this.transRotinas.put(args[0], novaTransicao);
		}
	}
	
	public boolean fazTransicaoChar(Character c){
		String proximoEstado;
		String idRotinaSemantica;
		
		// Whitespace é o caso especial, já que ele é usado para separar argumentos da descrição do autômato
		if(Character.isWhitespace(c)){
			if(this.transicoes.containsKey(this.estadoAtual)){
				if(this.transicoes.get(this.estadoAtual).containsKey(Constantes.BRANCO)){
					
					proximoEstado = this.transicoes.get(this.estadoAtual).get(Constantes.BRANCO);
					Logger.transicao(this.estadoAtual, "branco", proximoEstado);
					
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
						Logger.rotinaSemantica(idRotinaSemantica);
						// TODO inserir chamada de rotina semântica aqui
						
					} catch(NullPointerException e) {
						idRotinaSemantica = ""; // FIXME
					}
					
					this.estadoAtual = proximoEstado;
					
					return true;
					
				} else if(this.transicoes.get(this.estadoAtual).containsKey("outros")){

					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					Logger.transicao(this.estadoAtual, "outros", proximoEstado);
					
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
						Logger.rotinaSemantica(idRotinaSemantica);
						// TODO inserir chamada de rotina semântica aqui
					} catch(NullPointerException e) {
						idRotinaSemantica = ""; // FIXME
					}
					
					this.estadoAtual = proximoEstado;
					
					return true;
					
				} else {
					
					if(!this.estadosFinais.contains(this.estadoAtual)){						
						System.out.println("Erro 005: Transicao invalida: <" + this.estadoAtual + ",branco>"); // FIXME Logger
					}
					
				}
			} else {
				
				if(!this.estadosFinais.contains(this.estadoAtual)){					
					System.out.println("Erro 006: Estado de origem \"" + this.estadoAtual + "\" nao faz transicao"); // FIXME Logger
				}
				
			}
			return false;
		}
		
		return fazTransicao(c.toString());
	}
	
	public boolean fazTransicao(String a){
		String proximoEstado;
		String idRotinaSemantica;
		
		if(this.transicoes.containsKey(this.estadoAtual)){

			if(this.transicoes.get(this.estadoAtual).containsKey(a)){
				
				proximoEstado = this.transicoes.get(this.estadoAtual).get(a);
				Logger.transicao(this.estadoAtual, a, proximoEstado);
			
				try{
					idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
					Logger.rotinaSemantica(idRotinaSemantica);
					// TODO inserir chamada da rotina semântica aqui
				} catch(NullPointerException e) {
					idRotinaSemantica = "";
				}
				
				this.estadoAtual = proximoEstado;
				
				return true;
				
			} else {
				
				if(this.transicoes.get(this.estadoAtual).containsKey("outros")){
				
					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					Logger.transicao(this.estadoAtual, "outros", proximoEstado);
					
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
						Logger.rotinaSemantica(idRotinaSemantica);
						// TODO inserir chamada de rotina semântica aqui
					} catch(NullPointerException e) {
						idRotinaSemantica = ""; // FIXME esta chamada é necessária?
					}
					
					this.estadoAtual = proximoEstado;
					
					return true;
				} // TODO colocar este erro em outro lugar; aqui, o autômato ainda pode fazer uma chamada de submaquina e prosseguir
				/*else {
					
					if(!this.estadosFinais.contains(this.estadoAtual)){
						System.out.println("Erro 003: Transicao invalida: <" + this.estadoAtual + "," + a + ">"); // FIXME Logger
					}
				}*/
			}
		} else {
			
			if(!this.estadosFinais.contains(this.estadoAtual)){				
				System.out.println("Erro 002: Estado de origem \"" +  this.estadoAtual + "\" nao faz transicao"); // FIXME Logger
			}
		}
		return false;
	}
}
