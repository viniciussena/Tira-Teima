package tirateima.gerador;

import java.awt.Point;
import java.util.Stack;

public class CommandDirectPointer extends Command {
	
	private Stack<Object> var_stack;
	Point posicaoApontada;

	public CommandDirectPointer(Stack<Object> var_stack, Point posicaoApontada) {
		this.var_stack = var_stack;
		this.posicaoApontada = posicaoApontada;
	}

	@Override
	public void execute(Gerador g) throws TiraTeimaLanguageException {
		criarSeta(g.mostrador, var_stack, posicaoApontada);
	}

}
