import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> acervo;
    private List<Usuario> usuarios;


    public Biblioteca(){
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public Usuario buscarPorId(int IdProcurado){
        for (Usuario u: usuarios){
            if (u.getId() == IdProcurado){
                return u;
            }
        }
        try {
            return null;
        } catch (Exception e){
            return null; // vou verificar melhor forma de fazer esse try.
        }

    }


    public void adicionarUsuario(Usuario novoUsuario){
        usuarios.add(novoUsuario);
    }

    public void listarUsuarios(){

       if (usuarios.isEmpty()){
           System.out.println("Lista de usuários vazia!!!");
       }else {
           for (Usuario l: usuarios){
                 System.out.println(l);
           }
       }
    }

    public void listarLivros(){

        if (acervo.isEmpty()){
            System.out.println("Lista de Livros vazia!!!");
        }else {
            for (Livro l: acervo){
                System.out.println(l);
            }
        }
    }

    public String procurarLivro(String livroProcurar){
        if (acervo.isEmpty()) {
            return "Lista de livros esta vazia";
        }

            for (Livro l : acervo) {
             if (livroProcurar != null && livroProcurar.equalsIgnoreCase(l.getTitulo())) {
                System.out.println("Esse livro está cadastrado!");
                return l.getTitulo();
            }
        }
            return "Livro não cadastrado, por favor, realize o cadastro primeiro.";
    }



    public void adicionarLivro(Livro livro){
        acervo.add(livro);
    }

   public void devolverLivro(String titulo, int idUsuario) {
       Livro livroEncontrado = null;
       for (Livro l : acervo) {
           if (l.getTitulo().equalsIgnoreCase(titulo)) {
               livroEncontrado = l;
           }
       }

       Usuario usuarioEncontrado = null;
       for (Usuario u : usuarios) {
           if (u.getId() == idUsuario) {
               usuarioEncontrado = u;
           }

           if (livroEncontrado != null && usuarioEncontrado != null) {
               livroEncontrado.setDisponivel(true);
               usuarioEncontrado.getlivroEmprestado().remove(livroEncontrado);
               System.out.println("Livro devolvido!");
           } else {
               System.out.println("Livro não encontrado!");
           }
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
            if (usuarioEncontrado.getSaldo().saldoDevedor < usuarioEncontrado.getLimiteSaldo()){

            livroEncontrado.setDisponivel(false);
            usuarioEncontrado.getlivroEmprestado().add(livroEncontrado);
            usuarioEncontrado.getSaldo().registrarEmprestimo();
            } else {
                System.out.println("limite atingido!");
            }
        } else {
            System.out.println("Livro indisponivel ou usuário não encontrado");
        }
    }

    public void verificarSaldo(int idPagamento){
        Usuario usuarioEcontrado = null;
        for (Usuario u: usuarios){
            if (u.getId() == idPagamento){
                System.out.println("Seu saldo devedor é: " + u.getSaldo().getSaldoDevedor());
            } else {
                System.out.println("Id não identificado");
            }
        }
    }

    public void realizarPagamento(int id){
        Usuario usuarioEcontrado;
        for (Usuario u: usuarios){
            if (u.getId() == id){
                u.getSaldo().quitarDivida();
            }
        }
    }



    }

