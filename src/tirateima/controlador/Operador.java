package tirateima.controlador;

/**
 * Enumero os tipos de operadores e outros símbolos para avaliação de 
 * expressões, seguindo nomenclatura mnemônica parecida com assembly.
 * 
 * Possui ainda uma precedência, a qual imita as precedências do C ANSI.
 * 
 * @author Vinícius
 */
public enum Operador {
	OPEN_PAR(0), 
	CLOSE_PAR(0),	
	AND_OP(1), 
	OR_OP(1), 
	NE_OP(2), 
	EQ_OP(2), 
	GT_OP(3), 
	LE_OP(3), 
	LT_OP(3), 
	GE_OP(3), 
	ADD_OP(4), 
	SUB_OP(4), 
	MULT_OP(5), 
	DIV_OP(5), 
	MOD_OP(5), 
	NOT_OP(6); 
	
	private Integer precedencia;

	private Operador(Integer precedencia) {
		this.precedencia = precedencia;
	}
	
	public Integer getPrecedencia() {
		return precedencia;
	}	
}
