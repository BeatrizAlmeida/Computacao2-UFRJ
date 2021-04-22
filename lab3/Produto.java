package lab3;
public class Produto {
	
	private String descricao, urlDaImagem;
	private int pesoEmGramas, qtddEmEstoque;
	private float precoEmReais;
	private Dimensoes dimensoes;

    public Produto(String descricao, String urlDaImagem) {
        this.descricao = descricao;
        this.urlDaImagem = urlDaImagem;
    }

    /**
     * @return uma descrição textual do produto
     */
    public String getDescricao() {
        return descricao;  
    }

    public int getPesoEmGramas() {
        return pesoEmGramas;  
    }

    public Dimensoes getDimensoes() {
        return dimensoes; 
    }

    public float precoEmReais() {
        return precoEmReais; 
    }

    public void setPrecoEmReais(float preco) {
        this.precoEmReais = preco;
    }

    public String getUrlDaImagem() {
        return urlDaImagem;  
    }
    
    public void setQtddEmEstoque(int qtdd) {
    	qtddEmEstoque+=qtdd;
    }
    public int getQtddEmEstoque( ) {
    	return qtddEmEstoque;
    }
}
