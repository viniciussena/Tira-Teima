package tirateima.controlador;

import java.awt.Point;

import tirateima.gui.variaveis.Texto;

public class CommandInsertText extends Command {
	
	private String conteudo;
	private Integer tamanho;
	private Point posicao;
	
	public CommandInsertText(String conteudo, Integer tamanho, Point posicao){
		this.conteudo = conteudo;
		this.tamanho = tamanho;
		this.posicao = posicao;
	}
	
	@Override
	public void execute(Controlador c) throws TiraTeimaLanguageException {
		c.mostrador.adicionarTexto(new Texto(conteudo,tamanho,posicao));
		
	}

}
