package lab8;

public class UsuarioDesconhecidoException extends Exception {
	
	public String getMessage() {
		return "O usuario n�o est� cadastrado, n�o � poss�vel realizar a a��o desejada";
	}
}
