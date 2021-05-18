package lab6;

import java.util.Random;

public class Pacotinho {
	
    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";
    private static final int POSICAO_MINIMA = 1; // Para não gerar nenhuma posição menor que 1
	
	Repositorio repo; 
	int[] posicoesDesejadas;
	Figurinha[] figurinhas; 
	
    public Pacotinho(Repositorio repo, int[] posicoesDesejadas) {
        this.repo = repo;
        this.posicoesDesejadas = posicoesDesejadas;
        
        int quantidadeFigs = posicoesDesejadas.length;
        this.figurinhas = new Figurinha[quantidadeFigs];
        for (int i = 0; i <= quantidadeFigs-1; i++) {
            Figurinha fig = new Figurinha(posicoesDesejadas[i], PREFIXO_URL_IMAGENS + "figurinha" + posicoesDesejadas[i] );
            figurinhas[i] = fig;
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
        this.figurinhas = new Figurinha[quantFigurinhas+1];
        Random gerador = new Random();
        for (int i = 1; i <= quantFigurinhas; i++) {
        	int posicao = gerador.nextInt(repo.getTotalFigurinhas()-POSICAO_MINIMA) + POSICAO_MINIMA;
            Figurinha fig = new Figurinha(posicao, PREFIXO_URL_IMAGENS + "figurinha" + posicao );
            figurinhas[i] = fig;
        }
    }

    public Figurinha[] getFigurinhas() {
        return this.figurinhas;
    }
}
