package pcs.compiladores;

public class RotinasSemanticas {
	
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
	
	private static class Rotinas{
		
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
