package tirateima.controlador;

import java.util.Stack;

public class CommandConditionalJump extends Command {
	
	private String label;
	private Stack<Object> pilhaSimbolos;
	
	public CommandConditionalJump(String label, Stack<Object> pilhaSimbolos) {
		this.label = label;
		this.pilhaSimbolos = pilhaSimbolos;
	}

	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {		
		/** Executa o Jump se a expressão for verdadeira */
		if((Boolean)AvaliadorDeExpressao.avaliar(pilhaSimbolos) == Boolean.TRUE){
			c.jump = Boolean.TRUE;
			c.jumpTo = this.label;
		}
	}
}
