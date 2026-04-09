package br.com.biblioteca.model;

import java.util.Objects;

public class Livro {
    private static int contadorId = 1;
    private String titulo;
    private String autor;
    private boolean disponivel;
    private int prazo = 7;
    int id;

        public Livro(String titulo, String autor) {
            if (titulo == null || titulo.isBlank()){
                this.titulo = "Título indefinido";
            }else {
                this.titulo = titulo;
            }
            this.autor = autor;
            this.disponivel = true;
            this.id = contadorId;
            contadorId++;

         }



    public String getTitulo() {
            return titulo; }

    public String getAutor() {
        return autor;
    }
    public void setDisponivel(boolean disponivel) {
            this.disponivel = disponivel;}
    public boolean isDisponivel() {
            return disponivel;}
    public int getId(){
            return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return  "Id: " + getId() + "Título: " + this.titulo + " (Autor: " + this.autor + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(titulo, livro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(titulo);
    }
}
