package br.com.biblioteca.model;

import java.time.LocalDate;


public class RegistroEmprestimo {
    private static long ContadorId = 1;
    private long idTransacao;
    private int idUsuario;
    private int idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean finalizado;

    public RegistroEmprestimo(int idUsuario, int idLivro){
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = LocalDate.now();
        this.finalizado = false;
        this.idTransacao = ContadorId++;
    }

    public long getIdTransacao() {
        return idTransacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void finalizarEmprestimo(){
        this.dataDevolucao = LocalDate.now();
        this.finalizado = true;
    }

    @Override
    public String toString(){
        return String.format("Transação: %d | Usuário ID: %d | br.com.biblioteca.model.Livro ID: %d | Data: %s | Status: %s",
                idTransacao, idUsuario, idLivro, dataEmprestimo, (finalizado ? "Devolvido":"Ativo"));
    }
}
