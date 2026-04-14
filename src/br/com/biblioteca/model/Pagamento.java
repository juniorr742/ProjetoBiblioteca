package br.com.biblioteca.model;

import java.time.LocalDateTime;

public class Pagamento {
    double saldoDevedor;
    private LocalDateTime dataUltimaOperacao;


    public Pagamento(){
        this.saldoDevedor = 0;
        this.dataUltimaOperacao = LocalDateTime.now();
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public LocalDateTime getDataUltimaOperacao() {
        return dataUltimaOperacao;
    }

    public void aumentarDebito(double valor){
        if (valor > 0){
            this.saldoDevedor += valor;
        }else {
            System.out.println("ERRO: valor negativo!");
        }
        atualizarData();
    }

    public void reduzirValor(double valor){
        if (valor > 0 && valor <= this.saldoDevedor){
            this.saldoDevedor -= valor;
            atualizarData();
        }else if (valor > this.saldoDevedor){
            System.out.println("ERRO: Pagamento maior que o saldo devedor.");
        }else {
            System.out.println("ERRO: Valor negativo.");
        }
        atualizarData();
    }

    public void quitarTotalmente(){
        this.saldoDevedor = 0;
        atualizarData();
    }

    public void atualizarData(){
        this.dataUltimaOperacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "R$ " + this.saldoDevedor;
    }
}










    




