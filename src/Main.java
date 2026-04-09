import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuarRodar = true;
        Biblioteca minhaBiblioteca = new Biblioteca();
        menuGeral menu = new menuGeral(minhaBiblioteca);


        do {
            System.out.println("Bem-vindo ao Menu Biblioteca!!!");
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
            }
        } while (continuarRodar);
    }
}









