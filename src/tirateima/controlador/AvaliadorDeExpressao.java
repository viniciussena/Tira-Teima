package tirateima.controlador;

import java.util.Stack;


/**
 * Avalia expressões utilizando o método criado por Jan Lukasienwicz, o qual se 
 * baseia em transformar uma expressão infixada em uma expressão pós fixada e 
 * então efetuar o cálculo da expressão nessa forma.
 * 
 * @author Vinícius
 */
public class AvaliadorDeExpressao {	

	/**
	 * Avalia uma expressão retornando seu cálculo.
	 * 
	 * Para isso recebe uma pilha de símbolos que representam a expressão lida 
	 * no roteiro. Copia a pilha antes de computar o valor da expressão para 
	 * caso seja necessário re-executar o comado que chamou a avaliação.
	 * 
	 * Para tanto:
	 * 1 Converte a expressão para a forma pós-fixada.
	 * 2 Calcula o valor da expressão pós-fixada
	 *       
	 * Exemplo: 
	 * a + b * c - d       //expressão original
	 * abc*+d-             // expressão na notação pós fixada
	 * ar+d-               //r <- b*c 
	 * xd-                 //x <- a+r;
	 * resultado           //resultado <- x-d
	 * 
	 * Para explicação detalhada do algoritmo, consultar o arquivo
	 * avaliacao_de_expressoes_em_estrutura_de_dados.pdf
	 * localizado nas referências da pasta doc. 
	 * 
	 * @param pilhaSimbolos
	 * @return
	 */
	 public static Object avaliar(Stack<Object> pilhaSimbolos) {
		Stack<Object> pilhaAuxiliar = new Stack<Object>();
		copiaPilha(pilhaAuxiliar,pilhaSimbolos);
		converteExpressaoParaPosFixa(pilhaAuxiliar);
		return calculaValorExpressaoPosFixa(pilhaAuxiliar);
	}
	 
	/**
	 * Converte uma expressão para pós-fixada.
	 * 
	 * @param pilhaSimbolos
	 */
	private static void converteExpressaoParaPosFixa(Stack<Object> pilhaSimbolos) {
		
		invertePilha(pilhaSimbolos);
		
		Stack<Operador> pilhaEsperaOperadores = new Stack<Operador>();
		Stack<Object> pilhaSaida = new Stack<Object>();
		
		//para cada elemento
		Object elemento;
		while(!pilhaSimbolos.empty()){
			elemento = pilhaSimbolos.pop();
			//caso o elemento seja um operador 
			if(elemento instanceof Operador){
				//se for um parênteses de abertura
				if((Operador)elemento == Operador.OPEN_PAR){
					//empilha-o na pilha de operadores
					pilhaEsperaOperadores.push((Operador)elemento);
				}
				//se for um parênteses de fechamento
				else if((Operador)elemento == Operador.CLOSE_PAR){
					/*remove os símbolos da pilha de operadores e os copia para a
					 * saída até encontrar um parênteses de abertura
					 */
					while(pilhaEsperaOperadores.peek() != Operador.OPEN_PAR){						
						pilhaSaida.push(pilhaEsperaOperadores.pop());
					}
					//tira o parênteses de abertura encontrado da pilha
					pilhaEsperaOperadores.pop();
				} else {
					//enquanto a pilha de operadores estiver vazia
					while(!pilhaEsperaOperadores.empty()){
						//e sua precedência for menor que os mais recentemente acessados
						if(pilhaEsperaOperadores.peek().getPrecedencia() >= 
							((Operador)elemento).getPrecedencia()){
							//desempilhe os operadores e copie-os na saída
							pilhaSaida.push(pilhaEsperaOperadores.pop());
						}
					}
					//empilhe o operador encontrado
					pilhaEsperaOperadores.push((Operador)elemento);
				}
			}
			//caso o elemento seja um operando			
			else{
				//empilha-o
				pilhaSaida.push(elemento);
			}
		}
		//coloca os operadores restantes na pilha de saída
		while(!pilhaEsperaOperadores.empty()){
			pilhaSaida.push(pilhaEsperaOperadores.pop());
		}
		//atualiza a pilha de símbolos com sua versão pós fixada
		pilhaSimbolos = pilhaSaida;
	}	
	
	/**
	 * Calcula o valor da expressão pós-fixada
	 * @param pilhaSimbolos
	 * @return
	 */
	private static Object calculaValorExpressaoPosFixa(
			Stack<Object> pilhaSimbolos) {
		//TODO: implementar cálculo de valor de expressão pós-fixada.
		return null;
	}
	
	/**
	 * Copia a pilha de símbolos para não alterar seu valor e poder ser 
	 * re-utilizada.
	 * 
	 * @param pilhaAuxiliar
	 * @param pilhaSimbolos
	 */
	private static void copiaPilha(Stack<Object> pilhaAuxiliar,
			Stack<Object> pilhaSimbolos) {
		Stack<Object> pilhaIntermediaria = new Stack<Object>();
		//Empilha elementos na pilha intermediária
		while(!pilhaSimbolos.empty()){
			pilhaIntermediaria.push(pilhaSimbolos.pop());
		}
		//Empilha elementos na pilha auxiliar e na pilha de símbolos de volta.
		Object o;
		while(!pilhaIntermediaria.empty()){
			o = pilhaIntermediaria.pop();
			pilhaAuxiliar.push(o);
			pilhaSimbolos.push(o);
		}
	}
	
	/**
	 * Inverte uma pilha.
	 * 
	 * @param pilhaSimbolos
	 */
	private static void invertePilha(Stack<Object> pilhaSimbolos) {
		Stack<Object> pilhaAux = new Stack<Object>();
		while(!pilhaSimbolos.empty()){
			pilhaAux.push(pilhaSimbolos.pop());
		}
		pilhaSimbolos = pilhaAux;
	}
}


