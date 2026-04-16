package br.com.biblioteca.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public abstract class Usuario {
    private static int contarId = 1;
    private Integer id;
    private String nome;
    private List<Livro> livroEmprestado;
    private Pagamento saldo;



    public Usuario (String nome){
        this.nome = nome;
        this.livroEmprestado = new ArrayList<>();
        this.saldo = new Pagamento();
        this.id = contarId++;
    }

    public void adicionarLivro(Livro livro){
        this.livroEmprestado.add(livro);
    }

    public void removerLivro(Livro livro){
        this.livroEmprestado.removeIf(l -> l.getId() == livro.getId());
    }

    public String getNome(){return nome;}
    public Integer getId(){return id;}
    public  List<Livro> getlivroEmprestado(){return Collections.unmodifiableList(livroEmprestado);}
    public Pagamento getSaldo() {
        return saldo;
    }

    public abstract double getLimiteSaldo();
    public abstract int getLimiteLivros();

    public abstract String obterTipo();

    @Override
    public String toString() {
        return "Nome: " + this.nome + " | ID: " + this.id + " | Saldo: R$ " + this.saldo;
    }

}
