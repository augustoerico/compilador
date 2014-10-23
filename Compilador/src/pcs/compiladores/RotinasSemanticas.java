package pcs.compiladores;

import java.util.HashMap;

public class RotinasSemanticas {
	
	private HashMap<String, LinhaTabelaSimbolos> tabela;
	private int contador;
	// Declaration section variables
	private String nome;
	private int endereco;
	private int tamanho;
	private Tipo tipo;
	
	public RotinasSemanticas(){
		
		this.tabela = new HashMap<String, LinhaTabelaSimbolos>();
		this.contador = 0;
		
	}
	
	public static void dispararRotina(String idRotina){
		
		if(idRotina.equals("R0")){
			Rotinas.R0();
		} else if(idRotina.equals("R1")){
			Rotinas.R1();
		} else if(idRotina.equals("R1")){
			Rotinas.R2();
		} else {
			Logger.rotinaNaoDefinida(idRotina);
		}
		
	}
	
	public void disparaRotina(String idRotina, String[] token){
		
		if(idRotina.equals("DS1")){
			this.DS1(token);
		} else if(idRotina.equals("DS2")){
			this.DS2(token);
		} else if(idRotina.equals("DS3")){
			this.DS3(token);
		} else {
			Logger.rotinaNaoDefinida(idRotina);
		}
		
	}
	
	private void DS1(String[] token){
		this.nome = token[0];
		this.endereco = contador;
		this.tamanho = 1;
		this.tipo = Tipo.INTEIRO;
	}
	
	private void DS2(String[] token){
		this.contador = tamanho + 1;
		LinhaTabelaSimbolos linha = new LinhaTabelaSimbolos(nome, endereco,
										tamanho, tipo);
		this.tabela.put(nome, linha);
	}
	
	private void DS3(String[] token){
		
		try{
			this.tamanho = Integer.parseInt(token[0]);
		} catch(NumberFormatException e){
			try{
				this.tamanho = Integer.parseInt(token[0], 16);
			} catch(NumberFormatException ex){
				this.tamanho = 1; // TODO: criar um erro no log mais elaborado
				ex.printStackTrace();
			}
		}
	}
	
	private static class Rotinas{
		
		// Rotinas de teste
		static void R0(){
			System.out.println("\n\t Rotina 0 \n");
		}
		static void R1(){
			System.out.println("\n\t Rotina 1 \n");
		}
		static void R2(){
			System.out.println("\n\t Rotina 2 \n");
		}
	
	}

}
