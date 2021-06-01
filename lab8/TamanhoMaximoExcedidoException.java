package lab8;

public class TamanhoMaximoExcedidoException extends Exception {
	private int tamanhoTexto;
 
	public String getMessage() {
		return "O tamanho máximo foi excedido, não é possível realizar a ação desejada";
	}
	
	    
    public TamanhoMaximoExcedidoException(int tamanhoTexto){
         this.tamanhoTexto = tamanhoTexto;
    }
 
    public int getTamanhoTexto() {
        return tamanhoTexto;
    }
}
