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
					/*enquanto a pilha de operadores estiver vazia e sua 
					 * precedência for menor que os mais recentemente acessados*/
					while(!pilhaEsperaOperadores.empty() &&
							(pilhaEsperaOperadores.peek().getPrecedencia() >= 
								((Operador)elemento).getPrecedencia())){
							pilhaSaida.push(pilhaEsperaOperadores.pop());
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
		copiaPilha(pilhaSimbolos, pilhaSaida);
	}	
	
	/**
	 * Calcula o valor da expressão pós-fixada
	 * @param pilhaSimbolos
	 * @return
	 */
	private static Object calculaValorExpressaoPosFixa(
			Stack<Object> pilhaSimbolos) {
		
		invertePilha(pilhaSimbolos);
		
		
		Stack<Object> pilhaAuxiliar = new Stack<Object>();
		Object operandoEsquerda, operandoDireita, resultado;
		//para cada elemento da expressão, verifica seu tipo
		while(!pilhaSimbolos.empty()){
			Object elemento = pilhaSimbolos.pop();
			//se for um operando
			if(!(elemento instanceof Operador)){
				//empilha-o na pilha temporária
				pilhaAuxiliar.push(elemento);
			}
			//se for um operador
			else{
				//desempilha o(s) operando(s) e calcular o valor
				operandoDireita = pilhaAuxiliar.pop();
				if(!isOperadorUnario((Operador)elemento)){
					operandoEsquerda = pilhaAuxiliar.pop();
					resultado = opera(operandoEsquerda,(Operador)elemento,operandoDireita);
				}
				else{
					resultado = opera(null,(Operador)elemento,operandoDireita);
				}
				pilhaAuxiliar.push(resultado);
			}
		}
		//o valor que sobrou na pilha é o resultado da expressão
		return pilhaAuxiliar.pop();
	}
	
	/**
	 * Opera um ou dois elementos de acordo coma a operação passada.
	 * 
	 * @param operandoEsquerda
	 * @param elemento
	 * @param operandoDireita
	 * @return
	 */
	private static Object opera(Object operandoEsquerda, Operador operador,
			Object operandoDireita) {
		switch (operador) {
		case NOT_OP:
			return (!(Boolean)operandoDireita);
		case MULT_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Integer)operandoEsquerda * (Integer)operandoDireita;
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Double) operandoEsquerda * (Double)operandoDireita;
		case DIV_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Integer)operandoEsquerda / (Integer)operandoDireita;
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Double) operandoEsquerda / (Double)operandoDireita;
		case MOD_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Integer)operandoEsquerda % (Integer)operandoDireita;
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Double) operandoEsquerda % (Double)operandoDireita;
		case ADD_OP:
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Integer)operandoEsquerda + (Integer)operandoDireita;
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Double) operandoEsquerda + (Double)operandoDireita;
		case SUB_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Integer)operandoEsquerda - (Integer)operandoDireita;
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Double) operandoEsquerda - (Double)operandoDireita;
		case LT_OP:
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda < (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda < (Double)operandoDireita);
		case GT_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda > (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda > (Double)operandoDireita);
		case LE_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda <= (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda <= (Double)operandoDireita);
		case GE_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda >= (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda >= (Double)operandoDireita);
		case EQ_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda == (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda == (Double)operandoDireita);
			else if(operandoEsquerda instanceof String && operandoDireita instanceof String)
				return (Boolean)((String) operandoEsquerda == (String)operandoDireita);
			else if(operandoEsquerda instanceof Character && operandoDireita instanceof Character)
				return (Boolean)((Character) operandoEsquerda == (Character)operandoDireita);
		case NE_OP: 
			if(operandoEsquerda instanceof Integer && operandoDireita instanceof Integer)
				return (Boolean)((Integer)operandoEsquerda != (Integer)operandoDireita);
			else if(operandoEsquerda instanceof Double && operandoDireita instanceof Double)
				return (Boolean)((Double) operandoEsquerda != (Double)operandoDireita);
			else if(operandoEsquerda instanceof String && operandoDireita instanceof String)
				return (Boolean)((String) operandoEsquerda != (String)operandoDireita);
			else if(operandoEsquerda instanceof Character && operandoDireita instanceof Character)
				return (Boolean)((Character) operandoEsquerda != (Character)operandoDireita);
		case AND_OP: 
			if(operandoEsquerda instanceof Boolean && operandoDireita instanceof Boolean)
				return (Boolean)operandoEsquerda && (Boolean)operandoDireita;
		case OR_OP: 
			if(operandoEsquerda instanceof Boolean || operandoDireita instanceof Boolean)
				return (Boolean)operandoEsquerda || (Boolean)operandoDireita;
		case ASSIGN:
				if(operandoEsquerda.getClass() == operandoDireita.getClass())
					return (operandoEsquerda = operandoDireita);
		default:
			return null;
		}
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
		Stack<Object> pilhaIntermediaria = new Stack<Object>();
		Stack<Object> pilhaAuxiliar = new Stack<Object>();
		while(!pilhaSimbolos.empty()){
			pilhaIntermediaria.push(pilhaSimbolos.pop());
		}
		while(!pilhaIntermediaria.empty()){
			pilhaAuxiliar.push(pilhaIntermediaria.pop());
		}
		while(!pilhaAuxiliar.empty()){
			pilhaSimbolos.push(pilhaAuxiliar.pop());
		}
	}
	
	/**
	 * Verifica se o operador é unário.
	 * 
	 * @param elemento
	 * @return
	 */
	private static boolean isOperadorUnario(Operador operador) {
		if(operador == Operador.NOT_OP){
			return true;
		}
		else{
			return false;
		}
	}	
}


