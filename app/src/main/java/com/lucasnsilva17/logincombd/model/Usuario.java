package com.lucasnsilva17.logincombd.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer id;
    private String nome;
    private String senha;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
