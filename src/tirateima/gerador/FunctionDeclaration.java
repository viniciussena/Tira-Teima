package tirateima.gerador;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import tirateima.gui.variaveis.Function;
import tirateima.gui.variaveis.Variavel;
import tirateima.parser.TiraTeimaParserConstants;

/**
 * Armazena uma declaração de função a ser executada pelo Tira-Teima.
 * Extende a classe comando, portanto pode ser executada.
 * 
 * @author Luciano Santos
 */
public class FunctionDeclaration extends Command
		implements TiraTeimaParserConstants {
	
	private String name;
	/** Lista de variáveis passadas por parâmetro */
	private List<VarDefinition> param;
	private Type type;
	/** Lista de variáveis locais */
	private List<VarDefinition> local_vars;
	
	public FunctionDeclaration(
			String name,
			List<VarDefinition> param,
			Type type,
			List<VarDefinition> local_vars) {
		this.name = name;
		this.param = param;
		this.type = type;
		this.local_vars = local_vars;
	}
	
	/**
	 * Executa declaração de função.
	 * 
	 * Recebe o gerador de estados e adiciona a declaração no mapa de funções declaradas.
	 * @param Gerador g
	 */
	public void execute(Gerador g)
			throws TiraTeimaLanguageException {
		/** Testa se já não há declaração com o nome dado */
		if (g.declared_functions.containsKey(name))
			gerarErro("Função '" + name + "' redeclarada!");
		/** Declara a função colocando-a na lista de declarações do gerador. */
		g.declared_functions.put(name, this);
	}
	
	/**
	 * Cria uma nova função, com os parâmetros passados por valor.
	 * 
	 * @param g
	 * @param args
	 * @return
	 * @throws TiraTeimaLanguageException
	 */
	public Function newFunction(Gerador g, List<Object> args)
			throws TiraTeimaLanguageException {
		/** Testa se foram passadas todas as variáveis */
		if (args.size() != param.size())
			gerarErro("Número de parâmetros inválido!");
		
		List<Variavel> params = new ArrayList<Variavel>();
		ListIterator<Object> i = args.listIterator();
		int cont = 1;
		Variavel vaux;
		/** Para cada nova definição de variável passada por parâmetro */
		for (VarDefinition v : param) {
			/** Cria uma nova variável */
			vaux = newVar(g, v);
			try {
				vaux.setValor(i.next());
			} catch (ClassCastException e) {
				gerarErro("Argumento " + cont + " passado à função " + name
						+ " incompatível.");
			}
			params.add(vaux);
			cont++;
		}
		
		List<Variavel> vars = new ArrayList<Variavel>();
		/** Para cada nova definição de variável local */
		for (VarDefinition v : local_vars) {
			/** Cria uma nova variável */
			vars.add(newVar(g, v));
		}
		
		/** Retona uma nova função */
		return new Function(name, type, params, vars);
	}
}
