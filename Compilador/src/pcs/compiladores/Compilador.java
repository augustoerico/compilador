package pcs.compiladores;

import teste.Teste;

public class Compilador {
	
	public static void main(String[] args) {
//		Exercicios.exercicio1();
//		Exercicios.exercicio2();
//		Exercicios.testeAutPilha();
//		Exercicios.exercicio5_1();
//		Exercicios.exercicio5_2();
//		Exercicios.exercicio5_3();
//		Exercicios.testeAutomato();
//		Exercicios.testeRotinas();
//		Teste.testeAnalisadorLexico("definicoes/lexico/doge2.lx", 
//				"definicoes/palavras-reservadas/doge2.pr", 
//				"programas-exemplo/palindromo.dg");
//		Teste.sintaxe("/definicoes/lexico/doge2.lx", 
//				"/definicoes/palavras-reservadas/doge2.pr", 
//				"/programas-exemplo/media.dg");
//		Teste.sintaxe("/home/erico/poli_workspace/compilador/Compilador/input", "media.dg");
		Teste.chamadaRotinasSemanticas2("/home/erico/poli_workspace/compilador/Compilador/input", "media.dg");
	}
}
