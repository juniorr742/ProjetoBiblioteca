import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class GerencidorDeArquivos {
    private static final String CAMINHO_LIVROS = "livros.txt";


public static void salvarLivros(List<Livro> listaDeLivros){
    try (PrintWriter escritor = new PrintWriter(new FileWriter(CAMINHO_LIVROS, false))){
        for (Livro l: listaDeLivros){
            escritor.println(l.getId() + ";" + l.getTitulo() + ";" + l.isDisponivel());
        }
        System.out.println("Arquivo de livros atualizado!");
    }catch (IOException e){
        System.err.println("Erro ao gravar no disco: " + e.getMessage());
    }
}

 public static List<Livro> carregarLivros(){
    List<Livro> listaCarregada = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("livros.txt"))){
        String linha;
        while ((linha = reader.readLine()) != null){
            String[] dados = linha.split(";");
            int id = Integer.parseInt(dados[0]);
            String titulo = dados[1];
            boolean disponivel = Boolean.parseBoolean(dados[2]);
            Livro novoLivro = new Livro(titulo, "Autor Desconhecido");
            novoLivro.setId(id);
            novoLivro.setDisponivel(disponivel);
            listaCarregada.add(novoLivro);
        }
        System.out.println("Livros carregados com sucesso");
    } catch (IOException e) {
        System.out.println("Nenhum dado prévio encontrado. Começando do zero.");
    }
    return listaCarregada;
 }

}


