package lab8;

public class TamanhoMaximoExcedidoException extends Exception {
	private int tamanhoTexto;
 
	public String getMessage() {
		return "O tamanho m�ximo foi excedido, n�o � poss�vel realizar a a��o desejada";
	}
	
	    
    public TamanhoMaximoExcedidoException(int tamanhoTexto){
         this.tamanhoTexto = tamanhoTexto;
    }
 
    public int getTamanhoTexto() {
        return tamanhoTexto;
    }
}
