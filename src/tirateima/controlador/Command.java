package tirateima.controlador;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import tirateima.Par;
import tirateima.gui.variaveis.Mostrador;
import tirateima.gui.variaveis.Seta;
import tirateima.gui.variaveis.VarArray;
import tirateima.gui.variaveis.VarBoolean;
import tirateima.gui.variaveis.VarChar;
import tirateima.gui.variaveis.VarInteger;
import tirateima.gui.variaveis.VarMatriz;
import tirateima.gui.variaveis.VarPointer;
import tirateima.gui.variaveis.VarReal;
import tirateima.gui.variaveis.VarRecord;
import tirateima.gui.variaveis.VarString;
import tirateima.gui.variaveis.Variavel;

/**
 * Uma abstração que modela um comando do Tira-Teima.
 * 
 * @author Luciano Santos
 */
public abstract class Command {
	
	/**
	 * Executa esse comando, alterarndo os estados dos componentes.
	 * 
	 * @param c O gerador de estados.
	 * @param steps A lista de estados sendo gerada. 
	 * 
	 * @throws TiraTeimaLanguageException
	 * @throws ExecutionException 
	 */
	public abstract void execute(Controlador c)
		throws TiraTeimaLanguageException, ExecutionException;
	
	/**
	 * Reverte o comando executado, quando for necessário para manter
	 * a consistência da visualização.
	 * @param c
	 * @throws TiraTeimaLanguageException
	 * @throws ExecutionException
	 */
	public abstract void revert(Controlador c)
		throws TiraTeimaLanguageException, ExecutionException;
	
	/**
	 * Cria uma nova exceção com a mensagem especificada.
	 * 
	 * @param msg
	 * 
	 * @return A exceção criada.
	 */
	protected void gerarErro(String msg) throws TiraTeimaLanguageException {
		throw new TiraTeimaLanguageException(msg, 0, 0);
	}
	
