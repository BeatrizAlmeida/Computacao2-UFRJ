package lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class EncontrarParTest {
	
	 
	@Test
	void encontrarParTest() {
		List<Integer> lista = new ArrayList<Integer>();
		lista = Arrays.asList(1, 5, -10, 56, 44, 12, 18 );
		EncontrarPar encontrarPar = new EncontrarPar();
		encontrarPar.encontrarPar(lista,  34);	
	}

}
