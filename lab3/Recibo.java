package lab3;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Recibo {

	private float valorTotalDaCompra;
	private int qtddComprada;
	private Produto produto;
	private Usuario usuario;
	
	private static DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
    private static DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
    private static char sep = symbols.getDecimalSeparator();
	    
	public Recibo(Produto produto, int qtddComprada, Usuario comprador) {
		this.usuario = comprador;
		this.valorTotalDaCompra = produto.precoEmReais()*qtddComprada;
		this.qtddComprada = qtddComprada;
		this.produto = produto;
		produto.setQtddEmEstoque(-qtddComprada);
	}
	
    public float getValorTotalDaCompra() {
        return valorTotalDaCompra;  
    }

    public Usuario getUsuario() {
        return usuario;  
    }
    
    @Override
    public String toString() {
    	int valorInteiro = (int)this.valorTotalDaCompra;
    	int valorDecimal = (int)(this.valorTotalDaCompra - valorInteiro);
    	return "Recibo no valor de R$" +valorInteiro + sep + valorDecimal + "0 para "+ usuario.getNome() +
    			" referente a compra de " + this.qtddComprada + " unidades de Livro: " +
    			produto.getDescricao(); 
    }
    
}
