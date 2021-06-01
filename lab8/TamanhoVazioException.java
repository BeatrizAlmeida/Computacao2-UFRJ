package lab8;

public class TamanhoVazioException extends Exception{

	public String getMessage() {
		return "A variável nao pode estar vazia, não é possível realizar a ação desejada";
	}
}
