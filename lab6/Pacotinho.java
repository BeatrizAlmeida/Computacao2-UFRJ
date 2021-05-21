package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pacotinho <T extends Colecionavel> {
	
    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";
    private static final int POSICAO_MINIMA = 1; // Para não gerar nenhuma posição menor que 1
	
    private List<T> itensColecionaveis;
	
    public Pacotinho(Repositorio repo, int[] posicoesDesejadas) {
    	itensColecionaveis = new ArrayList<T>();
    	int quantidadeFigs = posicoesDesejadas.length;
        for (int i = 0; i <= quantidadeFigs-1; i++) {
        	itensColecionaveis.add((T)repo.getItem(posicoesDesejadas[i]));
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
    	itensColecionaveis = new ArrayList<T>();
        Random gerador = new Random();
        for (int i = 1; i <= quantFigurinhas; i++) {
        	int posicao = gerador.nextInt(repo.getTotalItens()-POSICAO_MINIMA) + POSICAO_MINIMA;
        	itensColecionaveis.add((T)repo.getItem(posicao));
        }
    }

    public T[] getColecionaveis() {
    	T[] colecionaveisArray = (T[]) new Colecionavel[itensColecionaveis.size()];
    	itensColecionaveis.toArray(colecionaveisArray);
        return itensColecionaveis.toArray(colecionaveisArray);
    }
}