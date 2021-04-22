package lab3;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa uma loja virtual para produtos de qualquer tipo,
 * desde que tenham descri��o, pre�o e dimens�es.
 *
 * Essa classe ser� um singleton, isto �, permitiremos apenas
 * uma inst�ncia deste objeto no sistema.
 */
public class Loja {

	private List<Usuario> usuarios;
	private List<Produto> produtos;
	private static final Loja instanciaUnica = new Loja();

    static {
        System.out.println("Estou subindo a classe Loja...");
    }

    protected Loja() {
    	this.produtos = new ArrayList<>();
    	this.usuarios = new ArrayList<>();
    }

    public static Loja getInstanciaUnica() {
        return instanciaUnica;
    }

    public void limparEstado() {
    	this.produtos = new ArrayList<>();
    	this.usuarios = new ArrayList<>();
    }

    /**
     * Inclui no estoque da loja a quantidade informado do produto.
     *
     * @param produto o produto a ser inclu�do
     * @param quantidadeAIncluir a quantidade que ser� acrescentada a quantidade existente.
     */
    public void incluirProduto(Produto produto, int quantidadeAIncluir) {
    	produtos.add(produto);
    	produto.setQtddEmEstoque(quantidadeAIncluir);
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Efetua a venda do produto desejado na quantidade especificada.
     *
     * @param produto o produto
     * @param quantidadeDesejada a quantidade
     *
     * @return um Recibo indicando a venda feita, se o produto existia (em quantidade suficiente)
     *         no estoque da loja; null, caso o usu�rio ou o produto sejam desconhecidos,
     *         ou n�o haja quantidade suficiente do produto desejado
     */
    public Recibo efetuarVenda(
            Produto produto, int quantidadeDesejada, Usuario usuario) {
    	if(!(produtos.contains(produto)) || !(usuarios.contains(usuario))) {
    		return null;
    	}
    	if(produto.getQtddEmEstoque() < quantidadeDesejada) {
    		return null;
    	}
        return new Recibo(produto, quantidadeDesejada, usuario);
    }

    /**
     * @param produto o produto a ser consultado
     *
     * @return a quantidade em estoque;
     *         0 se n�o houver nenhuma unidade;
     *         -1 se o produto n�o � sequer vendido pela loja
     */
    public int informarQuantidadeEmEstoque(Produto produto) {
        return produto.getQtddEmEstoque();  
    }
}
