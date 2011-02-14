package tirateima.controlador;

import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * Modela um comando de entrada do usuário.
 * 
 * @author Vinícius
 *
 */
public class CommandReadInput extends Command{
	
	private Stack<Object> var_stack;
	private Object value;
	
	public CommandReadInput(Stack<Object> var_stack) {
		this.var_stack = var_stack;
	}
	
	/**
	 * Executa o comando de leitura do usuário, setando um valor recebido na 
	 * variável.
	 * @throws ExecutionException 
	 */
	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException, ExecutionException {
		//Copia pilha para possível restauração.
		Stack<Object> pilhaAux = new Stack<Object>();
		pilhaAux = copia(var_stack);
		//Recebe entrada do usuário.
		value = JOptionPane.showInputDialog(null, 
				"Entre com o valor de " + var_stack.get(0) + ":", 
				"Entrada de Dados", 
				JOptionPane.DEFAULT_OPTION, 
				null, 
				null, 
				null);
		//Informa condição de prosseguimento
		if(value == null){
			JOptionPane.showMessageDialog(null, 
					"Para prosseguir, o valor deverá ser informado.", 
					"Aviso", 
					JOptionPane.WARNING_MESSAGE);
			throw new ExecutionException("Valor não informado.", this.getClass().toString());
		}
		
		//Tenta setar o valor de entrada para o usuário.
		try{
			setValue(c.mostrador, var_stack, value);
		}
		catch(Exception e){
			var_stack = pilhaAux;
			setValue(c.mostrador, var_stack, null);
			JOptionPane.showMessageDialog(null, 
					"Valor Incorreto!", 
					"Erro", 
					JOptionPane.ERROR_MESSAGE);
			//TODO: lançar mensagens diferentes para os diferentes tipos de exceção (mais didático).
			throw new ExecutionException("Valor incorreto.", this.getClass().toString());
		}
	}
	
	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Copia pilha para possível restauração.
	 * 
	 * @param var_stack
	 * @return
	 */
	private Stack<Object> copia(Stack<Object> pilha) {
		Stack<Object> pilhaCopia = new Stack<Object>();
		Stack<Object> pilhaAux = new Stack<Object>();
		Object elemento;
		//Passa elementos para uma pilha intermediária
		while(!pilha.empty()){
			elemento = pilha.pop();
			pilhaAux.push(elemento);
		}
		//Copia pilha e restaura pilha original
		while(!pilhaAux.empty()){
			elemento = pilhaAux.pop();
			pilhaCopia.push(elemento);
			pilha.push(elemento);
		}
		return pilhaCopia;
	}
	
}
