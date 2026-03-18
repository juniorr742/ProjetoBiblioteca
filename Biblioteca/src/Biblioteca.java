import  java.util.ArrayList;
import  java.util.List;



public class Biblioteca {
    private List<Livro> acervo;

    public Biblioteca(){
        this.acervo = new ArrayList<>();

    }

    public void adicionarLivro(Livro novoLivro){
        System.out.println("Livro "+ novoLivro.getTitulo() + " adicionado ao acervo!");
        acervo.add(novoLivro);
        System.out.println("Livro " + novoLivro.getTitulo() + " adicionado ao acervo");
    }

    public void listarAcervo() {
        if (acervo.isEmpty()){
            System.out.println("A biblioteca está vazia no momento");
        } else {
            System.out.println("------------LISTA DE LIVROS----------");
            for (Livro l : acervo) {
                l.exibirDetalhes();
            }
        }
    }

}
