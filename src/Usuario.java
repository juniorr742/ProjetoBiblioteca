import java.util.ArrayList;
import java.util.List;



public abstract class Usuario {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private List<Livro> livroEmprestado;
    private Pagamento saldo;



    public Usuario (String nome){
        this.nome = nome;
        this.livroEmprestado = new ArrayList<>();
        this.saldo = new Pagamento();
        this.id = contadorId;
        contadorId++;
    }

    public String getNome(){return nome;}
    public int getId(){return id;}
    public  List<Livro> getlivroEmprestado(){return livroEmprestado;}


    @Override
    public String toString() {
        return "Nome: " + this.nome + " | ID: " + this.id + " | Saldo: R$ " + this.saldo;
    }



    public void pegarLivro(Livro livro){

       boolean jaPossuiEsseExemplar = livroEmprestado.stream().anyMatch(l -> l.getId() == livro.getId());
       if (jaPossuiEsseExemplar){
           System.out.println("Você ja esta com esse livro");
           return;
       }

       if (this.saldo.getSaldoDevedor() >= getLimiteSaldo() || livroEmprestado.size() >= getLimiteLivros()){
           System.out.println("Saldo devedor atingiu o limite, por favor realize o pagameto");
        }else{
           livroEmprestado.add(livro);
            saldo.registrarEmprestimo();
        }
    }

    public void devolverLivro (Livro livro, int diasCorridos){

        boolean possuiOLivro = livroEmprestado.stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (possuiOLivro){
            int prazo = 7;

            if (diasCorridos > prazo){
                int diasAtraso = diasCorridos - prazo;
                double valorMulta = diasAtraso * 2;
                this.saldo.registrarMulta(valorMulta);

                System.out.println("Livro devolvido com atraso de " + diasAtraso + " dias.");
                System.out.println("Multa de " + valorMulta + " R$ aplicada");
            } else {
                System.out.println("livro devolvido no prazo.");
            }
            livroEmprestado.removeIf(l -> l.getId() == livro.getId());


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
