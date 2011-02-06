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
	NOT_OP(1), 
	MULT_OP(2), 
	DIV_OP(2), 
	MOD_OP(2), 
	ADD_OP(3), 
	SUB_OP(3), 
	LT_OP(4), 
	GT_OP(4), 
	LE_OP(4), 
	GE_OP(4), 
	EQ_OP(5), 
	NE_OP(5), 
	AND_OP(6), 
	OR_OP(6), 
	ASSIGN(7);	
	
	private Integer precedencia;

	private Operador(Integer precedencia) {
		this.precedencia = precedencia;
	}
	
	public Integer getPrecedencia() {
		return precedencia;
	}	
}
