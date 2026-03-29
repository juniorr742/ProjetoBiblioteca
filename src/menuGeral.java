import java.util.Scanner;

public class menuGeral {
    Scanner sc = new Scanner(System.in);
    private Biblioteca info;

    public menuGeral(Biblioteca biblioteca) {
        this.info = biblioteca;
    }

    public void menuUsuario() {
        System.out.println("Digite sua opção no menu usuário: ");
        System.out.println("[1] - Cadastrar usuário");
        System.out.println("[2] - Verificar status");
        System.out.println("");
        int menuUsuario = sc.nextInt();
        boolean continuarUsuario = true;


        do {
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
                        continuarUsuario = false;
                    } else {
                        System.out.println("Digite seu nome e seu id: ");
                        String nomeProfessor = sc.nextLine();
                        int idNova = sc.nextInt();
                        Usuario professorNovo = new Professor(nomeProfessor, idNova);
                        info.adicionarUsuario(professorNovo);
                        System.out.println("Professor cadastrado com sucesso");
                        continuarUsuario = false;
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
                        continuarUsuario = false;
                    } else {
                        System.out.println("Erro: Usuário inexistente!");
                    }
                    break;
            }

        } while (continuarUsuario);
    }
}


