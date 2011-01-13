package tirateima.gerador;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que modela um comando do tipo inicia função
 */
public class CommandStartFunction extends Command {
	
	/** nome da função */
	private String name;
	/** lista de parâmetros de entrada da função */
	private List<Object> args;
	
	public CommandStartFunction(String name, List<Object> args) {
		this.name = name;
		this.args = args;
	}
	
	/**
	 * Executa o início da função.
	 * 
	 * Recebe o gerador e executa o comando de startFunction.
	 */
	@SuppressWarnings("unchecked")
	public void execute(Gerador g) throws TiraTeimaLanguageException {
		/** Verifica se a função foi declarada */
		if (!g.declared_functions.containsKey(name)) {
			gerarErro("Função '" + name + "' não declarada!");
		}
		/** Para cada argumento */
		List<Object> values = new ArrayList<Object>();
		for (Object arg : args) {
			/** adiciona este à lista de argumentos*/
			if (arg instanceof List<?>)
				values.add(getValue(g, (List<Object>) arg));
			else
				values.add(arg);
		}
		/** Inicia a função no mostrador  */
		g.mostrador.startFunction(g.declared_functions.get(name)
				.newFunction(g, values));
	}
}
