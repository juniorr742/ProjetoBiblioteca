public class Livro {
    private String titulo;
    private String autor;
    private boolean disponivel;

        public Livro(String titulo, String autor){
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
         }

        public String getTitulo(){return titulo; }
        public void setDisponivel(boolean disponivel) {this.disponivel = disponivel;}
        public boolean isDisponivel() {return disponivel;}

}
