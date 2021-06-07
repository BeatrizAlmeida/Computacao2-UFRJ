package modelo;

import java.util.Objects;

public class Usuario {

	private String nome, endereco;
	private long cpf;
    public Usuario(String nome, long cpf) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public long getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        if(usuario.getCpf() == this.getCpf()) {
        	return true;
        }
        return false;
    }
}
