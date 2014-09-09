package pcs.compiladores;
import java.util.ArrayList;
// import java.util.ListIterator;
import java.util.HashMap;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TransdutorFinito {
	protected String estadoAtual;
	protected String estadoInicial;
	protected ArrayList<String> estados; 
	protected ArrayList<String> alfabeto;
	protected ArrayList<String> estadosFinais;
	protected HashMap<String, HashMap<String, String>> transicoes;
	
	public void setEstadoAtual(String estadoAtual){
		this.estadoAtual = estadoAtual;
	}
	
	public String getEstadoAtual(){
		return this.estadoAtual;
	}
	
	public void setEstadoInicial(String estadoInicial){
		this.estadoInicial = estadoInicial;
	}
	
	public String getEstadoInicial(){
		return this.estadoInicial;
	}
	
	public void setAlfabeto(ArrayList<String> alfabeto){
		this.alfabeto = alfabeto;
	}
	
	public ArrayList<String> getAlfabeto(){
		return this.alfabeto;
	}
	
	public void setEstados(ArrayList<String> estados){
		this.estados = estados;
	}
	
	public ArrayList<String> getEstados(){
		return this.estados;
	}

	public void setEstadosFinais(ArrayList<String> estadosFinais){
		this.estadosFinais = estadosFinais;
	}
	
	public ArrayList<String> getEstadosFinais(){
		return this.estadosFinais;
	}
	
	public void setTransicoes(HashMap<String, HashMap<String, String>> transicoes){
		this.transicoes = transicoes;
	}
	
	public HashMap<String, HashMap<String, String>> getTransicoes(){
		return this.transicoes;
	}
	
	public void inicializaAutomato(){
		this.estadoAtual = this.estadoInicial;
	}
	
	
	private void carregaEstados(String linha){
		String[] args = Auxiliar.getPalavrasLinha(linha);
		
		this.estadoAtual = new String("");
		this.estadoInicial = new String("");
		this.estados = new ArrayList<String>();
		this.estadosFinais = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++){
			if(args[i].startsWith("*")){
				this.estados.add(args[i].substring(1));
				this.estadosFinais.add(args[i].substring(1));
			} else {
				this.estados.add(args[i]);
			}
		}
		
		this.estadoAtual = this.estadoInicial = this.estados.get(0);
	}
	
	private void carregaAlfabeto(String linha){
		String[] args = Auxiliar.getPalavrasLinha(linha);
		this.alfabeto = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++){
				this.alfabeto.add(args[i]);
		}
	}
	
	private void carregaTransicao(String linha){
		String[] args = Auxiliar.getPalavrasLinha(linha);
				
		if(this.transicoes.containsKey(args[0])){
			this.transicoes.get(args[0]).put(args[1], args[2]);
		} else {
			HashMap<String, String> novaTransicao = new HashMap<String, String>();
			novaTransicao.put(args[1], args[2]);
			this.transicoes.put(args[0], novaTransicao);
		}
	}
	
	public void imprime(){
		System.out.println("estadoInicial: " + this.estadoInicial);
		System.out.println("estadoAtual: " + this.estadoAtual);
		System.out.println("estados: " + this.estados.toString());
		System.out.println("alfabeto: " + this.alfabeto.toString());
		System.out.println("estadosFinais: " + this.estadosFinais.toString());
		System.out.println("transicoes: " + this.transicoes.toString());
	}
	
	public TransdutorFinito(String nomeArquivo){
		BufferedReader inputStream = null;
		String linha = null;
		
		this.transicoes = new HashMap<String, HashMap<String, String>>();
		
		try{
			inputStream = new BufferedReader(new FileReader(nomeArquivo));
			
			carregaEstados(inputStream.readLine());
			carregaAlfabeto(inputStream.readLine());
			linha = inputStream.readLine();
			while(linha != null && !(linha.isEmpty())){
				carregaTransicao(linha);
				linha = inputStream.readLine();
			}
			
			
		} catch(IOException e) {
			System.out.println("Erro 001: Abertura do arquivo " + nomeArquivo);
			e.getMessage();
		}
	}
	
	public Vector<String[]> extraiTokensArq(String nomeArquivo){
		String linha = new String();
		BufferedReader inputStream = null;
		Vector<String[]> temp = new Vector<String[]>();
		
		try{
			inputStream = new BufferedReader(new FileReader(nomeArquivo));
			
			while(inputStream.ready()){
				linha = inputStream.readLine();
				if(!Auxiliar.linhaVazia(linha))
					temp.addAll(this.extraiTokens(linha));
			}
			
			inputStream.close();
			
		} catch(IOException e) {
			System.out.println("(001) Erro na abertura do arquivo \"" + nomeArquivo + "\"");
			e.getMessage();
		}
		
		return temp;
	}
	
	public Vector<String[]> extraiTokens(String cadeia){
		Vector<String[]> tabelaTokens = new Vector<String[]>();
		String[] par = new String[2];
		boolean primeiroChar = true;
		int j = 0, i = 0;
		
		this.estadoAtual = this.estadoInicial;
	
		for(i = 0; i < cadeia.length(); ){
			// System.out.println(cadeia.charAt(i));
			if(fazTransicaoChar((Character) cadeia.charAt(i))){
				if(primeiroChar && !Character.isWhitespace(cadeia.charAt(i))){
					j = i;
					primeiroChar = false;
				}
				i++;
			} else if(this.estadosFinais.contains(this.estadoAtual)){
				// Se o automato nao consegue prosseguir, ele é forçado para o estado inicial
				par = new String[2];
				par[0] = cadeia.substring(j, i);
				par[1] = this.estadoAtual;
				tabelaTokens.add(par);
				
				// System.out.println(par[0] + ' ' + par[1] + " added");
				this.estadoAtual = this.estadoInicial;
				primeiroChar = true;
			} else {
				System.out.println("(007) Maquina travada no estado de rejeicao: " + this.estadoAtual);
				break;
			}
		}
		// Conclui a operacao do loop para o ultimo caractere encontrado
		if(this.estadosFinais.contains(this.estadoAtual)){
			par = new String[2];
			par[0] = cadeia.substring(j, i);
			par[1] = this.estadoAtual;
			tabelaTokens.add(par);
			
			// System.out.println(par[0] + ' ' + par[1] + " added");
			this.estadoAtual = this.estadoInicial;
		} else {
			System.out.println("Erro 008: Maquina travada no estado de rejeicao");
		}
		
		return tabelaTokens;
	}
	
	public boolean fazTransicaoChar(Character c){
		String proximoEstado;
		
		// Whitespace é o caso especial, já que ele é usado para separar argumentos da descrição do autômato
		if(Character.isWhitespace(c)){
			if(this.transicoes.containsKey(this.estadoAtual)){
				if(this.transicoes.get(this.estadoAtual).containsKey("branco")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("branco");
					System.out.println("<" + this.estadoAtual + ",branco," + proximoEstado + ">");
					this.estadoAtual = proximoEstado;
					return true;
				} else if(this.transicoes.get(this.estadoAtual).containsKey("outros")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					System.out.println("<" + this.estadoAtual + ",outros," + proximoEstado + ">");
					this.estadoAtual = proximoEstado;
					return true;
				} else {
					// System.out.println("Erro 005: Transicao invalida: <" + this.estadoAtual + ",branco>");
					// Estado atual não faz transição com 'branco'; retorno para estado inicial
					System.out.println("(005) Transicao invalida: <" + this.estadoAtual + ", branco>");
					System.out.println("Retorno para estado inicial.");
				}
			} else {
				// System.out.println("Erro 006: Estado de origem \"" + this.estadoAtual + "\" nao faz transicao");
				// Estado atual não faz nenhuma transição; retorno para estado inicial
				System.out.println("(006) Estado de origem nao faz transicao: \"" + this.estadoAtual + "\"");
				System.out.println("Retorno para estado inicial.");
			}
			return false;
		}
		
		return fazTransicao(c.toString());
	}
	
	public boolean fazTransicao(String a){
		String proximoEstado;
		
		if(this.transicoes.containsKey(this.estadoAtual)){
			if(this.transicoes.get(this.estadoAtual).containsKey(a)){
				proximoEstado = this.transicoes.get(this.estadoAtual).get(a);
				System.out.println("<" + this.estadoAtual + "," + a + "," + proximoEstado + ">");
				this.estadoAtual = proximoEstado;
				return true;
			} else {
				if(this.transicoes.get(this.estadoAtual).containsKey("outros")){
					proximoEstado = this.transicoes.get(this.estadoAtual).get("outros");
					System.out.println("<" + this.estadoAtual + ",outros," + proximoEstado + ">");
					this.estadoAtual = proximoEstado;
					return true;
				} else {
					// System.out.println("Erro 003: Transicao invalida: <" + this.estadoAtual + "," + a + ">");
					// Estado atual não faz transição com token atual; retorno para estado inicial
					System.out.println("(003) Transicao invalida: " + "<" + this.estadoAtual + "," + a + ">");
					System.out.println("Retorno para estado inicial.");
				}
			}
		} else {
			// System.out.println("Erro 002: Estado de origem \"" + this.estadoAtual + "\" nao faz transicao");
			// Estado atual não faz nenhuma transição; retorno para estado inicial
			System.out.println("(002) Estado de origem nao faz transicao: \"" + this.estadoAtual + "\"");
			System.out.println("Retorno para estado inicial.");
		}
		return false;
	}
	
	public boolean avaliaCadeia(String cadeia){
		int i = 0;
		Character c;
		
		this.estadoAtual = this.estadoInicial;
	
		for(i = 0; i < cadeia.length(); ){
			c = cadeia.charAt(i);
			// System.out.println(cadeia.charAt(i));
			if(fazTransicaoChar(c)){
				i++;
			} else {
				System.out.println("(007) Maquina travada");
				return false;
			}
		}
		
		return this.estadosFinais.contains(estadoAtual);
	}
}
