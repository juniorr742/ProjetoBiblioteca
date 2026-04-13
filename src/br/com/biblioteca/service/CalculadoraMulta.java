package br.com.biblioteca.service;

public class CalculadoraMulta {
    public static final int PRAZO_PADRAO = 7;
    public static final double VALOR_MULTA_DIARIA = 2.0;

    public double valorCalculado(int diasCorridos){
        if (diasCorridos < PRAZO_PADRAO){
            return 0.0;
        }
        double valor = (diasCorridos - PRAZO_PADRAO) * VALOR_MULTA_DIARIA;
        return valor;
    }
}
