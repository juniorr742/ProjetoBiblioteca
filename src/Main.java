import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuarRodar = true;
        Usuario alun1 = new Aluno("Lucas", 1);
        Livro livro1 = new Livro("DOTA Pro", "Chicão pro");


        do {
            System.out.println("Bem-vindo ao Menu Biblioteca!!!");
            System.out.println("Digite a opção desejada: ");
            System.out.println("[1] - Usuário");
            System.out.println("[2] - Livros");
            System.out.println("[3] - Pagamentos e saldos");
            System.out.println("[4] - Sair");
            int opcaoUsuario = sc.nextInt();
            menuGeral menu = new menuGeral();

            switch (opcaoUsuario) {
                case 1: sc.nextLine();
                    menu.menuUsuario();
                    break;
                case 2:

            }
        } while (continuarRodar);
    }
}









