package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.dao.GerencidorDeArquivos;
import br.com.biblioteca.model.Livro;

import java.util.List;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuarRodar = true;
        Biblioteca minhaBiblioteca = new Biblioteca();
        menuGeral menu = new menuGeral(minhaBiblioteca);
        List<Livro> livrosSalvos = GerencidorDeArquivos.carregarLivros();
        minhaBiblioteca.setAcervo(livrosSalvos);

        do {
            System.out.println("Bem-vindo ao Menu br.com.biblioteca.controller.Biblioteca!!!");
            System.out.println("Digite a opção desejada: ");
            System.out.println("[1] - Usuário");
            System.out.println("[2] - Livros");
            System.out.println("[3] - Pagamentos e saldos");
            System.out.println("[4] - Sair");

            int opcaoUsuario = sc.nextInt();

            switch (opcaoUsuario) {
                case 1: sc.nextLine();
                    menu.menuUsuario();
                    break;
                case 2: sc.nextLine();
                    menu.menuLivro();
                    break;
                case 3:
                    menu.menuPagamento();
                    break;
                case 4:
                    System.out.println("Salvando dados antes de fechar...");
                    GerencidorDeArquivos.salvarLivros(minhaBiblioteca.getAcervo());
                    continuarRodar = false;
                    break;
            }
        } while (continuarRodar);
    }
}









