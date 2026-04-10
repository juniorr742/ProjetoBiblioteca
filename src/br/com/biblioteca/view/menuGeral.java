package br.com.biblioteca.view;

import br.com.biblioteca.controller.Biblioteca;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.*;

import java.util.Scanner;

public class menuGeral {
    Scanner sc = new Scanner(System.in);
    //todo: nome da variável info não é muito intuitivo, talvez seja melhor algo como "biblioteca" ou "sistemaBiblioteca"
    private Biblioteca info;
    LivroDAO meuBanco = new LivroDAO();
    //todo: linha vazia

    public menuGeral(Biblioteca biblioteca) {
        this.info = biblioteca;
    }

    public void menuUsuario() {

        boolean continuarUsuario = true;
        //todo: linha vazia

        do {
            System.out.println("Digite sua opção no menu usuário: ");
            //todo: [1] cadastrar Aluno, [2] cadastrar Professor.. etc
            System.out.println("[1] - Cadastrar usuário");
            System.out.println("[2] - Verificar status");
            System.out.println("[3] - Sair");

            int menuUsuario = sc.nextInt();

            switch (menuUsuario) {
                case 1:
                    System.out.println("Você é aluno ou professor?");
                    sc.nextLine();
                    String subUsuario = sc.nextLine();
                    
                    if (subUsuario.equalsIgnoreCase("aluno")) {
                        //todo: implementar metodo privado para organizar melhor o codigo ex: cadastrarUsuario(subUsuario).
                        // se tiver uma serviço de usuario, o cadastro poderia ser feito lá, e aqui no menuUsuario() só chamaria o serviço de cadastro passando o tipo do usuário como parâmetro.
                        //  olhar todo [1] da classe BibliotecaController para mais detalhes.;
                        System.out.println("Digite seu nome: ");
                        String nomeAluno = sc.nextLine();

                        Usuario alunoNovo = new Aluno(nomeAluno);
                        info.adicionarUsuario(alunoNovo);

                        System.out.println("br.com.biblioteca.model.Aluno cadastrado com sucesso, seu novo id é: " + alunoNovo.getId());
                    } else {
                        System.out.println("Digite seu nome: ");
                        String nomeProfessor = sc.nextLine();

                        Usuario professorNovo = new Professor(nomeProfessor);
                        info.adicionarUsuario(professorNovo);

                        System.out.println("br.com.biblioteca.model.Professor cadastrado com sucesso, seu novo id é: " + professorNovo.getId());
                    }
                    break;
                case 2:
                    System.out.println("Digite sua Id: ");
                    int idProcura = sc.nextInt();

                    Usuario usuarioLogado = info.buscarUsuariosPorId(idProcura);

                    if (usuarioLogado != null) {
                        System.out.println("Nome: " + usuarioLogado.getNome());
                        System.out.println("Livros em sua posse: " + usuarioLogado.getlivroEmprestado());
                        System.out.println("Limite de gasto: R$ " + usuarioLogado.getLimiteSaldo());
                    } else {
                        System.out.println("Erro: Usuário inexistente!");
                    }
                    break;
                case 3:
                    continuarUsuario = false;
                    break;

                //todo: o que acontece se o usuário digitar um número diferente de 1, 2, 3 ou 4?
            }


        } while (continuarUsuario);
    }

    public void menuLivro(){
        boolean continuarMenu = true;

        do {
            System.out.println("Bem-vindo ao Menu sobre os livros!");
            System.out.println("[1] - Cadastrar livro");
            System.out.println("[2] - Listar livros cadastrados");
            System.out.println("[3] - Verificar livro específico");
            System.out.println("[4] - Pegar livro emprestado");
            System.out.println("[5] - Devolver livro");
            System.out.println("[6] - Sair");
            int opcaoMenu = sc.nextInt();

            switch (opcaoMenu){
                case 1:sc.nextLine();
                    System.out.println("Digite o nome do livro: ");
                    String nomeLivro = sc.nextLine();
                    System.out.println("Digite o nome do autor: ");
                    String nomeAutor = sc.nextLine();
                    Livro novoLivro = new Livro(nomeLivro, nomeAutor);
                    meuBanco.salvar(novoLivro);
                    System.out.println("br.com.biblioteca.model.Livro cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("livros cadastrados: ");
                    info.listarLivros();
                    break;
                case 3:
                    System.out.println("Digite o nome do livro que gostaria de verificar: ");
                    String livro = sc.nextLine();
                    info.procurarLivroPorTitulo(livro);
                    if(livro != null){
                        System.out.println("br.com.biblioteca.model.Livro encontrado: " + livro);
                    }else {
                        System.out.println("O livro não se encontra nessa biblioteca.");
                    }
                    break;
                case 4: sc.nextLine();
                    System.out.println("Digite o livro que deseja pegar emprestado? ");
                    String tituloEmprestimo = sc.nextLine();
                    tituloEmprestimo = info.procurarLivroPorTitulo(tituloEmprestimo);
                    System.out.println("Digite a id do livro: ");
                    int idLivro = sc.nextInt();
                    System.out.println("Digite a id usuário: ");
                    int idUsuario = sc.nextInt();
                    info.emprestarLivro(idLivro, idUsuario);
                    break;
                case 5:sc.nextLine();
                    System.out.println("Digite sua id: ");
                    int idUsuarioDevolucao = sc.nextInt();

                    sc.nextLine();

                    System.out.println("Digite o livro que quer devolver");
                    String tituloDevolucao = sc.nextLine();
                    tituloDevolucao = info.procurarLivroPorTitulo(tituloDevolucao);
                    System.out.println("Digite o Id do livro: ");
                    int idDevolucao = sc.nextInt();
                    info.devolverLivro(idDevolucao, idUsuarioDevolucao);
                    break;
                case 6:
                    continuarMenu = false;
            }
        } while (continuarMenu);

    }
    public void menuPagamento(){
        boolean continuarPagamento = true;
        do {
            System.out.println("Digite a opção desejada");
            System.out.println("[1] - Verificar saldo");
            System.out.println("[2] - Realizar pagamento");
            System.out.println("[3] - Verificar custos");
            System.out.println("[4] - sair");
            int id;

            int opcaoPagamento = sc.nextInt();


            switch (opcaoPagamento){
                case 1:
                    System.out.println("Digite seu id: ");
                    id = sc.nextInt();
                    info.verificarSaldo(id);
                    break;
                case 2:
                    System.out.println("Digite seu id: ");
                    id = sc.nextInt();
                    info.realizarPagamento(id);
                    break;
                case 3:
                    Pagamento custoFixo = new Pagamento();
                    System.out.println("O custo de cada empréstimo é de: " +  custoFixo.getCustoFixo());
                    System.out.println("Para verificar seu saldo, solicite a opção 1 do menu pagamentos.");
                    break;
                case 4:
                    continuarPagamento = false;
            }
        }while (continuarPagamento);
    }
}


