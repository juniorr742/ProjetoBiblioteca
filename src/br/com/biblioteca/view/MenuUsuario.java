package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.factory.UsuarioFactory;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.Scanner;

public class MenuUsuario extends MenuBase{
    public MenuUsuario(Biblioteca biblioteca, Scanner sc) {
        super(biblioteca, sc);
    }

        public void exibir(){

        boolean continuarUsuario =  true;

        do {
            System.out.println("==MENU USUÁRIO==");
            System.out.println("[1] - Cadastrar |  [2] - Verificar status | [0] - Voltar");

            switch (lerInteiro()){
                case 1:
                    System.out.println("[1] - Aluno | [2] - Professor");
                    int tipoEscolhido = lerInteiro();
                    System.out.println("Qual seu nome: ");
                    String nome = sc.nextLine();
                    Usuario usuarioNovo = UsuarioFactory.criarUsuario(tipoEscolhido, nome);

                    if (usuarioNovo != null){
                        biblioteca.adicionarUsuario(usuarioNovo);
                        System.out.println("Usuário " + usuarioNovo.getNome() + " adicionado com sucesso!");
                    }else {
                        System.out.println("[AVISO] Erro ao cadastrar usuário");
                    }
                    break;
                case 2:
                    System.out.println("Digite seu nome para a busca: ");
                    Usuario u = selecionarUsuario(sc.nextLine());
                    System.out.println("Verificando dados de: " + u.getNome());
                    System.out.printf("ID: %d | Nome: %s | Limite de livros: %d", u.getId(), u.getNome(), u.getLimiteLivros());
                    biblioteca.verificarSaldo(u.getId());
                    break;
                case 3:
                    System.out.println("Voltando...");
                    continuarUsuario = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }while (continuarUsuario);

        }
    }

