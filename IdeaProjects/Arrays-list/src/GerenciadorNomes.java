import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorNomes {

ArrayList<String> nomes = new ArrayList<>();


public void cadatrarUsuario() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Informe o nome do usuario");
    String nome = sc.next();

    nomes.add(nome);

    System.out.println("Cadastrado com sucesso: ");

}

public void listar(){
    for (String nome: nomes){
        System.out.println(nome);
    }
}



}


