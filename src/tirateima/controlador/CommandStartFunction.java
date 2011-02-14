package tirateima.controlador;

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
	public void execute(Controlador c) throws TiraTeimaLanguageException {
		/** Verifica se a função foi declarada */
		if (!c.declared_functions.containsKey(name)) {
			gerarErro("Função '" + name + "' não declarada!");
		}
		/** Para cada argumento */
		List<Object> values = new ArrayList<Object>();
		for (Object arg : args) {
			/** adiciona este à lista de argumentos*/
			if (arg instanceof List<?>)
				values.add(getValue(c, (List<Object>) arg));
			else
				values.add(arg);
		}
		/** Inicia a função no mostrador  */
		c.mostrador.startFunction(c.declared_functions.get(name)
				.newFunction(c, values));
	}
	
	@Override
	public void revert(Controlador c) throws TiraTeimaLanguageException,
			ExecutionException {
		// TODO Auto-generated method stub
		
	}
}
