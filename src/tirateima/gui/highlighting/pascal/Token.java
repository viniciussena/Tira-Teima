package tirateima.gui.highlighting.pascal;

import java.util.HashMap;
import java.util.Map;

/**
 * Modela um token da linguagem Pascal.
 * 
 * @author Luciano Santos
 */
public class Token {
	private static Map<String, String> keywords;
	private static Map<String, String> types;
	
	private static final String palavras[] = {
	"and", "array", "begin", "case", "const", "div", "do",
	"downto", "else", "end", "file", "for", "function", "goto",
	"if", "in", "label", "mod", "nil", "not", "of", "packed",
	"procedure", "program", "record", "repeat", "set", "then",
	"to", "type", "until", "var", "while", "with"};

	private static final String tipos[] = {
	"integer", "shortint", "longint", "byte", "word",
	"real", "single", "double", "extended", "comp",
	"char", "string", "boolean"};
	
	static {
		keywords = new HashMap<String, String>();
		types = new HashMap<String, String>();
		
		for (int i = 0; i < palavras.length; i++) {
			keywords.put(palavras[i], palavras[i]);
		}
		
		for (int i = 0; i < tipos.length; i++) {
			types.put(tipos[i], tipos[i]);
		}
	}
	
	/* Constantes de identificação dos tokens */
	public static final int WHITESPACE					= 0;
	public static final int BEGINCOMMENT_CH				= 1;
	public static final int ENDCOMMENT_CH				= 2;
	public static final int BEGINCOMMENT_PAR			= 3;
	public static final int ENDCOMMENT_PAR				= 4;
	public static final int OTHER						= 5;
	public static final int EOLINE						= 6;
	public static final int PONT						= 7;
	public static final int IDENTIFIER					= 8;
	public static final int STRING						= 11;
	public static final int BAD_STRING					= 12;
	public static final int NUM							= 12;
	public static final int EOB							= 13;
	
	public static final int AND 						= 14;
	public static final int ARRAY 						= 15;
	public static final int BEGIN 						= 16;
	public static final int CASE 						= 17;
	public static final int CONST 						= 18;
	public static final int DIV 						= 19;
	public static final int DO							= 20;
	public static final int DOWNTO 						= 21;
	public static final int ELSE 						= 22;
	public static final int END 						= 23;
	public static final int FILE 						= 24;
	public static final int FOR 						= 25;
	public static final int FUNCTION					= 26;
	public static final int GOTO						= 27;
	public static final int IF							= 28;
	public static final int IN							= 29;
	public static final int LABEL						= 30;
	public static final int MOD							= 31;
	public static final int NIL							= 32;
	public static final int NOT							= 33;
	public static final int OF							= 34;
	public static final int PACKED						= 35;
	public static final int PROCEDURE					= 36;
	public static final int PROGRAM						= 37;
	public static final int RECORD						= 38;
	public static final int REPEAT						= 39;
	public static final int SET							= 40;
	public static final int THEN						= 41;
	public static final int TO							= 42;
	public static final int TYPE						= 43;
	public static final int UNTIL						= 44;
	public static final int VAR							= 45;
	public static final int WHILE						= 46;
	public static final int WITH						= 47;
	
	
	public static final int INTEGER						= 48;
	public static final int SHORTINT					= 49;
	public static final int LONGINT						= 50;
	public static final int BYTE						= 51;
	public static final int WORD						= 52;
	public static final int REAL						= 53;
	public static final int SINGLE						= 54;
	public static final int DOUBLE						= 55;
	public static final int EXTENDED					= 56;
	public static final int COMP						= 57;
	public static final int CHAR						= 58;
	public static final int STRING_TP					= 59;
	public static final int BOOLEAN						= 60;
	
	private int id_token;
	private String valor_token;
	
	public Token(){
		this.id_token = 0;
		this.valor_token = "";
	}
	
	public Token(int id_token, String valor_token){
		this.id_token = id_token;
		this.valor_token = valor_token;
	}
	
	public int getId(){
		return id_token;
	}
	
	public String getValor(){
		return valor_token;
	}
	
	public void setId(int id){
		this.id_token = id;
	}
	
	public void setValor(String valor){
		this.valor_token = valor;
	}
	
	public void appendToValor(char a) {
		valor_token += a;
	}
	
	public void appendToValor(String s) {
		valor_token += s;
	}
	
	public boolean ehPalavraChave() {
		return ((id_token >= AND) && (id_token <= WITH));
	}
	
	public boolean ehTipo(){
		return ((id_token >= INTEGER) && (id_token <= BOOLEAN));
	}
	
	public static boolean ehPalavraChave(String id) {
		return keywords.containsKey(id.toLowerCase());
	}
	
	public static boolean ehTipo(String id) {
		return types.containsKey(id.toLowerCase());
	}
}
