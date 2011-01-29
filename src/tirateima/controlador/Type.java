package tirateima.controlador;

/**
 * Modela um tipo primitivo da linguagem.
 * Esse tipo possui um id e um nome (entre uma lista de possíveis tipos).
 */
public class Type {
	
	private TypeId id;
	private String name;
	
	public Type(TypeId id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Type(TypeId id) {
		this(id, null);
	}
	
	public TypeId getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		switch (id) {
			case VOID:
				return "void";
			case INTEGER:
				return "integer";
			case REAL:
				return "real";
			case STRING:
				return "string";
			case CHAR:
				return "char";
			case BOOLEAN:
				return "boolean";
			default:
				return name;
		}
	}
}
