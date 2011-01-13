package tirateima.gui.editortexto;

@SuppressWarnings("serial")
class FimDeBufferAtingidoException extends Exception{
}

public class Buffer {
	char buffer[];
	int i;
	boolean eob = false;
	
	public Buffer(String b){
		if(b.length() > 0){
			this.buffer = b.toCharArray();
			i = 0;
		}else{
			buffer = null;
			eob = true;
		}
	}
	
	public int getchar(){
		if(!eob){
			if(i < buffer.length){
				char temp = buffer[i];
				i++;
				return temp;
			}else{
				eob = true;
				return -1;
			}
		}else{
			return -1;
		}
	}
	
	public void ungetchar(){
		if((i >= 0) && (!eob)){
			i--;
		}
	}
	
	public int length(){
		return buffer != null ? buffer.length : 0;
	}
}
