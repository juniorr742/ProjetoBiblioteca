package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class MenuBase {
    protected Biblioteca biblioteca;
    protected Scanner sc;

    public MenuBase(Biblioteca biblioteca, Scanner sc){
        this.biblioteca = biblioteca;
        this.sc = sc;
    }

    protected int lerInteiro(){
        while (true){
            try {
                return Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.print("ERRO: Digite apenas números válidos: ");
            }
        }
    }

    protected Usuario selecionarUsuario(String nome){
        List<Usuario> lista = biblioteca.procurarUsuarioPorNome(nome);
        if (lista == null || lista.isEmpty()){
            System.out.println("AVISO: Nenhum usuário encontrado.");
            return null;
        }
        System.out.println("\nUsuários encontrados: ");
        for (Usuario u: lista){
            System.out.printf("[%d] - %s\n", u.getId(), u.getNome());
        }
        System.out.println("Digite o id desejado: ");
        return biblioteca.buscarUsuariosPorId(lerInteiro());
    }

    protected Livro selecionarLivro(String titulo) {
        List<Livro> lista = biblioteca.procurarLivroPorTitulo(titulo);
        if (lista == null || lista.isEmpty()){
            System.out.println("AVISO: Nenhum livro encontrado.");
            return null;
        }
        System.out.println("\nLivros encontrados: ");
        for (Livro l : lista){
            System.out.printf("[%d] - %s (%s)\n", l.getId(), l.getTitulo(), l.isDisponivel());
        }
        System.out.print("Selecione o ID desejado: ");
        return biblioteca.buscarLivroPorId(lerInteiro());
    }
}
