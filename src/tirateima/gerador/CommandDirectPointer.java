package tirateima.gerador;

import java.util.Stack;

import tirateima.gui.variaveis.Direcao;

public class CommandDirectPointer extends Command {
	
	private Stack<Object> var_stack;
	Direcao direcao;
	Integer tamanho;

	public CommandDirectPointer(Stack<Object> var_stack, Direcao direcao,
			Integer tamanho) {
		this.var_stack = var_stack;
		this.direcao = direcao;
		this.tamanho = tamanho;
	}

	@Override
	public void execute(Gerador g) throws TiraTeimaLanguageException {
		criarSeta(g.mostrador, var_stack, direcao, tamanho);
	}

}
