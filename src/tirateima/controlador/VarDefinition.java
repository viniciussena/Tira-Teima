package tirateima.controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import tirateima.parser.TiraTeimaParserConstants;

/**
 * Contém uma definição para criação de variáveis.
 * Implementa um TiraTeimaParserConstants que é faz parte do parser.
 * 
 * @author Luciano Santos
 * @author Andrew
 */
public class VarDefinition implements TiraTeimaParserConstants {
	/** Atributos da variável a ser mostrada na tela */
	private Type type;
	private String name;
	private Index index;
	private Color color;
	private Color corExterna;
	private Dimension dimension;
	private Point posicao;
	
	/** Construtor de variável padrão */
	public VarDefinition(Type type, String name, Index index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}
	
	/** Construtor de variável customizada */
	public VarDefinition(Type type, String name, Index index, Color color, Dimension dimension, Point posicao) {
		this.type = type;
		this.name = name;
		this.index = index;
		this.color = color;
		this.dimension = dimension;
	}
	
	/** Construtor de variável mais customizada */
	public VarDefinition(Type type, String name, Index index, Color color, Color corExterna, Dimension dimension, Point posicao) {
		this.type = type;
		this.name = name;
		this.index = index;
		this.color = color;
		this.corExterna = corExterna;
		this.dimension = dimension;
		this.posicao = posicao;
	}

	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public Index getIndex() {
		return index;
	}

	public Color getColor() {
		return color;
	}
	
	public Color getcorExterna() {
		return corExterna;
	}

	public Dimension getDimension() {
		return dimension;
	}
	
	public Point getPosicao() {
		return posicao;
	}
	
}
