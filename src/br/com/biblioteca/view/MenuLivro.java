package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.Scanner;

public class MenuLivro extends MenuBase{
    public MenuLivro(Biblioteca biblioteca, Scanner sc){
        super(biblioteca, sc);
    }

    public void exibir(){
        boolean continuarLivro = true;
        do {
            System.out.println("[1] - Cadastrar | [2] - Emprestar | [3] - Devolver | [0] - Voltar");
            System.out.println("Digite a opção desejada: ");

            switch (lerInteiro()){
                case 1:
                    System.out.println("Título: ");
                    String t = sc.nextLine();
                    System.out.println("Autor: ");
                    String a = sc.nextLine();
                    biblioteca.adicionarLivro(new Livro(t, a));
                    System.out.println("Livro cadastrado.");
                    break;
                case 2:
                    System.out.println("Nome do usuário: ");
                    Usuario u = selecionarUsuario(sc.nextLine());
                    if (u == null){
                        System.out.println("Usuário não encontrado! tente novamente.");
                        continue;
                    }
                    System.out.println("Nome do livro: ");
                    Livro l = selecionarLivro(sc.nextLine());
                    if (l == null){
                        System.out.println("Livro não encontrado! tente novamente.");
                        continue;
                    }
                    biblioteca.emprestarLivro(l.getId(), u.getId());
                    System.out.println("Atenção para devolver o livro, será necessário saber o id.");
                    break;
                case 3:
                    System.out.println("Digite o nome do usuário que deseja devolver: ");
                    Usuario U = selecionarUsuario(sc.nextLine());
                    if (U == null){
                        System.out.println("[AVISO] Usuário inválido!");
                        continue;
                    }
                    if (U.getlivroEmprestado().isEmpty()){
                        System.out.println("[AVISO] Esse usuário não possui livros em sua posse.");
                        continue;
                    }
                    System.out.println("\nLivros atualmente com "+ U.getNome() +":");
                    for (Livro L : U.getlivroEmprestado()){
                        System.out.printf("[%d] - %s", L.getId(), L.getTitulo());
                    }
                    System.out.println("Digite o id do livro que quer devolver: ");
                    int idLivro = lerInteiro();
                    Livro livroParaDevolver = U.getlivroEmprestado().stream().filter(leitura -> leitura.getId() == idLivro).findFirst().orElse(null);

                    if (livroParaDevolver == null){
                        System.out.println("[AVISO] - Esse livro não está com o usuário ou id incorreto.");
                        continue;
                    }
                    System.out.println("Quantos dias o usuário ficou com o livro?");
                    int diasCorridos = lerInteiro();

                    biblioteca.devolverLivro(idLivro, U.getId(), diasCorridos);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    continuarLivro = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }while (continuarLivro);
    }
}
