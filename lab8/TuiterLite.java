package lab8;

import java.util.*;

/**
 *  Esta classe implementa um sistema de mensagens curtas estilo Twitter.
 *  � preciso cadastrar um usu�rio, identificado pelo seu e-mail, para que tu�tes possam ser feitos.
 *  Usu�rios come�am como iniciantes, depois s�o promovidos a senior e a ninja, em fun��o do n�mero de tu�tes.
 *  Existe um tamanho m�ximo permitido por mensagem (constante TAMANHO_MAXIMO_TUITES).
 *  As mensagens podem conter hashtags (palavras iniciadas por #), que s�o detectadas automaticamente.
 *  Os tu�tes podem conter, al�m da mensagem de texto, um anexo qualquer.
 *  H� um m�todo para retornar, a qualquer momento, qual a hashtag mais usada em toda a hist�ria do sistema.
 */
public class TuiterLite <T> {

    public static int TAMANHO_MAXIMO_TUITES = 120;

    private Map<String, Usuario> usuarioByEmail;

    private Map<String, Integer> contadorByHashtag;
    private String hashtagMaisComum;
    private int contadorDaHashtagMaisComum;

    public TuiterLite() {
        this.usuarioByEmail = new HashMap<>();
        this.contadorByHashtag = new HashMap<>();
        this.hashtagMaisComum = null;
        this.contadorDaHashtagMaisComum = 0;
    }

    /**
     * Cadastra um usu�rio, retornando o novo objeto Usuario criado.
     * @param nome O nome do usu�rio.
     * @param email O e-mail do usu�rio (precisa ser �nico no sistema).
     * @return O Usuario criado.
     * @throws UsuarioJaExisteException 
     */
    public Usuario cadastrarUsuario(String nome, String email) throws UsuarioJaExisteException {  // throws UsuarioJaExisteException {
        Usuario usuarioPreExistente = this.usuarioByEmail.get(email);
        if (usuarioPreExistente != null) {
            throw new UsuarioJaExisteException();
        }

        Usuario novoUsuario = new Usuario(nome, email);
        this.usuarioByEmail.put(email, novoUsuario);

        return novoUsuario;
    }

    /**
     *
     * @param usuario O autor do tu�te (n�o deve ser nulo)
     * @param texto O texto desejado (nunca nulo)
     * @return Um "tu�te", que ser� devidamente publicado no sistema
     *
     * PS.: Se o texto exceder o limite pr�-definido, ou o usu�rio n�o estiver cadastrado, return null
     * 
     */
    public Tuite tuitarAlgo(Usuario usuario, String texto) throws UsuarioDesconhecidoException, TamanhoMaximoExcedidoException, TamanhoVazioException {
        
        if(texto == null || usuario == null) {
        	throw new IllegalArgumentException("Usu�rio ou texto n�o podem ser nulos!");
        }
        
        if(texto.length() > TAMANHO_MAXIMO_TUITES) {
        	throw new TamanhoMaximoExcedidoException(texto.length());
        }
        
        if(!this.usuarioByEmail.containsKey(usuario.getEmail())) {
        	throw new UsuarioDesconhecidoException();
        }
        
        if(texto.length() == 0) {
        	throw new TamanhoVazioException();
        }

        Set<String> hashtags = obterHashtags(texto);
        Tuite tuite = new Tuite(usuario, texto, hashtags);

        // atualiza os contadores de hashtags do sistema
        for (String hashtag : hashtags) {
            int contadorAtual = this.contadorByHashtag.getOrDefault(hashtag, 0);
            int novoContador = contadorAtual + 1;
            this.contadorByHashtag.put(hashtag, novoContador);

            if (novoContador > this.contadorDaHashtagMaisComum) {
                this.hashtagMaisComum = hashtag;
                this.contadorDaHashtagMaisComum = novoContador;
            }
        }

        return tuite;
    }

    /**
     * Retorna a hashtag mais comum dentre todas as que j� apareceram.
     * A cada tu�te criado, hashtags devem ser detectadas automaticamente para que este m�todo possa funcionar.
     * @return A hashtag mais comum, ou null se nunca uma hashtag houver sido tuitada.
     */
    public String getHashtagMaisComum() {
        return this.hashtagMaisComum;
    }

    private Set<String> obterHashtags(String texto) {

        Set<String> hashtags = null;

        String[] palavrasDaFrase = texto.split("([^(a-z|A-Z|0-9|�-�|#)])+");   // "escapando" a contrabarra para que minha string seja "CONTRABARRA S"

        for (String palavra : palavrasDaFrase) {

            if (palavra.charAt(0) == '#') {
                palavra = palavra.replaceAll("#+", "#");

                String[] tags = palavra.split("#");
                for (String tag : tags) {
                    if (tag.length() > 0) {

                        if (hashtags == null) {  // lazy instantiation
                            hashtags = new HashSet<>();
                        }
                        hashtags.add("#" + tag);
                    }
                }
            }
        }

        return hashtags == null ? Collections.emptySet() : hashtags;
    }

    private static boolean isAlphanumeric(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c);
    }



}