import java.util.ArrayList;
import java.util.List;



public class Usuario {
    private String nome;
    private int id;
    private List<Livro> livroEmprestado;

    public Usuario (String nome, int id){
        this.nome = nome;
        this.id = id;
        this.livroEmprestado = new ArrayList<>();
    }
    public String getNome(){return nome;}
    public int getId(){return id;}
    public  List<Livro> getlivroEmprestado(){return livroEmprestado;}

    public void pegarLivro(Livro livro){
        livroEmprestado.add(livro);
    }

    public void devolverLivro (Livro livro){
        livroEmprestado.remove(livro);
    }

}
