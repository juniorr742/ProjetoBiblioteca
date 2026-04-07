import java.time.LocalDateTime;

public class Pagamento {
    double saldoDevedor;
    private static final double custoFixo = 15;
    private static int contadorPagamento = 1;
    private int id;
    private LocalDateTime dataUltimaOperacao;


    public Pagamento(){
        this.id = contadorPagamento;
        contadorPagamento++;
        this.saldoDevedor = 0;
        this.dataUltimaOperacao = LocalDateTime.now();
    }

    public int getId(){
        return id;
    }


    public void registrarEmprestimo(){
        this.saldoDevedor += custoFixo;
        this.dataUltimaOperacao = LocalDateTime.now();
        System.out.println("Empréstimo realizado com sucesso! (ID Pagamento: " + id +")");
    }


    public void quitarDivida (){
        if (this.saldoDevedor > 0){
            System.out.printf("Saldo de: %.2f. Pagamento realizado com sucesso!\n", saldoDevedor);
            this.saldoDevedor = 0;
            this.dataUltimaOperacao = LocalDateTime.now();
        }else {
            System.out.println("Você não tem débitos!");
        }
    }

    public void registrarMulta(double valor){
        if (valor > 0){
            this.saldoDevedor += valor;
            System.out.println("Registro foi um sucesso!");
        }
    }

    public void verificarStatus(){
        if (saldoDevedor == 0){
            System.out.println("Você não tem débitos");
        }else{
            System.out.printf("Seu débito é de %.2f", saldoDevedor);
        }

    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public double getCustoFixo(){
        return custoFixo;
    }

    @Override
    public String toString() {
        return "R$ " + this.saldoDevedor;
    }
}







    




