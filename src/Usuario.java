import java.util.ArrayList;
import java.util.List;



public abstract class Usuario {
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

    public String meusDados(int id){
        if (this.id == id){

            for (Livro livro: livroEmprestado){
                System.out.println(livro);
            }

        } else {
            return "O id não está correto";
        }
        return "Nome: " + getNome() + " | id: " + getId() + " | saldo: " + getSaldo();
    }


    public void pegarLivro(Livro livro){

       if (this.saldo.getSaldoDevedor() >= getLimiteSaldo() || livroEmprestado.size() >= getLimiteLivros()){
           System.out.println("Saldo devedor atingiu o limite, por favor realize o pagameto");
       }else{
        livroEmprestado.add(livro);
        saldo.registrarEmprestimo();
       }
    }

    public void devolverLivro (Livro livro, int diasCorridos){

        if (livroEmprestado.contains(livro)){
            int prazo = 7;

            if (diasCorridos > prazo){
                int diasAtraso = diasCorridos - prazo;
                double valorMulta = diasAtraso * 2;
                this.saldo.registrarMulta(valorMulta);

                System.out.println("Livro devolvido com atraso de "+diasAtraso + " dias.");
                System.out.println("Multa de " + valorMulta + " R$ aplicada");
            } else {
                System.out.println("livro devolvido no prazo.");
            }
                livroEmprestado.remove(livro);

        }else {
            System.out.println("Livro não encontrado com esse usuário");
        }


    }

    public Pagamento getSaldo() {
        return saldo;
    }

    public abstract double getLimiteSaldo();
    public abstract int getLimiteLivros();

    public void listarLista(){
       if (livroEmprestado.isEmpty()){
           System.out.println("Você não tem nenhum livro");
       }else {
           for (Livro livro: livroEmprestado){
               System.out.println(livro);
           }
       }

    }



}
