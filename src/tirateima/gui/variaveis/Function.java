package tirateima.gui.variaveis;

import java.awt.Color;
import java.util.List;
import java.util.ListIterator;

import tirateima.controlador.Type;
import tirateima.controlador.TypeId;
import tirateima.gui.arquivos.FileBorder;

/**
 * Modela uma função do Tira-Teima.
 * 
 * Uma função, do ponto de vista da interface, nada mais é que um
 * Mostrador com borda.
 * 
 * @author Luciano Santos
 */
@SuppressWarnings("serial")
public class Function extends Mostrador {
	private static Color COR_BORDA = Color.lightGray;
	
	public Function(
			String nome,
			Type tipoRetorno,
			List<Variavel> params,
			List<Variavel> vars) {
		
		super.setBorder(new FileBorder(
				gerarTitulo(nome, tipoRetorno, params),
				COR_BORDA));
		
		for (Variavel v : params) {
			this.adicionarVariavel(v);
		}
		
		for (Variavel v : vars) {
			this.adicionarVariavel(v);
		}
	}
	
	private static String gerarTitulo(
			String nome,
			Type type,
			List<Variavel> params) {
		
		StringBuffer titulo = new StringBuffer();
		
		boolean is_void = (type.getId().equals(TypeId.VOID));
		
		titulo.append(is_void ? "procedure " : "function ");
		titulo.append(nome);
		titulo.append("(");
		
		ListIterator<Variavel> i = params.listIterator();
		Variavel v;
		while (i.hasNext()) {
			v = i.next();
			titulo.append(v.signature());
			
			if (i.hasNext()) {
				titulo.append(", ");
			}
		}
		
		titulo.append(")");
		
		if (!is_void) {
			titulo.append(" : ");
			titulo.append(type.toString());
		}
		
		return titulo.toString();
	}
}
