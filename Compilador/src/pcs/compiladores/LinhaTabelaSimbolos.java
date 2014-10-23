package pcs.compiladores;

public class LinhaTabelaSimbolos {
	
	private String nome;
	private int endereco;
	private int tamanho;
	private Tipo tipo;
	
	public LinhaTabelaSimbolos(String nome, int endereco,
			int tamanho, Tipo tipo){
		this.nome = nome;
		this.endereco = endereco;
		this.tamanho = tamanho;
		this.tipo = tipo;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getEndereco() {
		return endereco;
	}
	public void setEndereco(int endereco) {
		this.endereco = endereco;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	

}
