package lab3;
public class Livro extends Produto {

	private String nome, editora, trechoEmDestaque, autor;
	private int anoPublicacao, numeroPaginas;
	
    public Livro(String nome, String editora) {
        super(nome, editora);
        this.nome = nome;
        this.editora = editora;
    }

    private int getNumeroDePaginas() {
        return numeroPaginas;
    }

    public String getTrechoEmDestaque() {
        return trechoEmDestaque;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoDePublicacao() {
        return anoPublicacao;
    }
}
