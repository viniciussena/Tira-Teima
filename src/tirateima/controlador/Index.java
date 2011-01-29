package tirateima.controlador;

/**
 * Modela um índice do Tira-Teima, que pode ser de matriz ou array de uma dimensão.
 * 
 * @author Luciano Santos
 */
public class Index {
	/** Valor do índice na primeira dimensão. */
	public int first;
	
	/** Valor do índice na segunda dimensão. */
	public int second;
	
	/** Define se o índice é de matriz. */
	public boolean isMatrix;
	
	/**
	 * construtor usado para criar um índice informando sua primeira e segunda dimensões, além de um valor informando se é matriz
	 * saber se é uma matriz.
	 * @param first
	 * @param second
	 * @param isMatrix
	 */
	public Index(int first, int second, boolean isMatrix) {
		this.first = first;
		this.second = second;
		this.isMatrix = isMatrix;
	}
	
	/**
	 * construtor usado quando se sabe que é uma matriz de duas dimensões
	 * @param first
	 * @param second
	 */
	public Index(int first, int second) {
		this(first, second, true);
	}
	
	/**
	 * construtor usado quando se sabe que o índice é um vetor
	 * @param first
	 */
	public Index(int first) {
		this(first, 0, false);
	}
	
	/**
	 * construtor usado quando se sabe que o índice é um vetor sem dimensões
	 */
	public Index() {
		this(0, 0, false);
	}
}
