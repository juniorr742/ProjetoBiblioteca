import  java.util.Scanner;


public class Arrays01 {
    public static void main(String[] args) {

        boolean conttinuar = true;
        int opcaoUsuario;
        Scanner sc = new Scanner(System.in);
        GerenciadorNomes grc = new GerenciadorNomes();

        do {
            System.out.println("digite sua opção: ");
            System.out.println("1: para cadastrar");
            System.out.println("2: para listar os usuários");
            System.out.println("0: para sair");

            opcaoUsuario = sc.nextInt();

            switch (opcaoUsuario){
                case 1: grc.cadatrarUsuario();
                break;
                case 2: grc.listar();
                break;
                case 0: conttinuar = false;
            }

        } while (conttinuar);





    sc.close();

    }
}
