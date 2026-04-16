package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PagamentoService pagamentoService = new PagamentoService();
        ValidadorEmprestimo validadorEmprestimo = new ValidadorEmprestimo();
        CalculadoraMulta calculadoraMulta = new CalculadoraMulta();
        EmprestimoService emprestimoService = new EmprestimoService(validadorEmprestimo, calculadoraMulta, pagamentoService);

        Biblioteca biblioteca = new Biblioteca(pagamentoService, emprestimoService);

        MenuUsuario menuUsuario = new MenuUsuario(biblioteca, sc);
        MenuLivro menuLivro = new MenuLivro(biblioteca, sc);
        MenuFinanceiro menuFinanceiro = new MenuFinanceiro(biblioteca, sc);
        MenuPrincipal menuPrincipal = new MenuPrincipal(biblioteca,sc,menuUsuario,menuLivro,menuFinanceiro);

        menuPrincipal.iniciar();
    }
}
