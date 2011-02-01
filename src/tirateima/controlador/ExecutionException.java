package tirateima.controlador;

/**
 * Modela uma exceção na execução do código. Utilizada para capturar uma
 * exceção e se recuperar um estado anterior.
 * 
 * @author Vinícius
 *
 */
public class ExecutionException extends Exception {
	
	private static final long serialVersionUID = 1985;
	
	public ExecutionException(String mensagem, String comando){
		super("Erro de execução no comando '" + comando + "': " + mensagem + "");
	}
}
