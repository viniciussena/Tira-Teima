package tirateima.gui.highlighting.pascal;

/**
 * Analisador léxico para identificar tokens de pascal.
 * 
 * @author Luciano
 */
public class Anlex {
	/** Buffer de caracteres interno.*/
	private Buffer buffer;
	
	/**Contantes dos estados do analisador.*/
	private static final int EST_COMENT_CHAVE_01 	= 1;
	private static final int EST_COMENT_PAR_01 		= 2;
	private static final int EST_COMENT_PAR_02		= 3;
	private static final int EST_COMENT_FIM_CH_01	= 4;
	private static final int EST_COMENT_FIM_PAR_01	= 5;
	private static final int EST_COMENT_FIM_PAR_02 	= 6;
	private static final int EST_IDENT_01 			= 7;
	private static final int EST_IDENT_02 			= 8;
	private static final int EST_EOLINE_01 			= 9;
	private static final int EST_OTHER_01 			= 10;
	private static final int EST_LITERAL_01 		= 11;
	private static final int EST_LITERAL_02 		= 12;
	private static final int EST_NUM_01				= 13;
	private static final int EST_NUM_02				= 14;
	private static final int EST_NUM_03 			= 15;
	private static final int EST_PONT_01			= 20;
	private static final int EST_EOB_01				= 21;
	
	/**
	 * Construtor do analisador léxico.
	 * 
	 * @param b Buffer de caracteres a ser utilizado pelo analisador.
	 */
	public Anlex(String b){
		this.buffer = new Buffer(b);
	}
	
	/**
	 * ALtera o estado de procura em caso de falha.
	 * 
	 * Se não for possível encontrar o token procurado no estado atual,
	 * este método redireciona o analisador para o próximo estado de procura.
	 * 
	 * @param estado Estado atual do analisador.
	 * 
	 * @return Novo estado de procura.
	 */
	private int falhar(int estado){
		switch(estado){
			case EST_COMENT_CHAVE_01:
				return EST_COMENT_PAR_01;
			case EST_COMENT_PAR_01:
				return EST_COMENT_FIM_CH_01;
			case EST_COMENT_FIM_CH_01:
				return EST_COMENT_FIM_PAR_01;
			case EST_COMENT_FIM_PAR_01:
				return EST_IDENT_01;
			case EST_IDENT_01:
				return EST_NUM_01;
			case EST_NUM_01:
			case EST_NUM_02:
				return EST_PONT_01;
			case EST_PONT_01:
				return EST_EOLINE_01;
			case EST_EOLINE_01:
				return EST_LITERAL_01;
			case EST_LITERAL_01:
				return EST_EOB_01;
			case EST_EOB_01:
				return EST_OTHER_01;
		}
		return 0;
	}
	
