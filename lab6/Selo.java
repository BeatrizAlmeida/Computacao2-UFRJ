package lab6;

import java.awt.Image;

public class Selo implements Colecionavel {
	
	int posicao; 
	String urlDaImagem, pais;
	float valor;
	
	public Selo(int posicao, String urlDaImagem, float valor, String Pais) {
		this.urlDaImagem = urlDaImagem;
        this.posicao = posicao;
        this.valor = valor;
        this.pais = pais;
	}
	public int getPosicao() {
		return this.posicao;
	}
	public Image getImagem() {
		return null;
	}
	public float getValorNominal() {
		return this.valor;
	}
    public String getPais() {
    	return this.pais;
    }
}
