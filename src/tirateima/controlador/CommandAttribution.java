package tirateima.controlador;

import java.util.Stack;

/**
 * Modela um comando de atribuição.
 * 
 * @author Luciano Santos
 */
public class CommandAttribution extends Command {
	
	private Stack<Object> var_stack;
	private Object value;
	
	public CommandAttribution(Stack<Object> var_stack, Object value) {
		this.var_stack = var_stack;
		this.value = value;
	}
	
	/**
	 * Executa o comando de atribuição setando o valor da variável (respeitado seu escopo). 
	 */
	public void execute(Controlador c)
			throws TiraTeimaLanguageException {
		setValue(c.mostrador, var_stack, value);
	}
}