	public Token getToken(){
		Token temp = new Token();
		int estado = EST_COMENT_CHAVE_01;
		while(true){
			int a = getchar();
			switch(estado){
				case EST_COMENT_CHAVE_01:
					if(a == '{'){
						temp.setId(Token.BEGINCOMMENT_CH);
						temp.setValor("{");
						return temp;
					}
					ungetchar(a);
					estado = falhar(estado);
					break;
				
				case EST_COMENT_PAR_01:
					if(a == '('){
						temp.appendToValor((char) a);
						estado = EST_COMENT_PAR_02;
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;

				case EST_COMENT_PAR_02:
					if(a == '*'){
						temp.setId(Token.BEGINCOMMENT_PAR);
						temp.setValor("(*");
					}else{
						ungetchar(a);
						temp.setId(Token.OTHER);
					}
					return temp;
				
				case EST_COMENT_FIM_CH_01:
					if(a == '}'){
						temp.setId(Token.ENDCOMMENT_CH);
						temp.setValor("}");
						return temp;
					}
					ungetchar(a);
					estado = falhar(estado);
					break;
				case EST_COMENT_FIM_PAR_01:
					if(a == '*'){
						temp.appendToValor((char) a);
						estado = EST_COMENT_FIM_PAR_02;
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;
				
				case EST_COMENT_FIM_PAR_02:
					if(a == ')'){
						temp.setId(Token.ENDCOMMENT_PAR);
						temp.setValor("*)");
					}else{
						ungetchar(a);
						temp.setId(Token.OTHER);
					}
					return temp;
					
				case EST_IDENT_01:
					if((Character.isLetter(a))||(a == '_')){
						temp.appendToValor((char) a);
						estado = EST_IDENT_02;
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;
				
				case EST_IDENT_02:
					if((Character.isLetterOrDigit(a)) || (a == '_')){
						temp.appendToValor((char) a);
					}else{
						ungetchar(a);
						temp.setId(Token.IDENTIFIER);
						return temp;
					}
					break;
				
				case EST_NUM_01:
					if(Character.isDigit(a)){
						do{
							temp.appendToValor((char) a);
							a = getchar();
						}while(Character.isDigit(a));
						
						if((a == '.')){
							estado = EST_NUM_02;
						}else if((a == 'e')||(a == 'E')){
							estado = EST_NUM_03;
						}else{
							ungetchar(a);
							temp.setId(Token.NUM);
							return temp;
						}
					}else if(a == '.'){
						estado = EST_NUM_02;
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;
				
				case EST_NUM_02:
					if(Character.isDigit(a)){
						temp.appendToValor('.');
						do{
							temp.appendToValor((char) a);
							a = getchar();
						}while(Character.isDigit(a));
						
						if((a == 'e')||(a == 'E')){
							estado = EST_NUM_03;
						}else{
							ungetchar(a);
							temp.setId(Token.NUM);
							return temp;
						}
					} else {
						ungetchar(a);
						ungetchar('.');
						
						if(temp.getValor().length() > 0){
							temp.setId(Token.NUM);
							return temp;
						}else{
							estado = falhar(estado);
						}
					}
					break;
				
				case EST_NUM_03:
					if(Character.isDigit(a)){
						do{
							temp.appendToValor((char) a);
							a = getchar();
						}while(Character.isDigit(a));
						
						ungetchar(a);
						temp.setId(Token.NUM);
						return temp;
					}else if((a == '-')||(a == '+')){
						a = getchar();
						
						if(Character.isDigit(a)){
							do{
								temp.appendToValor((char) a);
								a = getchar();
							}while(Character.isDigit(a));
							
							ungetchar(a);
							temp.setId(Token.NUM);
							return temp;
						}else{
							ungetchar(a);
							ungetchar('+');
							ungetchar('e');
							
							temp.setId(Token.NUM);
							return temp;
						}
					}else{
						ungetchar(a);
						ungetchar('e');
						temp.setId(Token.NUM);
						return temp;
					}
				
				case EST_PONT_01:
					switch(a){
						case '.':
							temp.appendToValor('.');
							a = getchar();
							if (a != '.') {
								ungetchar(a);
							} else {
								temp.appendToValor('.');
							}
							
							temp.setId(Token.PONT);
							return temp;
						case ':':
						case ';':
						case ',':
						case '[':
						case ']':
							temp.appendToValor((char) a);
							temp.setId(Token.PONT);
							return temp;
						default:
							ungetchar(a);
							estado = falhar(estado);
					}
					break;
				case EST_EOLINE_01:
					if(a == '\n'){
						temp.setId(Token.EOLINE);
						temp.setValor("\n");
						return temp;
					}
					ungetchar(a);
					estado = falhar(estado);
					break;
				case EST_LITERAL_01:
					if(a == '\''){
						temp.appendToValor((char) a);
						estado = EST_LITERAL_02;
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;
				
				case EST_LITERAL_02:
					if(a == '\''){
						temp.appendToValor((char) a);
						temp.setId(Token.STRING);
						return temp;
					}else if(a == '\n'){
						ungetchar(a);
						temp.setId(Token.STRING);
						return temp;
					}else{
						temp.appendToValor((char) a);
					}
					break;
				case EST_EOB_01:
					if(a == -1){
						return new Token(Token.EOB, new String(""));
					}else{
						ungetchar(a);
						estado = falhar(estado);
					}
					break;
				default:
					temp.appendToValor((char) a); 
					temp.setId(Token.OTHER);
					return temp;
			}
		}
	}
	
	private int getchar(){
		return buffer.getchar();
	}
	
	private void ungetchar(int a){
		buffer.ungetchar();
	}
}
