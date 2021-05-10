package lab5;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import java.lang.*;


class UsuarioTest {
	
    private static final int NUMERO_AMIGOS = 40000;


	private Usuario usuario = new Usuario(1, new CalculadorIntersecaoIngenuo());;
	private Usuario usuario2 = new Usuario(2, new CalculadorIntersecaoEsperto());
	private Usuario usuario3= new Usuario(3, new CalculadorIntersecaoViaBuscaBinaria());
	
	@Test
	public void testarTamanhoIntersecaoAmigos() {
		usuario2.setAmigo(usuario3);
		usuario.setAmigo(usuario3);
		assertEquals(usuario.obterQuantidadeDeAmigosEmComum(usuario2), 1);
		assertEquals(usuario2.obterQuantidadeDeAmigosEmComum(usuario), 1);
	}
	
	@Test
	public void testarPerformanceCalculadora () {
		Usuario amigo;
		for (int i=4; i<NUMERO_AMIGOS; i++) {
			amigo = new Usuario(i, new CalculadorIntersecaoIngenuo());
			usuario.setAmigo(amigo);
			usuario2.setAmigo(amigo);
			usuario3.setAmigo(amigo);
		}
        long inicio = System.currentTimeMillis();
        usuario.obterQuantidadeDeAmigosEmComum(usuario2);
        long duracao = System.currentTimeMillis() - inicio;

        System.out.printf("duracao calculadora ingênua = %.3f\n", duracao / 1000f );
        
        inicio = System.currentTimeMillis();
        usuario2.obterQuantidadeDeAmigosEmComum(usuario);
        duracao = System.currentTimeMillis() - inicio;

        System.out.printf("duracao calculadora esperta = %.3f\n", duracao / 1000f);
        
        inicio = System.currentTimeMillis();
        usuario3.obterQuantidadeDeAmigosEmComum(usuario);
        duracao = System.currentTimeMillis() - inicio;

        System.out.printf("duracao calculadora via busca binária = %.3f\n",duracao / 1000f );
	}

}
