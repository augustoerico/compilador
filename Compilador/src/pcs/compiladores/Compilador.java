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
		Teste.analisadorLexico("/home/kiko/test_workspace/Compilador/definicoes/lexico/doge2.lx", 
				"/home/kiko/test_workspace/Compilador/definicoes/palavras-reservadas/doge2.pr", 
				"/home/kiko/test_workspace/Compilador/programas-exemplo/palindromo.dg",
				"/home/kiko/test_workspace/Compilador/resultados/palindromo2.lx-res");
		Teste.sintaxe("/home/kiko/test_workspace/Compilador/definicoes/lexico/doge2.lx", 
				"/home/kiko/test_workspace/Compilador/definicoes/palavras-reservadas/doge2.pr", 
				"/home/kiko/test_workspace/Compilador/programas-exemplo/palindromo.dg",
				"/home/kiko/test_workspace/Compilador/resultados/palindromo2.lx-res");
	}
}
