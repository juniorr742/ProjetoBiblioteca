import java.util.Scanner;

public class menuGeral {
    Scanner sc = new Scanner(System.in);
    private Biblioteca info;


    public menuGeral(Biblioteca biblioteca) {
        this.info = biblioteca;
    }

    public void menuUsuario() {

        boolean continuarUsuario = true;


        do {
            System.out.println("Digite sua opção no menu usuário: ");
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
                        System.out.println("Digite seu nome e seu id: ");
                        String nomeAluno = sc.nextLine();
                        int idNova = sc.nextInt();
                        Usuario alunoNovo = new Aluno(nomeAluno, idNova);
                        info.adicionarUsuario(alunoNovo);
                        System.out.println("Aluno cadastrado com sucesso");
                    } else {
                        System.out.println("Digite seu nome e seu id: ");
                        String nomeProfessor = sc.nextLine();
                        int idNova = sc.nextInt();
                        Usuario professorNovo = new Professor(nomeProfessor, idNova);
                        info.adicionarUsuario(professorNovo);
                        System.out.println("Professor cadastrado com sucesso");
                    }
                    break;
                case 2:
                    System.out.println("Digite sua Id: ");
                    int idProcura = sc.nextInt();

                    Usuario usuarioLogado = info.buscarPorId(idProcura);

                    if (usuarioLogado != null) {
                        System.out.println("Saldo: " + usuarioLogado.getSaldo());
                        System.out.println("Nome: " + usuarioLogado.getNome());
                        System.out.println("Livros em sua posse: " + usuarioLogado.getlivroEmprestado());
                        System.out.println("Limites de livros: " + usuarioLogado.getLimiteSaldo());
                    } else {
                        System.out.println("Erro: Usuário inexistente!");
                    }
                    break;
                case 3:
                    continuarUsuario = false;
                    break;
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
            System.out.println("[4] - Devolver livro");
            System.out.println("[5] - Pegar livro emprestado");
            System.out.println("[6] - Sair");
            int opcaoMenu = sc.nextInt();

            switch (opcaoMenu){
                case 1:sc.nextLine();
                    System.out.println("Digite o nome do livro: ");
                    String nomeLivro = sc.nextLine();
                    System.out.println("Digite o nome do autor: ");
                    String nomeAutor = sc.nextLine();
                    Livro novoLivro = new Livro(nomeLivro, nomeAutor);
                    info.adicionarLivro(novoLivro);
                    System.out.println("Livro cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("livros cadastrados: ");
                    info.listarLivros();
                    break;
                case 3:
                    System.out.println("Digite o nome do livro que gostaria de verificar: ");
                    String livro = sc.nextLine();
                    info.procurarLivro(livro);
                    break;
                case 4: sc.nextLine();
                    System.out.println("Digite sua id: ");
                    int idUsuario = sc.nextInt();

                    sc.nextLine();

                    System.out.println("Digite o livro que quer devolver");
                    String tituloDevolucao = sc.nextLine();

                    info.devolverLivro(tituloDevolucao, idUsuario);
                    break;
                case 5: sc.nextLine();
                    System.out.println("Digite o livro que deseja pegar emprestado? ");
                    String tituloEmprestimo = sc.nextLine();
                    System.out.println("Digite sua id: ");
                    int idEmprestimo = sc.nextInt();
                    info.emprestarLivro(tituloEmprestimo, idEmprestimo);
                    break;
                case 6:
                    continuarMenu = false;
            }
        } while (continuarMenu);
    }
}


