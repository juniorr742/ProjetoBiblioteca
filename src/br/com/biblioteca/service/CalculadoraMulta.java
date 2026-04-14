package br.com.biblioteca.service;

import br.com.biblioteca.config.BibliotecaConfig;

public class CalculadoraMulta {
    int prazo = BibliotecaConfig.PRAZO_DEVOLUCO_PADRAO_DIAS;
    double multaDiária = BibliotecaConfig.VALOR_MULTA_DIARIA;

    public double valorCalculado(int diasCorridos){
        if (diasCorridos < prazo){
            return 0.0;
        }
        double valor = (diasCorridos - prazo) * multaDiária;
        return valor;
    }
}
