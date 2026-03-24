public class Pagamento {
    double saldoDevedor;
    int custoFixo = 15;

    public void pagamento(){
        this.saldoDevedor = 0;
    }

    public void registrarEmprestimo(){
        this.saldoDevedor += custoFixo;
        System.out.println("Empréstimo realizado com sucesso!");
    }


    public void quitarDivida (){
        if (this.saldoDevedor > 0){
            System.out.printf("Saldo de: %.2f. Pagamento realizado com sucesso!", saldoDevedor);
            this.saldoDevedor = 0;
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
}


    




