import java.util.Scanner;

public class menuGeral {
    Scanner sc = new Scanner(System.in);

    public void menuUsuario() {
        System.out.println("Digite sua opção no menu usuário: ");
        System.out.println("[1] - Cadastrar usuário");
        System.out.println("");
        System.out.println("");
        int menuUsuario = sc.nextInt();
        boolean continuarUsuario = true;
        do {
            switch (menuUsuario) {
                case 1:
                    System.out.println("Você é aluno ou professor?");
                    String subUsuario = sc.nextLine();
                    if (subUsuario.equalsIgnoreCase("aluno")){
                        System.out.println("Digite seu nome e seu id: ");
                        String nomeAluno = sc.nextLine();
                        int idNova = sc.nextInt();
                        Usuario alunoNovo = new Aluno(nomeAluno, idNova);
                        System.out.println("Aluno cadastrado com sucesso");
                        continuarUsuario = false;
                    }
                    break;

            }

        }while(continuarUsuario);

    }
}
