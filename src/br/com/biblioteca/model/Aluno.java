package br.com.biblioteca.model;

public class Aluno extends Usuario {
    public Aluno(String nome){
        super(nome);
    }

    @Override
    public double getLimiteSaldo() {
        return 15;
    }

    @Override
    public int getLimiteLivros(){
        return 3;
    }

    @Override
    public String obterTipo(){
        return "Aluno";
    }
}
