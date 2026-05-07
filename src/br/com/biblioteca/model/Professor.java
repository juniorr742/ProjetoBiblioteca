package br.com.biblioteca.model;

public class  Professor extends Usuario {
    public Professor(String nome){
        super(nome);
    }

    @Override
    public double getLimiteSaldo(){
        return 100;
    }
    @Override
    public int getLimiteLivros(){
        return 10;
    }
    @Override
    public String obterTipo(){
        return "Professor";
    }

}
