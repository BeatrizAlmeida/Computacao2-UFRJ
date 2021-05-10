package lab5;

import java.util.ArrayList;
import java.util.List;

public class CalculadorIntersecaoIngenuo implements CalculadorIntersecao {

    @Override
    public List<Usuario> obterIntersecao(List<Usuario> lista1, List<Usuario> lista2) {

        List<Usuario> intersecao = new ArrayList<>();

    	for (int i=0; i<lista1.size(); i++) {
    		for (int j=0; j<lista1.size(); j++) {
        		if (lista1.get(i) == lista2.get(j)) {
        			intersecao.add(lista1.get(i));
        		}
        	}
    	}
        return intersecao;
    }
}
