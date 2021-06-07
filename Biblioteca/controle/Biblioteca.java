package controle;

import java.util.HashMap;
import java.util.HashSet;

import excecao.DevolucaoInvalidaException;
import excecao.LimiteEmprestimosExcedidoException;
import excecao.UsuarioNaoCadastradoException;
import modelo.Livro;
import modelo.Usuario;

public class Biblioteca {

    /** quantidade m�nima de unidades de um livro que precisam existir nas estantes da biblioteca para
        que o livro possa ser emprestado */
    public static final int MIN_COPIAS_PARA_PODER_EMPRESTAR = 2;

    /** quantidade m�xima de livros da biblioteca que podem estar emprestados, a qualquer tempo, a um mesmo usu�rio */
    public static final int MAX_LIVROS_DEVIDOS = 3;

    HashMap<Livro, Integer> estante;
    HashMap<Long, Usuario> usuarios;
    HashMap<Usuario, Integer> usuariosEmprestimos;


    
    private int quantidadeDeLivrosNaEstante=0;
    private int totalUsuarios=0;

    
    public Biblioteca() {
        this.estante = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.usuariosEmprestimos = new HashMap<>();
    }

    /**
     * Cadastra um usu�rio. Caso o usu�rio j� exista, atualiza seu nome e seu endere�o.
     *
     * @param usuario o usu�rio a ser inserido/atualizado.
     */
    public void cadastrarUsuario(Usuario usuario) {
    	Usuario usuarioCadastro = this.usuarios.get(usuario.getCpf());
    	if(usuarioCadastro !=null) {
    		usuarioCadastro.setEndereco(usuario.getEndereco());
    		usuarioCadastro.setNome(usuario.getNome());
    		return;
    	}
        this.usuarios.put(usuario.getCpf(), usuario);
        this.usuariosEmprestimos.put(usuario, 0);
        this.totalUsuarios ++;
    }

    /**
     * Retorna um usu�rio previamente cadastrado, a partir do CPF informado.
     *
     * @param cpf o CPF do usu�rio desejado
     * @return o usu�rio, caso exista; ou null, caso n�o exista nenhum usu�rio cadastrado com aquele CPF
     */
    public Usuario getUsuario(long cpf) {
        return this.usuarios.get(cpf);
    }

    /**
     * @return o n�mero total de usu�rios cadastrados na biblioteca
     */
    public int getTotalDeUsuariosCadastrados() {
        return this.totalUsuarios;
    }

    /**
     * Adquire `quantidade` c�pias do livro informado e as inclui no acervo da biblioteca.
     *
     * @param livro o livro sendo adquirido
     * @param quantidade o n�mero de c�pias do livro sendo adquiridas
     */
    public void incluirLivroNoAcervo(Livro livro, int quantidade) {
        if(this.estante.get(livro)!= null) {
        	 int quantidadeDoLivro = this.estante.get(livro); 
        	 quantidade += quantidadeDoLivro;
        }
        this.estante.put(livro, quantidade);
        this.quantidadeDeLivrosNaEstante+=quantidade;
    }

    /**
     * Empresta um livro para um usu�rio da biblioteca.
     *
     * @param livro o livro a ser emprestado
     * @param usuario o usu�rio que est� pegando emprestado o livro
     *
     * @return true, se o empr�stimo foi bem-sucedido;
     *         false, se o livro n�o est� dispon�vel para empr�stimo
     *         (IMPORTANTE: um livro � considerado dispon�vel para empr�stimo se h� pelo menos
     *                      controle.Biblioteca.MIN_COPIAS_PARA_PODER_EMPRESTAR c�pias daquele livro nas estantes da biblioteca;
     *                      isto �, as estantes da biblioteca jamais poder�o ficar com menos do que
     *                      controle.Biblioteca.MIN_COPIAS_PARA_PODER_EMPRESTAR-1 c�pias de qualquer livro de seu acervo)
     *
     * @throws UsuarioNaoCadastradoException se o usu�rio informado n�o est� cadastrado na biblioteca
     * @throws LimiteEmprestimosExcedidoException se o usu�rio j� est� com muitos livros emprestados no momento
     */
    public boolean emprestarLivro(Livro livro, Usuario usuario)
            throws UsuarioNaoCadastradoException, LimiteEmprestimosExcedidoException {
        if(this.usuarios.get(usuario.getCpf()) == null) {
        	throw new UsuarioNaoCadastradoException();
        }
        int quantidadeEmprestimos = this.usuariosEmprestimos.get(usuario);
        
        if(quantidadeEmprestimos >= MAX_LIVROS_DEVIDOS) {
        	throw new LimiteEmprestimosExcedidoException();
        }
        if(this.estante.get(livro) == null) {
        	return false;
        }
        int quantidadeNaEstante = this.estante.get(livro);
        if(quantidadeNaEstante < MIN_COPIAS_PARA_PODER_EMPRESTAR) {
        	return false;
        }
        this.usuariosEmprestimos.put(usuario, quantidadeEmprestimos+1);
        this.estante.put(livro, quantidadeNaEstante-1);
    	return true;  
    }

    /**
     * Recebe de volta um livro que havia sido emprestado.
     *
     * @param livro o livro sendo devolvido
     * @param usuario o usu�rio que havia tomado emprestado aquele livro
     *
     * @throws DevolucaoInvalidaException se o livro informado n�o se encontra emprestado para o usu�rio informado
     */
    public void receberDevolucaoLivro(Livro livro, Usuario usuario) throws DevolucaoInvalidaException {
        int quantidadeEmprestada = this.usuariosEmprestimos.get(usuario);
        int quantidadeNaEstante = this.estante.get(livro);
        this.usuariosEmprestimos.put(usuario, quantidadeEmprestada-1);
        this.estante.put(livro, quantidadeNaEstante+1);
    }

    /**
     * Retorna a quantidade de livros emprestados ao usu�rio informado.
     *
     * @param usuario o usu�rio desejado
     * @return a quantidade de livros emprestados �quele usu�rio; caso o usu�rio n�o esteja devendo nenhum livro,
     *         ou n�o seja nem mesmo um usu�rio cadastrado na biblioteca, retorna zero, n�o deve nada
     */
    public int getQuantidadeDeLivrosDevidos(Usuario usuario) {
    	if(this.usuariosEmprestimos.get(usuario) == null) {
    		return 0;
    	}
        return this.usuariosEmprestimos.get(usuario);
    }

    /**
     * @return a quantidade total de livros nas estantes da biblioteca (isto �, a soma das quantidades de c�pias
     *         n�o-emprestadas de todos os livros do acervo da biblioteca).
     */
    public int getQuantidadeDeLivrosNaEstante() {
        return this.quantidadeDeLivrosNaEstante; 
    }

    /**
     * Retorna o n�mero de c�pias do livro informado que existem na estante da biblioteca
     * (isto �, que n�o est�o emprestados).
     *
     * @param livro o livro desejado
     * @return o n�mero de c�pias n�o-emprestadas daquele livro
     */
    public int getQuantidadeDeLivrosNaEstante(Livro livro) {
        if(this.estante.get(livro) == null) {
        	return 0;
        }
    	return this.estante.get(livro); 
    }
}