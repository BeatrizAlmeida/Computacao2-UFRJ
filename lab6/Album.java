package lab6;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Album <T extends Colecionavel> {

    public static final int PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR = 90;
    public static final Image IMAGEM_PADRAO_PARA_POSICAO_VAZIA = null;
    private static final String PREFIXO_URL_IMAGENS = "http://www.nossoalbum.com.br/imagens/";

    private final Repositorio repositorio;
    private final int quantItensPorPacotinho;

    private List<T> itensColados;  // direct addressing
    private int quantItensColados;

    private Map<Integer, Integer> contRepetidasByPosicao;

    public Album(Repositorio repositorio, int quantItensPorPacotinho) {
        this.repositorio = repositorio;
        this.quantItensPorPacotinho = quantItensPorPacotinho;

        int tamanhoFisicoDaLista = getTamanho() + 1;
        this.itensColados = new ArrayList<>(tamanhoFisicoDaLista);
        // inicializa as posições com nulls, para poder acessá-las diretamente
        for (int i = 0; i < tamanhoFisicoDaLista; i++) {
            this.itensColados.add(null);
        }
        this.quantItensColados = 0;

        this.contRepetidasByPosicao = new HashMap<>();
    }

    public void receberNovoPacotinho(Pacotinho pacotinho) {
        Colecionavel[] itensDoPacotinho = pacotinho.getColecionaveis();
        if (itensDoPacotinho.length != this.quantItensPorPacotinho) {
            return;  
        }

        for (Colecionavel item : pacotinho.getColecionaveis()) {
            final int posicao = item.getPosicao();
            if (possuiItemColado(posicao)) {
                // adiciona como repetida
                int contRepetidas = this.contRepetidasByPosicao.getOrDefault(posicao, 0);
                this.contRepetidasByPosicao.put(posicao, contRepetidas + 1);

            } else {
                // item inédito
                this.itensColados.set(posicao, (T)item);
                this.quantItensColados++;
            }
        }
    }

    public Colecionavel getItemColado(int posicao) {
        return itensColados.get(posicao);
    }

    public boolean possuiItemColado(int posicao) {
    	if(posicao<1 || posicao>=itensColados.size()) {
    		return false;
    	}
        if(this.itensColados.get(posicao)!= null) {
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
        return this.repositorio.getTotalItens();
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
        return this.quantItensColados;
    }

    public int getQuantItensFaltantes() {
        return getTamanho() - getQuantItensColados();
    }

    public void autoCompletar() {
    	 int minimoItensColadasParaAutoCompletar =
    	            (int) (repositorio.getTotalItens() * PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR / 100f);
    	 
    	 if(this.quantItensColados>= minimoItensColadasParaAutoCompletar) {
    		 
    		 for(int i = 1; i<=repositorio.getTotalItens(); i++) {
    			if(this.itensColados.get(i) == null) {
    				Colecionavel item = this.repositorio.getItem(i);
    				this.itensColados.set(i, (T)item);
    				this.quantItensColados++;
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