	/**
	 * Cria uma nova variável, dada uma VarDefinition.
	 * 
	 * @param g O gerador.
	 * @param var_def A definição da variável.
	 * 
	 * @return A variável criada.
	 * 
	 * @throws TiraTeimaLanguageException
	 */
	protected Variavel newVar(Controlador c, VarDefinition var_def)
			throws TiraTeimaLanguageException {
		
		Variavel v = null;
		Index i;
		
		/** Cria uma nova variável de acordo com seu tipo */
		Type t = var_def.getType();
		switch (t.getId()) {
			case INTEGER:
				v = new VarInteger(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case REAL:
				v = new VarReal(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case CHAR:
				v = new VarChar(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case STRING:
				v = new VarString(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case BOOLEAN:
				v = new VarBoolean(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case POINTER:
				v = new VarPointer(var_def.getName(), var_def.getColor(), var_def.getDimension(), var_def.getPosicao(), var_def.getMostraNome());
				break;
			case RECORD:
				if (!c.declared_records.containsKey(t.getName()))
					gerarErro("Tipo '" + t.getName() + "' não foi declarado!");
				
				RecordDefinition def = c.declared_records.get(t.getName()).clone();
				v = new VarRecord(t.getName(), var_def.getName(), def.fields, var_def.getColor(), 
						var_def.getcorExterna(), var_def.getDimension(), var_def.getPosicao(),var_def.getMostraNome());
				break;
		}
		/** Se for uma variável composta (array ou registro) entra */
		if ((i = var_def.getIndex()) != null) {
			try {
				if (i.isMatrix) {
					v = new VarMatriz(
						var_def.getName(),
						i.first, i.second,
						v, var_def.getColor(), 
						var_def.getcorExterna(), var_def.getDimension(), var_def.getPosicao());
				} else {
					v = new VarArray(var_def.getName(), i.first, v, var_def.getColor(), 
							var_def.getcorExterna(), var_def.getDimension(), var_def.getPosicao(),var_def.getMostraNome());
				}
			} catch (Exception e) {
				gerarErro("Falha ao criar variável '" + var_def.getName() + "'.");
			}
		}
		/** Retorna a nova variável formatada. */
		return v;
	}
	
	/**
	 * Pega o valor de uma variável no mostrador, incluindo-se índices de
	 * arrays e matrizes e campos de records.
	 * 
	 * @param g
	 * @param var_stack
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static public Object getValue(Controlador c, List<Object> var) {
		Object aux, value;
		
		ListIterator<Object> i = var.listIterator();
		value = c.mostrador.getCopiaVariavel((String) i.next()).getValor();
		
		while(i.hasNext()) {
			aux = i.next();
			
			if (aux instanceof String) {
				Map<String, Object> map = (Map<String, Object>) value;
				value = map.get((String) aux);
			} else {
				Index index = (Index) aux;
				Object values[] = (Object[]) value;
				
				if (index.isMatrix) {
					value = values[index.first - 1];
				} else {
					value = values[index.first - 1];
				}
			}
		}
		
		return value;
	}
	
	/**
	 * Insere uma seta na variavel de pilha para representar um ponteiro.
	 * Possui direcao e tamanho especificados.
	 * @param mostrador
	 * @param var_stack
	 * @param direcao
	 * @param tamanho
	 */
	protected void criarSeta(Mostrador mostrador, Stack<Object> var_stack,
			Point posicaoApontada) throws TiraTeimaLanguageException{
		//Pilha auxiliar para armazenar temporariamente os valores da pilha.
		Stack<Object> pilhaAux = new Stack<Object>();
		
		Object parent;
		
		parent = var_stack.pop();
		pilhaAux.push(parent);
				
		String nome_var = (String) parent;
		if (!mostrador.hasVariavel(nome_var))
			gerarErro("Variavel '" + nome_var + "' não foi declarada!");
		mostrador.adicionarSeta(nome_var,new Seta(nome_var,posicaoApontada));
		mostrador.modificarVariavel(nome_var, "");
		restaurarPilha(var_stack,pilhaAux);
	}
	
	/**
	 * Insere uma seta na variavel de pilha para representar um ponteiro.
	 * Possui direcao e tamanho especificados.
	 * @param mostrador
	 * @param var_stack
	 * @param direcao
	 * @param tamanho
	 */
	protected void removerSeta(Mostrador mostrador, Stack<Object> var_stack) throws TiraTeimaLanguageException{
		//Pilha auxiliar para armazenar temporariamente os valores da pilha.
		Stack<Object> pilhaAux = new Stack<Object>();
		
		Object parent;
		
		parent = var_stack.pop();
		pilhaAux.push(parent);
				
		String nome_var = (String) parent;
		/*if (!mostrador.hasSeta(nome_var))
			gerarErro("Variavel '" + nome_var + "' não foi declarada!");*/
		mostrador.removerSeta(nome_var);
		restaurarPilha(var_stack,pilhaAux);
	}
	
	
	
	/**
	 * Seta o valor de uma variável no mostrador, respeitando o escopo.
	 * 
	 * @param mostrador
	 * @param var_stack
	 * @param value
	 * 
	 * @throws TiraTeimaLanguageException
	 */
	protected void setValue(Mostrador mostrador, Stack<Object> var_stack, Object value)
			throws TiraTeimaLanguageException {
		//pilha que armazenará temporariamente os valores desempilhados para posterior restauração
		Stack<Object> pilhaAux = new Stack<Object>();
		
		Object child, parent;
		
		parent = child = var_stack.pop();
		pilhaAux.push(parent);
		while (!var_stack.empty()) {
			parent = var_stack.pop();
			pilhaAux.push(parent);
			if (child instanceof Index) {
				Index i = (Index) child;
				if (i.isMatrix) {
					Map<Par<Integer, Integer>, Object> map = new HashMap<Par<Integer, Integer>, Object>();
					map.put(
						new Par<Integer, Integer>(i.first - 1, i.second - 1),
						value);
					value = map;
				} else {
					Map<Integer, Object> map = new HashMap<Integer, Object>();
					map.put(i.first - 1, value);
					value = map;
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put((String) child, value);
				value = map;
			}
			
			child = parent;
		}
		
		String nome_var = (String) parent;
		if (!mostrador.hasVariavel(nome_var))
			gerarErro("Variavel '" + nome_var + "' não foi declarada!");
		try {
			mostrador.modificarVariavel(nome_var, value);
			mostrador.removerSeta(nome_var);
		} catch (ClassCastException e) {
			gerarErro("Atribuição inválida!");
		}
		restaurarPilha(var_stack,pilhaAux);
	}

	/**
	 * Restaura uma pilha a partira de um a pilha axiliar.
	 * @param var_stack - pilha a ser restaurada
	 * @param pilhaAux - pilha usada para armazernar temporariamente os valores.
	 */
	public void restaurarPilha(Stack<Object> var_stack, Stack<Object> pilhaAux) {
		while(!pilhaAux.empty()){
			var_stack.push(pilhaAux.pop());
		}
	}
}
