package lab8;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class TuiterLiteTest {

    private TuiterLite tuiterLite;
    private Usuario usuario;

    @Before
    public void setUp() throws UsuarioJaExisteException {
        // cria um TuiterLite
        tuiterLite = new TuiterLite();

        // cria um usu�rio
        usuario = tuiterLite.cadastrarUsuario("Fulano", "fulano@teste.com");

        // cria uma imagem para o usu�rio
        Image foto = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        usuario.setFoto(foto);
    }

    @Test
    public void testeUsuariosReconhecidosCorretamente() {
        assertEquals("Duas inst�ncias de Usuario devem ser consideradas iguais se possuirem o mesmo email",
                this.usuario, new Usuario("Fulano de Tal", "fulano@teste.com"));
    }

    @Test
    public void testeAutorDoTuite()
            throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException, TamanhoVazioException {
        Tuite tuite = tuiterLite.tuitarAlgo(usuario, "Testando");
        assertEquals("O tu�te deve retornar corretamente seu autor",
                usuario, tuite.getAutor());
    }

//    @Test
//    public void testeTuiteDeUsuarioDesconhecido() throws TamanhoMaximoExcedidoException {
//        try {
//            tuiterLite.tuitarAlgo(new Usuario("Usu�rio Desconhedido", "unknown@void.com"), "Testando");
//            fail("Uma UsuarioDesconhecidoException deve ser lan�ada caso o autor n�o seja usu�rio cadastrado");
//        } catch (UsuarioDesconhecidoException e) {
//            // ok
//        }
//    }

    @Test
    public void testeTamanhoTuite() throws UsuarioDesconhecidoException, TamanhoMaximoExcedidoException, TamanhoVazioException {
        // testaremos para 100 tamanhos diferentes, todos maiores do que o m�ximo permitido
        for (int excessoCaracteres = 1; excessoCaracteres <= 100; excessoCaracteres++) {

            // cria uma String muito grande
            int tamanho = TuiterLite.TAMANHO_MAXIMO_TUITES + 1;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= tamanho; i++) {
                sb.append("x");
            }
            String texto = sb.toString();

             try {
                 tuiterLite.tuitarAlgo(usuario, texto);
                fail("Um tuite maior do que o tamanho m�ximo deve lan�ar uma TamanhoMaximoExcedidoException");
 
             } catch (TamanhoMaximoExcedidoException e) {
                assertEquals("A exce��o deve comunicar corretamente o tamanho do texto que se tentou tuitar",
                         tamanho, e.getTamanhoTexto());
             } 
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testeAnexo()
            throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException, TamanhoVazioException {

        Tuite tuite = tuiterLite.tuitarAlgo(usuario, "Testando");

        // vamos anexar a foto do usu�rio no tu�te que ele acabou de fazer
        tuite.anexarAlgo(usuario.getFoto());
        assertEquals("O tu�te deve retornar corretamente o objeto que foi anexado a ele",
                usuario.getFoto(), tuite.getAnexo());

        // agora vamos anexar um outro objeto qualquer ao mesmo tu�te
        Object objeto = new Object();
        tuite.anexarAlgo(objeto);
        assertEquals("O tu�te deve sobrescrever o anexo anterior (se existir) com o novo anexo",
                objeto, tuite.getAnexo());
    }

    @Test
    public void testeApenasUmTipoPermitidoComoAnexo()
            throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException, UsuarioJaExisteException, TamanhoVazioException {

        // vamos criar um outro TuiterLite aqui, especificando que ele dever� se relacionar com o tipo Image
        TuiterLite<Image> tuiterLiteQueAceitaApenasImagensComoAnexo = new TuiterLite<>();
        tuiterLiteQueAceitaApenasImagensComoAnexo.cadastrarUsuario(usuario.getNome(), usuario.getEmail());
        Tuite<Image> tuite = tuiterLiteQueAceitaApenasImagensComoAnexo.tuitarAlgo(usuario, "Testando");

        // agora vamos anexar
        tuite.anexarAlgo(usuario.getFoto());
        assertNotNull(tuite.getAnexo());

        // Deixe as linhas seguintes comentadas, mas verifique o comportamento desejado indicado abaixo
        // (note que estamos tentando anexar outros tipos de objetos que n�o s�o Image).

//        tuite.anexarAlgo(usuario);       // essa linha, se fosse descomentada, daria erro de compila��o
//        tuite.anexarAlgo("1234");        // essa linha, se fosse descomentada, daria erro de compila��o
//        tuite.anexarAlgo(new Object());  // essa linha, se fosse descomentada, daria erro de compila��o
    }

    @Test
    public void testeHashtags()
            throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException, TamanhoVazioException {

        Tuite tuite = tuiterLite.tuitarAlgo(usuario, "#LAB7 Testando algo com #hashtag ao longo... #teste");

        // vamos testar se as hashtags (palavras iniciadas por #) foram corretamente detectadas

        assertTrue("Hashtags devem ser detectadas automaticamente e adicionadas ao tu�te",
                tuite.getHashtags().contains("#hashtag"));
        assertTrue("Hashtags devem ser detectadas automaticamente inclusive no come�o do tu�te",
                tuite.getHashtags().contains("#LAB7"));
        assertTrue("Hashtags devem ser detectadas automaticamente inclusive no fim do tuite",
                tuite.getHashtags().contains("#teste"));

        // e agora vamos ver se n�o h� falsos positivos
        assertFalse(tuite.getHashtags().contains("#algo"));
        assertFalse(tuite.getHashtags().contains("algo"));
        assertFalse(tuite.getHashtags().contains("#paralelepipedo"));

        // finalmente, vamos tuitar outra coisa para ver se as hashtags est�o sendo registradas corretamente no sistema
        tuiterLite.tuitarAlgo(usuario, "Repetindo o uso de uma hashtag #LAB7");
        assertEquals("Hashtags devem ser contabilizadas corretamente pelo sistema",
                "#LAB7", tuiterLite.getHashtagMaisComum());
    }

    @Test
    public void testarMultiplosSimbolosDeHashtag() throws UsuarioDesconhecidoException, TamanhoMaximoExcedidoException, TamanhoVazioException {
        Tuite tuite = tuiterLite.tuitarAlgo(usuario, "###LAB7 ######LAB7");
        assertTrue("O n�mero de caracteres # n�o deve importar",
                tuite.getHashtags().contains("#LAB7"));
        assertFalse("Para consulta, devemos usar sempre uma �nica #",
                tuite.getHashtags().contains("###LAB7"));
    }

//    @Test
//    public void testeTipoUsuario() throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException {
//        // sanity check
//        assertEquals("Um usu�rio sem nenhum tuite deve estar no n�vel INICIANTE",
//                NivelUsuario.INICIANTE, usuario.getNivel());
//
//        // vamos tuitar v�rias vezes com o mesmo usu�rio, mas n�o ainda a ponto de promov�-lo ao pr�ximo n�vel
//        for (int i = 1; i < NivelUsuario.SENIOR.getMinTuites(); i++) {
//            tuiterLite.tuitarAlgo(usuario, "Oi!");
//        }
//
//        // vamos verificar que o usu�rio ainda � INICIANTE
//        assertEquals("Um usu�rio n�o pode ser promovido antes de ter feito 100 tu�tes",
//                NivelUsuario.INICIANTE, usuario.getNivel());
//
//        // agora vamos produzir mais um tuite daquele usu�rio
//        tuiterLite.tuitarAlgo(usuario, "Oi! Vou ser promovido!");
//
//        // verifique a promo��o ao n�vel seguinte
//        assertEquals("O usu�rio deve ser promovido automaticamente a SENIOR quando atinge a marca de 100 tu�tes",
//                NivelUsuario.SENIOR, usuario.getNivel());
//
//        // vamos agora pass�-lo para o pr�ximo n�vel
//        for (int i = 1; i <= NivelUsuario.NINJA.getMinTuites() - NivelUsuario.SENIOR.getMinTuites(); i++) {
//            tuiterLite.tuitarAlgo(usuario, "Para o alto e avante!");
//        }
//
//        // verifique a promo��o ao n�vel seguinte
//        assertEquals("O usu�rio deve ser promovido automaticamente a NINJA quando atinge a marca de 500 tu�tes",
//                NivelUsuario.NINJA, usuario.getNivel());
//    }
//
    /////
    /////   ATEN��O: Este teste deve rodar rapidamente (poucos segundos)
    /////
    @Test
    public void testePerformanceContabilizacaoDasHashtags()
            throws TamanhoMaximoExcedidoException, UsuarioDesconhecidoException, TamanhoVazioException {

        for (int i = 1; i <= 200_000; i++) {
            String hashtag = String.format("#%d", i);
            tuiterLite.tuitarAlgo(usuario, hashtag);
        }
        tuiterLite.tuitarAlgo(usuario, "#5");
        assertEquals("#5", tuiterLite.getHashtagMaisComum());
    }

    /////
    /////   ATEN��O: Este teste deve rodar rapidamente (poucos segundos)
    /////
    @Test
    public void testePerformanceTuites() throws UsuarioJaExisteException, TamanhoMaximoExcedidoException, TamanhoVazioException {
        // vamos cadastrar um n�mero grande de usu�rios
        for (int i = 1; i <= 300_000; i++) {
            String nome = String.format("Usu�rio %d", i);
            String email = String.format("usuario%d@email.com", i);
            tuiterLite.cadastrarUsuario(nome, email);
        }

        // agora vamos tentar fazer um n�mero grande de tu�tes com usu�rio desconhecido
        Usuario usuarioNaoCadastrado = new Usuario("Usu�rio Desconhedido", "unknown@void.com");
        for (int i = 1; i <= 300_000; i++) {
            try {
                tuiterLite.tuitarAlgo(usuarioNaoCadastrado, "Teste");
            } catch (UsuarioDesconhecidoException e) {
                // ok, essa exce��o � esperada
            }
        }
    }
    
    @Test
    public void testarTamanhoMaximoException () throws TamanhoMaximoExcedidoException{
    	String texto = new String();
    	for (int excessoCaracteres = 1; excessoCaracteres <= 100; excessoCaracteres++) {

            // cria uma String muito grande
            int tamanho = TuiterLite.TAMANHO_MAXIMO_TUITES + 1;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= tamanho; i++) {
                sb.append("x");
            }
             texto = sb.toString();

            
        }
    	try {
            tuiterLite.tuitarAlgo(usuario, texto);
            fail("a exce��o deveria ser lan�ada");

        } catch (Exception e) {
           assertTrue(true);
           System.out.println(e.getMessage());
       }
    }
    
    @Test
    public void testarUsuarioDesconhecidoException () throws UsuarioDesconhecidoException{
    	String texto = "abc";
    	Usuario usuario = new Usuario("fulanno", "novo.gmail");
    	  
    	try {
            tuiterLite.tuitarAlgo(usuario, texto);
            fail("a exce��o deveria ser lan�ada");

        } catch (Exception e) {
           assertTrue(true);
           System.out.println(e.getMessage());
       }
    }
    
    @Test
    public void testarTamanhoVazioException () throws TamanhoVazioException{
    	String texto = new String();
    	  
    	try {
            tuiterLite.tuitarAlgo(usuario, texto);
            fail("a exce��o deveria ser lan�ada");

        } catch (Exception e) {
           assertTrue(true);
           System.out.println(e.getMessage());
       }
    }
    
    @Test
    public void testarUsuarioJaExisteException () throws UsuarioJaExisteException{
    	 
    	try {
            tuiterLite.cadastrarUsuario("fulano", "fulano@teste.com");
            fail("a exce��o deveria ser lan�ada");

        } catch (Exception e) {
           assertTrue(true);
           System.out.println(e.getMessage());
       }
    }
}