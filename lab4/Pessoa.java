package lab4;

public class Pessoa {
	
    public final static int TAMANHO_MAXIMO_DO_NOME = 30;

	private String nome;
	private int anoNascimento;
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		 if (nome.length() > TAMANHO_MAXIMO_DO_NOME) {
	            return;
	      }
	     this.nome = nome;
	}
	public int getAnoNascimento() {
		return anoNascimento;
	}
	public void setAnoNascimento(int anoNascimento) {
		this.anoNascimento = anoNascimento;
	}
}
