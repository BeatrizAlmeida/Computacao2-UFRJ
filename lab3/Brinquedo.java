package lab3;

public class Brinquedo extends Produto {
	
	private String marca;
	private int idadeMinimaRecomendada;

    public Brinquedo(String descricao ) {
        super(descricao,"url");
    }

    public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setIdadeMinimaRecomendada(int idadeMinimaRecomendada) {
		this.idadeMinimaRecomendada = idadeMinimaRecomendada;
	}

	public String getMarca() {
        return marca;
    }

    public int getIdadeMinimaRecomendada() {
        return idadeMinimaRecomendada;  
    }
}
