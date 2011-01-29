package tirateima.controlador;

/**
 * Modela um comando de fim de função.
 */
public class CommandEndFunction extends Command {
	/**
	 * Executa o comando de fim de função, chamando diretamente o mostrador. 
	 */
	public void execute(Controlador c) throws TiraTeimaLanguageException {
		c.mostrador.endFunction();
	}
}
