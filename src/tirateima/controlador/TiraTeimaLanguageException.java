package tirateima.controlador;

/**
 * Uma exceção lançada pelo lexer/parser/gerador.
 * Ao capturar um erro essa classe manda uma mensagem no console relatando o erro com uma
 * mensagem e depois lança a exceção, que será interceptada pelo java.
 * 
 * @author Luciano Santos
 */
public class TiraTeimaLanguageException extends Exception {
	private static final long serialVersionUID = 1L;

	private int line;
	private int column;
	
	/**
	 * pega a linha e coluna na qual a exceção ocorreu e manda a mensagem recebida.
	 * @param message
	 * @param line
	 * @param column
	 */
	public TiraTeimaLanguageException(String message, int line, int column) {
		super(message);
		
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
}
