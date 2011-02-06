package tirateima.controlador;

import java.util.List;
import java.util.Stack;

/**
 * Modela um comando de atribuição.
 * 
 * @author Luciano Santos
 */
public class CommandAttribution extends Command {
	
	private Stack<Object> var_stack;
	private Stack<Object> pilhaSimbolos;
	
	public CommandAttribution(Stack<Object> var_stack, 
			Stack<Object> pilhaSimbolos) {
		this.var_stack = var_stack;
		this.pilhaSimbolos = pilhaSimbolos;
	}
	
	/**
	 * Executa o comando de atribuição setando o valor da variável (respeitado 
	 * seu escopo). 
	 */
	public void execute(Controlador c)
			throws TiraTeimaLanguageException {
		Stack<Object> pilhaAuxiliar = new Stack<Object>();
		recuperaValoresVariaveis(pilhaAuxiliar, c);
		Object value = AvaliadorDeExpressao.avaliar(pilhaAuxiliar);
		setValue(c.mostrador, var_stack, value);
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
