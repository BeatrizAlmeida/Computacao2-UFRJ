package lab7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Esta classe implementa um sistema de mensagens curtas estilo Twitter.
 *  � preciso cadastrar um usu�rio, identificado pelo seu e-mail, para que tu�tes possam ser feitos.
 *  Usu�rios come�am como iniciantes, depois s�o promovidos a senior e a ninja, em fun��o do n�mero de tu�tes.
 *  Existe um tamanho m�ximo permitido por mensagem (constante TAMANHO_MAXIMO_TUITES).
 *  As mensagens podem conter hashtags (palavras iniciadas por #), que s�o detectadas automaticamente.
 *  Os tu�tes podem conter, al�m da mensagem de texto, um anexo qualquer.
 *  H� um m�todo para retornar, a qualquer momento, qual a hashtag mais usada em toda a hist�ria do sistema.
 */
public class TuiterLite {

    public static int TAMANHO_MAXIMO_TUITES = 120;
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Map<String, Integer> hashtagsTuite = new HashMap<>();

    
    /**
     * Cadastra um usu�rio, retornando o novo objeto Usuario criado.
     * @param nome O nome do usu�rio.
     * @param email O e-mail do usu�rio (precisa ser �nico no sistema).
     * @return O Usuario criado.
     */
    public Usuario cadastrarUsuario(String nome, String email) {  // throws UsuarioJaExisteException {
        if(!usuarios.containsKey(email)) {
        	Usuario novoUsuario = new Usuario(nome, email);
        	usuarios.put(email, novoUsuario);
        	return novoUsuario;
        }
        return null;
    }

    /**
     *
     * @param usuario O autor do tu�te
     * @param texto O texto desejado
     * @return Um "tu�te", que ser� devidamente publicado no sistema
     *
     * PS.: Se o texto exceder o limite pr�-definido, ou o usu�rio n�o estiver cadastrado, return null
     */
    public Tuite tuitarAlgo(Usuario usuario, String texto) {
        if(texto.length() > TAMANHO_MAXIMO_TUITES || !usuarios.containsKey(usuario.getEmail())) {
        	return null;
        }
        String[] palavrasDaFrase = texto.split("([^(a-z|A-Z|0-9|�-�|#)])+");  
        for (String palavra : palavrasDaFrase) {

            if (palavra.charAt(0) == '#') {
                palavra = palavra.replaceAll("#+", "#");

                String[] tags = palavra.split("#");
                for (String tag : tags) {
                    if (tag.length() > 0) {
                    	if(hashtagsTuite.containsKey("#" + tag)) {
                    		hashtagsTuite.put("#" + tag, hashtagsTuite.get("#" + tag)+1);
                    	}else {
                    		hashtagsTuite.put("#" + tag, 1);
                    	}
                    }
                }
            }
        }

        Tuite tuite = new Tuite(usuario, texto, hashtagsTuite.keySet());
        return tuite;
    }

    /**
     * Retorna a hashtag mais comum dentre todas as que j� apareceram.
     * A cada tu�te criado, hashtags devem ser detectadas automaticamente para que este m�todo possa funcionar.
     * @return A hashtag mais comum, ou null se nunca uma hashtag houver sido tuitada.
     */
    public String getHashtagMaisComum() {
        if(this.hashtagsTuite.isEmpty()) {
        	return null;
        }
        int quantidadeDaMaisRepetida=(Collections.max(hashtagsTuite.values()));  
        for (String hashtag : hashtagsTuite.keySet()) {  
            if (hashtagsTuite.get(hashtag) == quantidadeDaMaisRepetida) {
                return hashtag;   
            }
        }
        return null;
    }
}