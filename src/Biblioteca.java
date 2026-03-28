import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> acervo;
    private List<Usuario> usuarios;

    public Biblioteca(){
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void adicionarUsuario(Usuario novoUsuario){
        for (Usuario l: usuarios){
            if (novoUsuario.getId() == l.getId()){
                System.out.println("Esse Id ja existe, digite outro");
            }
        }

    }


    public void adicionarLivro(Livro livro){
        acervo.add(livro);
    }

   public void devolverLivro(String titulo){
        Livro livroEncontrado = null;
        for (Livro l : acervo){
            if (l.getTitulo().equalsIgnoreCase(titulo)){
                livroEncontrado = l;
                break;
            }
        }

        if (livroEncontrado != null){
            livroEncontrado.setDisponivel(true);
            System.out.println("Livro devolvido!");
        } else {
            System.out.println("Livro não encontrado!");
        }
   }




    public void emprestarLivro(String tituloLivro, int idUsuario){
        Livro livroEncontrado = null;
        for (Livro l : acervo){
            if (l.getTitulo().equalsIgnoreCase(tituloLivro) && l.isDisponivel()){
                livroEncontrado = l;
                break;
            }
        }

        Usuario usuarioEncontrado = null;
        for (Usuario u : usuarios){
            if (u.getId() == idUsuario) {
                usuarioEncontrado = u;
                break;
            }
        }

        if (livroEncontrado != null && usuarioEncontrado != null){
            livroEncontrado.setDisponivel(false);
            usuarioEncontrado.getlivroEmprestado().add(livroEncontrado);
            System.out.println("Empréstimo realizado!");
        } else {
            System.out.println("Livro indisponivel ou usuário não encontrado");
        }
    }



    }

