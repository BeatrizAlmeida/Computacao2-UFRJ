package lab5;

import org.junit.jupiter.api.Test;

class CaracterMaisFrequenteTest {

	@Test
	void encontrarCaracterMaisFrequenteTest() {
		String palavra = "arara";
		CaracterMaisFrequente encontrar = new CaracterMaisFrequente();
		encontrar.encontrarCaracterMaisFrequente(palavra);
	}

}
