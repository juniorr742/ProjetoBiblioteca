package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.config.BibliotecaConfig;

import java.util.Scanner;

public class MenuFinanceiro extends MenuBase{
    public MenuFinanceiro(Biblioteca biblioteca, Scanner sc){
        super(biblioteca, sc);
    }

    public void exibir(){
        boolean continuarFinanceiro = true;
        do {
            System.out.println("==MENU FINANCEIRO==");
            System.out.println("[1] - Verificar saldo | [2] - Realizar pagamento | [3] - Verificar custos fixos | [0] - Voltar");

            switch (lerInteiro()){
                case 1:
                    System.out.println("Digite o nome do usuário: ");
                    Usuario u = selecionarUsuario(sc.nextLine());
                    if (u != null) biblioteca.verificarSaldo(u.getId());
                    break;
                case 2:
                    System.out.println("Digite o nome do usuário: ");
                    Usuario U = selecionarUsuario(sc.nextLine());
                    if (U != null) {
                        biblioteca.realizarPagamento(U.getId());
                        System.out.println("Pagamento realizado.");
                    }
                    break;
                case 3:
                    double valorEmprestimo = BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
                    double valorMulta = BibliotecaConfig.VALOR_MULTA_DIARIA;
                    int prazo = BibliotecaConfig.PRAZO_DEVOLUCO_PADRAO_DIAS;
                    System.out.printf("Valor do Empréstimo = %.2f | Valor da multa = %.2f/dia | Prazo de devolução: %d dias\n",valorEmprestimo,valorMulta,prazo);
                    break;
                case 0 :
                    System.out.println("Voltando...");
                    continuarFinanceiro = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }while (continuarFinanceiro);
    }
}
