package br.com.biblioteca.controller;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.RegistroEmprestimo;
import br.com.biblioteca.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

//todo [1]: Uma Controller geralmente é usada para APIs, e tem uma responsabilidade bem definada que é apenas gerenciar as requisições e respostas, sem conter regras de negócio.
//  O ideal seria criar uma classe "BibliotecaService"  para conter as regras de negócio,
//  e a classe "BibliotecaController" para gerenciar as requisições e respostas (que nao é o caso dessa app console). Dessa forma, o código fica mais organizado e fácil de manter.
//
// sugestão: criar "serviços" separados para cada entidade, ex: UsuarioService, LivroService, etc. assim as responsabilidades ficam mais claras e o código mais organizado.
//  remover essa classe biblioteca e distribuir as responsabilidades para os serviços,
//  dessa forma o código fica mais organizado e fácil de manter.
public class Biblioteca {
    private List<Livro> acervo;
    private List<Usuario> usuarios;
    private List<RegistroEmprestimo> historicoEmprestimos;


    public Biblioteca(){
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
    }

    public Usuario buscarUsuariosPorId(int IdProcurado){
        for (Usuario u: usuarios){
            if (u.getId() == IdProcurado){
                return u;
            }
        }
            return null;
        }

    public Livro bucarLivroPorId(int id){
        for (Livro l: acervo){
            if (l.getId() == id){
                return l;
            }
        }
        return null;
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

    public String procurarLivroPorTitulo(String livroProcurar){
        if (livroProcurar == null || livroProcurar.isBlank()) return null;

        for (Livro l : acervo) {
            if (l.getTitulo().equalsIgnoreCase(livroProcurar.trim())) {
                    return "ID do livro: " + l.getId();
            }
        }
            return null;
    }



    public void adicionarLivro(Livro livro){
        acervo.add(livro);
    }

   public void devolverLivro(int idLivro, int idUsuario) {
       Livro livroEncontrado = bucarLivroPorId(idLivro);
       Usuario usuarioEncontrado = buscarUsuariosPorId(idUsuario);
           if (livroEncontrado != null && usuarioEncontrado != null) {
               long diasCorridos = 0;

               for(RegistroEmprestimo re: historicoEmprestimos){
                   if (re.getIdLivro() == idLivro && re.getIdUsuario() == idUsuario && !re.isFinalizado()){
                       diasCorridos = ChronoUnit.DAYS.between(re.getDataEmprestimo(), LocalDate.now());
                       re.finalizarEmprestimo();
                       System.out.println("Transação de histórico " + re.getIdTransacao() + " encerrada.");
                       break;
                   }
               }
               usuarioEncontrado.devolverLivro(livroEncontrado, (int) diasCorridos);
               livroEncontrado.setDisponivel(true);
               System.out.println("br.com.biblioteca.model.Livro devolvido!");
           } else {
               System.out.println("br.com.biblioteca.model.Livro não encontrado");
           }

       }

    public void emprestarLivro(int idLivro, int idUsuario) {
        Livro livroEncontrado = bucarLivroPorId(idLivro);
        Usuario usuarioEncontrado = buscarUsuariosPorId(idUsuario);
        if (livroEncontrado != null && usuarioEncontrado != null) {
            if (!livroEncontrado.isDisponivel()){
                System.out.println("br.com.biblioteca.model.Livro ja está emprestado!");
            }else if(usuarioEncontrado.getSaldo().getSaldoDevedor() >= usuarioEncontrado.getLimiteSaldo()){
                System.out.println("Limite atingido, realize o pagamento!");
            }else {
                livroEncontrado.setDisponivel(false);
                usuarioEncontrado.getlivroEmprestado().add(livroEncontrado);
                usuarioEncontrado.getSaldo().registrarEmprestimo();
                RegistroEmprestimo novoRegistro = new RegistroEmprestimo(idUsuario,idLivro);
                historicoEmprestimos.add(novoRegistro);
                System.out.println("Registro de transação gerado com sucesso: ID " + novoRegistro.getIdTransacao());
            }
        }else {
            System.out.println("br.com.biblioteca.model.Livro ou usuário não encontrado!");
        }
    }

    public void verificarSaldo(int idPagamento){
        Usuario usuarioEcontrado = buscarUsuariosPorId(idPagamento);

            if (usuarioEcontrado != null){
                System.out.println("Seu saldo devedor é: " + usuarioEcontrado.getSaldo().getSaldoDevedor());
            } else {
                System.out.println("Id não identificado");
            }
        }

    public void realizarPagamento(int id){
        Usuario usuarioEcontrado = buscarUsuariosPorId(id);
        if (usuarioEcontrado != null){
            usuarioEcontrado.getSaldo().quitarDivida();
        } else {
            System.out.println("Erro: Usuário: " + id + "não encontrado.");
        }
    }

    public List<Livro> getAcervo() {
        return acervo;
    }

    public void setAcervo(List<Livro> acervo) {
        this.acervo = acervo;
    }
}

