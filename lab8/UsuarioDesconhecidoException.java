package lab8;

public class UsuarioDesconhecidoException extends Exception {
	
	public String getMessage() {
		return "O usuario não está cadastrado, não é possível realizar a ação desejada";
	}
}
