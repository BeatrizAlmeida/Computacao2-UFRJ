package lab1;

import java.util.Scanner;

public class Primos {

	/**
     * Retorna um array contendo exatamente os números primos em [1, n].
     * @param n O maior número a ser considerado
     * @return um array de inteiros com os primos no intervalo dado.
     */
	
	public static int[] obterPrimosComCrivo(int n){
		int[ ] primos = new int[n];
		int j=0;
		int composto;
		boolean[ ] ePrimo = new boolean[n+1];
		for (int i=2; i<=n; i++) {
			ePrimo[i] = true;
		}
		for (int i=2; i<=n; i++) {
			if(ePrimo[i] == true) {
				primos[j] = i;
				j++;
				composto = i+i;
				while(composto<=n) {
					ePrimo[composto] = false; 
					composto += i;
				}
			}
		}
		return primos;
	}

	public static int[] obterPrimos (int n){
		int[ ] primos;
		primos = new int[n];  
		int contDiv, j=0;
		int possivelPrimo=2; 
		while(possivelPrimo<=n) {
			contDiv = 0;
			for( int i=1; i<=Math.sqrt(possivelPrimo); i++ ) {
				if(possivelPrimo%i == 0) {
					contDiv++;
				}
			}
			if(contDiv == 1 && possivelPrimo!=1) {
				primos[j]=possivelPrimo;
				j++;
			}
			possivelPrimo++;
		}
		
		return primos;
	}
	public static void main(String[] args) {

        for ( int n = 10; n <= 10_000; n *= 10) {
            double inicio = System.currentTimeMillis();
            int[] primos = obterPrimos(n) ;
            double fim = System.currentTimeMillis();
            double tempo = fim - inicio; 
            System.out.printf("o tempo utilizado foi de %.5f ms com n igual a %d\n",tempo, n);
            inicio = System.currentTimeMillis();
            primos = obterPrimosComCrivo(n);
            fim = System.currentTimeMillis();
            tempo = fim - inicio;
            System.out.printf("o tempo utilizando crivo foi de %.5f ms com n igual a %d\n",tempo, n);
        }
	}

}
