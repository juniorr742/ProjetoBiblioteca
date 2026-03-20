import java.util.ArrayList;
import java.util.List;



public class Usuario {
    private String nome;
    private int id;
    private List<Livro> livroEmprestado;
    private Pagamento saldo;

    public Usuario (String nome, int id){
        this.nome = nome;
        this.id = id;
        this.livroEmprestado = new ArrayList<>();
        this.saldo = new Pagamento();
    }
    public String getNome(){return nome;}
    public int getId(){return id;}
    public  List<Livro> getlivroEmprestado(){return livroEmprestado;}

    public void pegarLivro(Livro livro){

       if (this.saldo.getSaldoDevedor() >= 45){
           System.out.println("Saldo devedor atingiu o limite, por favor realize o pagameto");
       }else{
        livroEmprestado.add(livro);
        saldo.registrarEmprestimo();
       }
    }

    public void devolverLivro (Livro livro){
        livroEmprestado.remove(livro);
    }

    public Pagamento getSaldo() {
        return saldo;
    }
}
