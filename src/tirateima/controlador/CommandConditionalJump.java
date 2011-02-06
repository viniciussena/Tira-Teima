package tirateima.controlador;

import java.util.List;
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
		Stack<Object> pilhaAuxiliar = new Stack<Object>();
		recuperaValoresVariaveis(pilhaAuxiliar,c);
		if((Boolean)AvaliadorDeExpressao.avaliar(pilhaAuxiliar) == Boolean.TRUE){
			c.jump = Boolean.TRUE;
			c.jumpTo = this.label;
		}
	}
	
	/**
	 * Recupera os valores das variáveis colocando no lugar da pilha em que estava
	 * a variável o seu valor corrente para cálculo.
	 * 
	 * @param pilhaSimbolos
	 */
	@SuppressWarnings("unchecked")
	private void recuperaValoresVariaveis(Stack<Object> pilhaAuxiliar,Controlador c) {
		Stack<Object> pilhaIntermediaria = new Stack<Object>();
		//passa os elementos para uma pilha intermediária
		while(!pilhaSimbolos.empty()){
			pilhaIntermediaria.push(pilhaSimbolos.pop());
		}
		/*pega cada elemento e coloca na pilha auxiliar, e devolve-o à pilha de 
		 * símbolos original.*/
		while(!pilhaIntermediaria.empty()){
			Object elemento = pilhaIntermediaria.pop();
			if(elemento instanceof List){
				pilhaAuxiliar.push(getValue(c, (List<Object>)elemento));
			}
			else{
				pilhaAuxiliar.push(elemento);
			}
			pilhaSimbolos.push(elemento);
		}
	}
}
