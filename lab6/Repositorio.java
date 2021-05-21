package lab6;

import java.util.ArrayList;
import java.util.List;

public class Repositorio <T extends Colecionavel> {

    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";
    private static final float VALOR_DEFAULT = 100;
    private static final String PAIS_DEFAULT = "Brasil";

    private List<T> todosOsItens;

    public Repositorio(String sufixoUrlImagens, int quantItens, String tipo) {
    	todosOsItens = new ArrayList<>(quantItens);
        
    	for (int i = 1; i <= quantItens; i++) {
        	switch (tipo.toLowerCase()) {
                case "figurinha":
                    Figurinha fig = new Figurinha(i, PREFIXO_URL_IMAGENS + sufixoUrlImagens);
                    todosOsItens.add((T)fig);
                    break;
                case "selo":
                    Selo selo = new Selo(i, PREFIXO_URL_IMAGENS + sufixoUrlImagens, VALOR_DEFAULT, PAIS_DEFAULT);
                    todosOsItens.add((T)selo);
                    break;
                default:
                	
        	}            
        }
    }

    public int getTotalItens() {
        return this.todosOsItens.size();
    }
    
    public Colecionavel getItem( int posicao) {
    	if(posicao<1 || posicao>=getTotalItens()) {
    		return null;
    	}
    	return this.todosOsItens.get(posicao-1);
    }
}
