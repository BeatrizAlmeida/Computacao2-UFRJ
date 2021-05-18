package lab6;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Album {

    public static final int PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR = 90;
    public static final Image IMAGEM_PADRAO_PARA_POSICAO_VAZIA = null;
    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";

    private final Repositorio repositorio;
    private final int quantItensPorPacotinho;

    private List<Figurinha> figurinhasColadas;  // direct addressing
    private int quantFigurinhasColadas;

    private Map<Integer, Integer> contRepetidasByPosicao;

    public Album(Repositorio repositorio, int quantItensPorPacotinho) {
        this.repositorio = repositorio;
        this.quantItensPorPacotinho = quantItensPorPacotinho;

        int tamanhoFisicoDaLista = getTamanho() + 1;
        this.figurinhasColadas = new ArrayList<>(tamanhoFisicoDaLista);
        // inicializa as posições com nulls, para poder acessá-las diretamente
        for (int i = 0; i < tamanhoFisicoDaLista; i++) {
            this.figurinhasColadas.add(null);
        }
        this.quantFigurinhasColadas = 0;

        this.contRepetidasByPosicao = new HashMap<>();
    }

    public void receberNovoPacotinho(Pacotinho pacotinho) {
        Figurinha[] figurinhasDoPacotinho = pacotinho.getFigurinhas();
        if (figurinhasDoPacotinho.length != this.quantItensPorPacotinho) {
            return;  
        }

        for (Figurinha fig : pacotinho.getFigurinhas()) {
            final int posicao = fig.getPosicao();
            if (possuiItemColado(posicao)) {
                // adiciona como repetida
                int contRepetidas = this.contRepetidasByPosicao.getOrDefault(posicao, 0);
                this.contRepetidasByPosicao.put(posicao, contRepetidas + 1);

            } else {
                // item inédito
                this.figurinhasColadas.set(posicao, fig);
                this.quantFigurinhasColadas++;
            }
        }
    }

    public Figurinha getItemColado(int posicao) {
        return figurinhasColadas.get(posicao);
    }

    public boolean possuiItemColado(int posicao) {
    	if(posicao<1 || posicao>=figurinhasColadas.size()) {
    		return false;
    	}
        if(this.figurinhasColadas.get(posicao)!= null) {
        	return true;
        }
        return false;
    }

    public boolean possuiItemRepetido(int posicao) {
        if(contRepetidasByPosicao.containsKey(posicao)) {
        	return true;
        }
        return false;
    }

    public int getTamanho() {
        return this.repositorio.getTotalFigurinhas();
    }

    public int getQuantItensColados() {
//        int contador = 0;
//        for (Figurinha fig : this.figurinhasColadas) {
//            if (fig != null) {
//                contador++;
//            }
//        }
//        return contador;

        // melhor jeito: atributo!
        return this.quantFigurinhasColadas;
    }

    public int getQuantItensFaltantes() {
        return getTamanho() - getQuantItensColados();
    }

    public void autoCompletar() {
    	 int minimoFigurinhasColadasParaAutoCompletar =
    	            (int) (repositorio.getTotalFigurinhas() * PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR / 100f);
    	 
    	 if(this.quantFigurinhasColadas>= minimoFigurinhasColadasParaAutoCompletar) {
    		 
    		 for(int i = 1; i<=repositorio.getTotalFigurinhas(); i++) {
    			if(this.figurinhasColadas.get(i) == null) {
    				Figurinha fig = new Figurinha(i, PREFIXO_URL_IMAGENS + "figurinha" + i);
    				this.figurinhasColadas.add(i, fig);
    			}
    		 }
    		 
    	 }else {
    		 System.out.println("Não é possível auto-completar um álbum que não tenha "
    		 		+ "a quantidade mínima de figurinhas coladas");
    	 }
    	 
    }

    private Image getImagem(int posicao) {
        return possuiItemColado(posicao)
                ? this.getItemColado(posicao).getImagem()
                : IMAGEM_PADRAO_PARA_POSICAO_VAZIA;
    }

}
