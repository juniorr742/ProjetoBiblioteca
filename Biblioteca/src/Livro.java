public class Livro {
    //Vamos fazer privado para garamtir o encapsulamento
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;   //Todo livro sempre vai começar verdadeiro


    }


    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public boolean isDisponivel(){
        return disponivel;
    }
    public void exibirDetalhes(){
       String status = disponivel ? "Disponivel" : "Emprestado";
        System.out.println("Título: " + titulo + "/ Autor " + autor + " / Status: " + status);
    }
}
