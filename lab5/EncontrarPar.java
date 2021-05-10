package lab5;

import java.util.HashSet;
import java.util.List;

public class EncontrarPar {
	
	public void encontrarPar(List<Integer> lista, int k) {
        
		HashSet<Integer> numerosDaLista = new HashSet<>();
		int numeroDoPar1 = 0, numeroDoPar2 = 0;
		boolean temPar = false;
		
		for (Integer numero: lista) {
			numerosDaLista.add(numero);
			if (numerosDaLista.contains(k-numero)) {
				numeroDoPar1 = numero;
				numeroDoPar2 = k-numero;
				temPar = true;
			}
		}
		
		if(temPar) {
			System.out.printf("%d, %d", numeroDoPar1, numeroDoPar2);
		}else {
			System.out.printf("Não há números que somem %d na sua lista", k);
		}
	}
}
