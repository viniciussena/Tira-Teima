package tirateima.gerador;

/**
 * Classe que modela um comando de jump para alterar a ordem de execução dos passos do 
 * Tira-Teima.
 * 
 * @author Vinícius
 *
 */
public class CommandJump extends Command {
	
	private String label;
	
	public CommandJump(String label){
		this.label = label;
	}
	
	@Override
	public void execute(Gerador g) throws TiraTeimaLanguageException {
		g.jump = Boolean.TRUE;
		g.jumpTo = this.label;
	}

}
