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


    public void quitarDivida (double saldoDevedor){
        if (saldoDevedor > 0){
            System.out.println("Saldo de: %f. Pagamento realizado com sucesso!");
            this.saldoDevedor = 0;
        }else {
            System.out.println("Você não tem débitos!");
        }
    }

    public void verificarStatus(){
        if (saldoDevedor == 0){
            System.out.println("Você não tem débitos");
        }else{
            System.out.printf("Seu débito é de %2.f", saldoDevedor);
        }

    }
    }


    




