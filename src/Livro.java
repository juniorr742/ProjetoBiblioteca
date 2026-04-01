import java.util.Objects;

public class Livro {

    private String titulo;
    private String autor;
    private boolean disponivel;
    private int prazo = 7;

        public Livro(String titulo, String autor) {
            if (titulo == null || titulo.trim().isEmpty()){
                this.titulo = "Título indefinido";
            }else {
                this.titulo = titulo;
            }
            this.autor = autor;
            this.disponivel = true;
         }

        public String getTitulo(){return titulo; }
        public void setDisponivel(boolean disponivel) {this.disponivel = disponivel;}
        public boolean isDisponivel() {return disponivel;}

    @Override
    public String toString() {

        return "Título: " + this.titulo + " (Autor: " + this.autor + ")";
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
