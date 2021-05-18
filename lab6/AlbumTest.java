package lab6;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlbumTest {

    private Album albumFigurinhas;
    private Repositorio repositorioFigurinhas;

    private static final int TAMANHO_DO_ALBUM = 200;
    private static final int ITENS_POR_PACOTE = 3;

    @Before  
    public void setUp() {
        this.repositorioFigurinhas = new Repositorio("album_copa2014", TAMANHO_DO_ALBUM);
        this.albumFigurinhas = new Album(repositorioFigurinhas, ITENS_POR_PACOTE);
    }

    private void popularAlbum(int[] posicoesDesejadas) {
        Pacotinho pacote = new Pacotinho(this.repositorioFigurinhas, posicoesDesejadas);
        this.albumFigurinhas.receberNovoPacotinho(pacote);
    }

    @Test
    public void testarPossuiFigurinhaParaFigurinhasExistentes() {
        popularAlbum(new int[] {1, 2, 3});

        for (int i = 1; i <= 3; i++) {
            assertTrue("Figurinhas j� inseridas devem ser encontradas",
                    this.albumFigurinhas.possuiItemColado(i));
        }
    }

    @Test
    public void testarPossuiFigurinhaParaFigurinhasAusentes() {
        popularAlbum(new int[] {1, 2, 3});

        assertFalse("N�o devemos encontrar no �lbum figurinhas nunca inseridas",
                this.albumFigurinhas.possuiItemColado(4));
        assertFalse("N�o devemos encontrar figurinhas de posi��es n�o-positivas",
                this.albumFigurinhas.possuiItemColado(-390));
        assertFalse("N�o devemos encontrar figurinhas maiores do que o tamanho",
                this.albumFigurinhas.possuiItemColado(TAMANHO_DO_ALBUM + 1));
    }

    @Test
    public void testarPossuiRepetidaParaFigurinhaRepetida() {
        popularAlbum(new int[] {1, 2, 3});
        assertFalse(this.albumFigurinhas.possuiItemRepetido(2));  // sanity check

        popularAlbum(new int[] {2, 8, 9});
        assertTrue(this.albumFigurinhas.possuiItemRepetido(2));
    }

    @Test
    public void testarGetTamanhoAlbum() {
        assertEquals(TAMANHO_DO_ALBUM, this.albumFigurinhas.getTamanho());
    }

    @Test
    public void testarGetContFigurinhas() {
        popularAlbum(new int[] {1, 2, 3});
        assertEquals(3, this.albumFigurinhas.getQuantItensColados());

        // vou agora abrir outro pacotinho com as mesmas figurinhas
        // e verificar que o �lbum ter� ainda 3 figurinhas

        popularAlbum(new int[] {1, 2, 3});
        assertEquals(3, this.albumFigurinhas.getQuantItensColados());
    }

    @Test
    public void testarGetQuantasFaltam() {
        popularAlbum(new int[] {1, 2, 3});
        assertEquals(TAMANHO_DO_ALBUM - 3,
                this.albumFigurinhas.getQuantItensFaltantes());
    }

    @Test
    public void testarAutoCompletar() {
        albumFigurinhas.autoCompletar();
        assertEquals("N�o deve ser poss�vel auto-completar um �lbum que esteja vazio",
                TAMANHO_DO_ALBUM, albumFigurinhas.getQuantItensFaltantes());

        // agora vamos adicionar pacotinhos aleat�rios at� o �lbum ficar quase cheio

        int minimoFigurinhasColadasParaAutoCompletar =
                (int) (TAMANHO_DO_ALBUM * Album.PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR / 100f);

        while (albumFigurinhas.getQuantItensColados() < minimoFigurinhasColadasParaAutoCompletar) {
            Pacotinho novoPacotinho = new Pacotinho(
                    this.repositorioFigurinhas, ITENS_POR_PACOTE);   
            albumFigurinhas.receberNovoPacotinho(novoPacotinho);
        }

        // sanity check
        assertTrue(albumFigurinhas.getQuantItensFaltantes() > 0);

        albumFigurinhas.autoCompletar();

        assertEquals("O �lbum deve estar completo ap�s uma chamada v�lida ao auto-completar " +
                        "(isto �, ap�s o percentual m�nimo de figurinhas j� ter sido obtido)",
                0, albumFigurinhas.getQuantItensFaltantes());  // �lbum completo!
        
    }

    @Test
    public void testarGetItemColado() {
        popularAlbum(new int[] {1, 2, 3});
        Figurinha figurinha = albumFigurinhas.getItemColado(2);

        assertNotNull(figurinha);

        assertEquals(2, figurinha.getPosicao());

        assertNull(albumFigurinhas.getItemColado(4));
    }

    @Test
    public void testarRejeicaoPacotinhosDeTamanhoErrado() {
        popularAlbum(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8});

        assertEquals("Pacotes de tamanho distinto do informado na constru��o " +
                "do �lbum devem ser rejeitados",
                0, albumFigurinhas.getQuantItensColados());
    }

}