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
 *  É preciso cadastrar um usuário, identificado pelo seu e-mail, para que tuítes possam ser feitos.
 *  Usuários começam como iniciantes, depois são promovidos a senior e a ninja, em função do número de tuítes.
 *  Existe um tamanho máximo permitido por mensagem (constante TAMANHO_MAXIMO_TUITES).
 *  As mensagens podem conter hashtags (palavras iniciadas por #), que são detectadas automaticamente.
 *  Os tuítes podem conter, além da mensagem de texto, um anexo qualquer.
 *  Há um método para retornar, a qualquer momento, qual a hashtag mais usada em toda a história do sistema.
 */
public class TuiterLite {

    public static int TAMANHO_MAXIMO_TUITES = 120;
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Map<String, Integer> hashtagsTuite = new HashMap<>();

    
    /**
     * Cadastra um usuário, retornando o novo objeto Usuario criado.
     * @param nome O nome do usuário.
     * @param email O e-mail do usuário (precisa ser único no sistema).
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
     * @param usuario O autor do tuíte
     * @param texto O texto desejado
     * @return Um "tuíte", que será devidamente publicado no sistema
     *
     * PS.: Se o texto exceder o limite pré-definido, ou o usuário não estiver cadastrado, return null
     */
    public Tuite tuitarAlgo(Usuario usuario, String texto) {
        if(texto.length() > TAMANHO_MAXIMO_TUITES || !usuarios.containsKey(usuario.getEmail())) {
        	return null;
        }
        String[] palavrasDaFrase = texto.split("([^(a-z|A-Z|0-9|ã-ü|#)])+");  
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
     * Retorna a hashtag mais comum dentre todas as que já apareceram.
     * A cada tuíte criado, hashtags devem ser detectadas automaticamente para que este método possa funcionar.
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