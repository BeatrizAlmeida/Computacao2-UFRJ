package lab5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculadorIntersecaoViaBuscaBinaria implements CalculadorIntersecao {


    @Override
    public List<Usuario> obterIntersecao(List<Usuario> lista1, List<Usuario> lista2) {

        List<Usuario> intersecao = new ArrayList<>();

//        // ordena a primeira lista
//        Comparator<Usuario> c = new ComparadorUsuarios();
//        lista1.sort(c);

        // outra maneira (n�o precisa criar um Comparator)
        Collections.sort(lista1);

        // para cada elemento da segunda lista, busca na primeira via Busca Bin�ria
        for (Usuario usuarioDaSegundaLista : lista2) {
            if (Collections.binarySearch(lista1, usuarioDaSegundaLista) >= 0) {
                // a busca bin�ria encontrou o elemento
                intersecao.add(usuarioDaSegundaLista);
            }
        }

        return intersecao;
    }
}
