package lab5;

import java.util.HashMap;

public class CaracterMaisFrequente {

	public void encontrarCaracterMaisFrequente(String string) {
		
	    HashMap<Character, Integer> frequencia = new HashMap<>();
	    int maximo = 0;
	    char maisFrequente = '0';
	    for (int i = 0; i < string.length(); i++) {
            char caracter = string.charAt(i);
            if(frequencia.containsKey(caracter)) {
            	frequencia.put(caracter, frequencia.get(caracter)+1);
            }else {
            	frequencia.put(caracter, 1);
            }
            if(frequencia.get(caracter) > maximo) {
            	maximo = frequencia.get(caracter);
            	maisFrequente = caracter;
            }
        }
	    
	    System.out.printf("%c, %d", maisFrequente, maximo);
	}
}
