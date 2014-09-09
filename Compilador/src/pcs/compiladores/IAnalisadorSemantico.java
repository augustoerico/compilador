package pcs.compiladores;
import java.util.Vector;

public interface IAnalisadorSemantico extends IAutomatoPilha {
	
	public boolean geraCodigo(Vector<String[]> tokens);
	
}
