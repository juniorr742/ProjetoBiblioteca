package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;

import java.util.Scanner;

public class MenuPrincipal extends MenuBase{
    private MenuUsuario mu;
    private MenuLivro ml;
    private MenuFinanceiro mf;
    boolean rodar = true;

    public MenuPrincipal(Biblioteca biblioteca, Scanner sc, MenuUsuario mu, MenuLivro ml, MenuFinanceiro mf){
        super(biblioteca, sc);
        this.mu = mu;
        this.ml = ml;
        this.mf = mf;
    }

    public void iniciar(){
        do {
            System.out.println("\n===MENU BIBLIOTECA===");
            System.out.println("[1] - Usuários | [2] - Livros | [3] - Financeiro | [0] - Sair");
            switch (lerInteiro()){
                case 1: mu.exibir();
                        break;
                case 2: ml.exibir();
                        break;
                case 3: mf.exibir();
                        break;
                case 0:
                    System.out.println("Encerrando sistema, até logo!");
                    rodar = false;
                    break;
            }
        }while (rodar);
    }
}
