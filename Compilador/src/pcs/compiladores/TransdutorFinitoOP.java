package pcs.compiladores;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TransdutorFinitoOP extends TransdutorFinito{
	
	/**
	 * Tabela de transições e rotinas semânticas correspondentes
	 * (Estado Atual, Próximo Estado): ID da Rotina Semântica
	 */
	private HashMap<String, HashMap<String, String>> transRotinas;
	
	public TransdutorFinitoOP(String nomeArquivo1, String nomeArquivo2){
		super(nomeArquivo1);
		carregaRotinas(nomeArquivo2);
	}
	
	// Construtor para TransdutorFinito comum, sem rotinas semânticas
	public TransdutorFinitoOP(String nomeArquivo1){
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
				if(this.transicoes.get(this.estadoAtual).containsKey("branco")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("branco");
					// System.out.println("<" + this.estadoAtual + ",branco," + proximoEstado + ">");
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
					} catch(NullPointerException e) {
						idRotinaSemantica = "";
					}
					System.out.println("Rotina Semantica: " + idRotinaSemantica);
					this.estadoAtual = proximoEstado;
					return true;
				} else if(this.transicoes.get(this.estadoAtual).containsKey("outros")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					// System.out.println("<" + this.estadoAtual + ",outros," + proximoEstado + ">");
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
					} catch(NullPointerException e) {
						idRotinaSemantica = "";
					}
					System.out.println("Rotina Semantica: " + idRotinaSemantica);
					this.estadoAtual = proximoEstado;
					return true;
				} else {
					System.out.println("Erro 005: Transicao invalida: <" + this.estadoAtual + ",branco>");
				}
			} else {
				System.out.println("Erro 006: Estado de origem \"" + this.estadoAtual + "\" nao faz transicao");
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
				// System.out.println("<" + this.estadoAtual + "," + a + "," + proximoEstado + ">");
				try{
					idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
				} catch(NullPointerException e) {
					idRotinaSemantica = "";
				}
				System.out.println("Rotina Semantica: " + idRotinaSemantica);
				this.estadoAtual = proximoEstado;
				return true;
			} else {
				if(this.transicoes.get(this.estadoAtual).containsKey("outros")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					// System.out.println("<" + this.estadoAtual + ",outros," + proximoEstado + ">");
					try{
						idRotinaSemantica = this.transRotinas.get(this.estadoAtual).get(proximoEstado);
					} catch(NullPointerException e) {
						idRotinaSemantica = "";
					}
					System.out.println("Rotina Semantica: " + idRotinaSemantica);
					this.estadoAtual = proximoEstado;
					return true;
				} else {
					// System.out.println("Erro 003: Transicao invalida: <" + this.estadoAtual + "," + a + ">");
				}
			}
		} else {
			System.out.println("Erro 002: Estado de origem \"" +  this.estadoAtual + "\" nao faz transicao");
		}
		return false;
	}
	
	public void selecionaRotinaSemantica(String rotina){
		
	}
}
