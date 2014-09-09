package pcs.compiladores;
import java.util.ListIterator;
import java.util.Vector;


public final class Exercicios {
	public static void exercicio1(){
		System.out.println("Exercicio 1");
		TransdutorFinito transdutor = new TransdutorFinito("defC.txt");
		Vector<String[]> tabelaTokens = transdutor.extraiTokensArq("exercicio1.txt");
		String[] elem = new String[2];
		for(ListIterator<String[]> it = tabelaTokens.listIterator(); it.hasNext();){
			elem = it.next();
			System.out.format("%10s: <%s>\n", elem[0], elem[1]);
		}
	}
	
	public static void exercicio2(){
		System.out.println("Exercicio 2");
		AnalisadorLexico analisador = new AnalisadorLexico("defC.txt", "defC-reserv.txt");
		analisador.carregaTabelaSimbolos("exercicio1.txt");
		analisador.imprime();
	}
	
//	public static void testeAutPilha(){
//		System.out.println("Exercicio 3");
//		String[] nomes = new String[1];
//		nomes[0] = "S.txt";
//		AutomatoPilha automato = new AutomatoPilha(nomes);
//		Vector<String> tokens = new Vector<String>();
//		
//		String input = new String("( ( ( a ) ) ) , [ [ a ] ] , { { { { a } } } }");
//		String[] words = input.split(" ");
//		for(int i = 0; i < words.length; i++){
//			tokens.add(words[i]);
//		}
//		System.out.println(automato.avaliaTokensSimples2(tokens));
//	}
	
	public static void exercicio5_1(){
		System.out.println("Exercicio 5 - 1");
		TransdutorFinito transdutor = new TransdutorFinito("defWirth.txt");
		Vector<String[]> tabelaTokens = transdutor.extraiTokensArq("exercicio5.txt");
		String[] elem = new String[2];
		for(ListIterator<String[]> it = tabelaTokens.listIterator(); it.hasNext();){
			elem = it.next();
			System.out.format("%10s: <%s>\n", elem[0], elem[1]);
		}
	}
	
	public static void exercicio5_2(){
		System.out.println("Exercicio 5 - 2");
		AnalisadorLexico analisador = new AnalisadorLexico("defWirth.txt", "defWirth-reserv.txt");
		analisador.carregaTabelaSimbolos("exercicio5.txt");
		analisador.imprime();
	}
	
//	public static void exercicio5_3(){
//		System.out.println("Exercicio 5 - 3");
//		String[] nomes = new String[2];
//		nomes[0] = "GRAM.txt";
//		nomes[1] = "EXPR.txt";
//		AutomatoPilha automato = new AutomatoPilha(nomes);
//		
//		TransdutorFinito transdutor = new TransdutorFinito("defWirth.txt");
//		System.out.println(automato.avaliaTokens(transdutor.extraiTokensArq("exercicio5.txt")));
//	}
	
	public static void testeAutomato(){
		System.out.println("Teste Automato Finito");
		TransdutorFinito trans = new TransdutorFinito("automato1.txt");
		System.out.println(trans.avaliaCadeia("TAI I"));
		System.out.println(trans.avaliaCadeia("TIAI I"));
		System.out.println(trans.avaliaCadeia("TI I<>N9I"));
		System.out.println(trans.avaliaCadeia("TI I<>N9I)"));
	}
	
	public static void testeRotinas(){
		System.out.println("Teste Rotinas");
		String[] nomes = new String[2];
		String[] nomes2 = new String[2];
		nomes[0] = "GRAM.txt";
		nomes[1] = "EXPR.txt";
		nomes2[0] = "GRAM-RS.txt";
		nomes2[1] = "EXPR-RS.txt";
		
		TransdutorFinito transdutor = new TransdutorFinito("defWirth.txt");
		
		Vector<String[]> tabelaTokens = transdutor.extraiTokensArq("testeRotinas.txt");
		String[] elem = new String[2];
		for(ListIterator<String[]> it = tabelaTokens.listIterator(); it.hasNext();){
			elem = it.next();
			System.out.format("%10s: <%s>\n", elem[0], elem[1]);
		}
		
		AutomatoPilha automato = new AutomatoPilha(nomes, nomes2);
		System.out.println(automato.avaliaTokens(tabelaTokens));
	}
}
