package controle;

import java.util.HashMap;
import java.util.HashSet;

import excecao.DevolucaoInvalidaException;
import excecao.LimiteEmprestimosExcedidoException;
import excecao.UsuarioNaoCadastradoException;
import modelo.Livro;
import modelo.Usuario;

public class Biblioteca {

    /** quantidade mínima de unidades de um livro que precisam existir nas estantes da biblioteca para
        que o livro possa ser emprestado */
    public static final int MIN_COPIAS_PARA_PODER_EMPRESTAR = 2;

    /** quantidade máxima de livros da biblioteca que podem estar emprestados, a qualquer tempo, a um mesmo usuário */
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
     * Cadastra um usuário. Caso o usuário já exista, atualiza seu nome e seu endereço.
     *
     * @param usuario o usuário a ser inserido/atualizado.
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
     * Retorna um usuário previamente cadastrado, a partir do CPF informado.
     *
     * @param cpf o CPF do usuário desejado
     * @return o usuário, caso exista; ou null, caso não exista nenhum usuário cadastrado com aquele CPF
     */
    public Usuario getUsuario(long cpf) {
        return this.usuarios.get(cpf);
    }

    /**
     * @return o número total de usuários cadastrados na biblioteca
     */
    public int getTotalDeUsuariosCadastrados() {
        return this.totalUsuarios;
    }

    /**
     * Adquire `quantidade` cópias do livro informado e as inclui no acervo da biblioteca.
     *
     * @param livro o livro sendo adquirido
     * @param quantidade o número de cópias do livro sendo adquiridas
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
     * Empresta um livro para um usuário da biblioteca.
     *
     * @param livro o livro a ser emprestado
     * @param usuario o usuário que está pegando emprestado o livro
     *
     * @return true, se o empréstimo foi bem-sucedido;
     *         false, se o livro não está disponível para empréstimo
     *         (IMPORTANTE: um livro é considerado disponível para empréstimo se há pelo menos
     *                      controle.Biblioteca.MIN_COPIAS_PARA_PODER_EMPRESTAR cópias daquele livro nas estantes da biblioteca;
     *                      isto é, as estantes da biblioteca jamais poderão ficar com menos do que
     *                      controle.Biblioteca.MIN_COPIAS_PARA_PODER_EMPRESTAR-1 cópias de qualquer livro de seu acervo)
     *
     * @throws UsuarioNaoCadastradoException se o usuário informado não está cadastrado na biblioteca
     * @throws LimiteEmprestimosExcedidoException se o usuário já está com muitos livros emprestados no momento
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
     * @param usuario o usuário que havia tomado emprestado aquele livro
     *
     * @throws DevolucaoInvalidaException se o livro informado não se encontra emprestado para o usuário informado
     */
    public void receberDevolucaoLivro(Livro livro, Usuario usuario) throws DevolucaoInvalidaException {
        int quantidadeEmprestada = this.usuariosEmprestimos.get(usuario);
        int quantidadeNaEstante = this.estante.get(livro);
        this.usuariosEmprestimos.put(usuario, quantidadeEmprestada-1);
        this.estante.put(livro, quantidadeNaEstante+1);
    }

    /**
     * Retorna a quantidade de livros emprestados ao usuário informado.
     *
     * @param usuario o usuário desejado
     * @return a quantidade de livros emprestados àquele usuário; caso o usuário não esteja devendo nenhum livro,
     *         ou não seja nem mesmo um usuário cadastrado na biblioteca, retorna zero, não deve nada
     */
    public int getQuantidadeDeLivrosDevidos(Usuario usuario) {
    	if(this.usuariosEmprestimos.get(usuario) == null) {
    		return 0;
    	}
        return this.usuariosEmprestimos.get(usuario);
    }

    /**
     * @return a quantidade total de livros nas estantes da biblioteca (isto é, a soma das quantidades de cópias
     *         não-emprestadas de todos os livros do acervo da biblioteca).
     */
    public int getQuantidadeDeLivrosNaEstante() {
        return this.quantidadeDeLivrosNaEstante; 
    }

    /**
     * Retorna o número de cópias do livro informado que existem na estante da biblioteca
     * (isto é, que não estão emprestados).
     *
     * @param livro o livro desejado
     * @return o número de cópias não-emprestadas daquele livro
     */
    public int getQuantidadeDeLivrosNaEstante(Livro livro) {
        if(this.estante.get(livro) == null) {
        	return 0;
        }
    	return this.estante.get(livro); 
    }
}