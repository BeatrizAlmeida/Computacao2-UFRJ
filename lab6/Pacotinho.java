package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pacotinho {
	
    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";
    private static final int POSICAO_MINIMA = 1; // Para não gerar nenhuma posição menor que 1
	
    private List<Figurinha> figurinhas;
	
    public Pacotinho(Repositorio repo, int[] posicoesDesejadas) {
    	figurinhas = new ArrayList<Figurinha>();
    	int quantidadeFigs = posicoesDesejadas.length;
        for (int i = 0; i <= quantidadeFigs-1; i++) {
            figurinhas.add(repo.getFigurinha(posicoesDesejadas[i]));
        }
    }

    /**
     * Produz um pacotinho com quantFigurinhas sorteadas aleatoriamente
     * dentre aqueles presentes no repositório informado.
     *
     * @param repo o repositório desejado
     * @param quantFigurinhas a quantidade de figurinhas a constar no pacotinho
     */
    public Pacotinho(Repositorio repo, int quantFigurinhas) {
    	figurinhas = new ArrayList<Figurinha>();
        Random gerador = new Random();
        for (int i = 1; i <= quantFigurinhas; i++) {
        	int posicao = gerador.nextInt(repo.getTotalFigurinhas()-POSICAO_MINIMA) + POSICAO_MINIMA;
        	 figurinhas.add(repo.getFigurinha(posicao));
        }
    }

    public Figurinha[] getFigurinhas() {
    	Figurinha[] figurinhasArray = new Figurinha[figurinhas.size()];
        figurinhas.toArray(figurinhasArray);
        return figurinhas.toArray(figurinhasArray);
    }
}