package br.com.biblioteca.controller;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.RegistroEmprestimo;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.service.EmprestimoService;
import br.com.biblioteca.service.PagamentoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class Biblioteca {
    private List<Livro> acervo;
    private List<Usuario> usuarios;
    private List<RegistroEmprestimo> historicoEmprestimos;
    private PagamentoService pagamentoService;
    private EmprestimoService emprestimoService;


    public Biblioteca(PagamentoService pagamentoService, EmprestimoService emprestimoService){
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
        this.pagamentoService = pagamentoService;
        this.emprestimoService = emprestimoService;
    }

    public Usuario buscarUsuariosPorId(int id){
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public Livro buscarLivroPorId(int id){
        return acervo.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }


    public void adicionarUsuario(Usuario novoUsuario){
        usuarios.add(novoUsuario);
    }

    public void adicionarLivro(Livro livro){
        acervo.add(livro);
    }

    public List<Usuario> listarTodosUsuarios(){
        return Collections.unmodifiableList(usuarios);
    }

    public List<Livro> listarTodosLivros(){
        return Collections.unmodifiableList(acervo);
    }

     public void listarHistorico(){
         System.out.println("\n=====HISTÓRICO DOS REGISTROS=====");
         historicoEmprestimos.forEach(System.out::println);
     }

    public List<Livro> procurarLivroPorTitulo(String termoBusca){
        if (termoBusca == null || termoBusca.isBlank()) {
            return new ArrayList<>();
        }

        String termoTradado = termoBusca.toLowerCase().trim();

        return acervo.stream().filter(l -> l.getTitulo().toLowerCase().contains(termoTradado)).toList();
    }

    public List<Usuario> procurarUsuarioPorNome(String termoBusca){
        if (termoBusca == null || termoBusca.isBlank()){
            return new ArrayList<>();
        }

        String termoTratado = termoBusca.toLowerCase().trim();

        return usuarios.stream().filter(u -> u.getNome().toLowerCase().contains(termoTratado)).toList();
    }

   public void devolverLivro(int idLivro, int idUsuario, int diasCorridos) {
       Usuario usuario = buscarUsuariosPorId(idUsuario);
       Livro livro = buscarLivroPorId(idLivro);
       if (usuario != null && livro != null) {
           emprestimoService.realizarDevolucao(usuario, livro, diasCorridos);
           historicoEmprestimos.stream().filter(h -> h.getIdLivro() == idLivro && h.getIdUsuario() == idUsuario && !h.isFinalizado())
                   .findFirst().ifPresent(RegistroEmprestimo::finalizarEmprestimo);
       }

   }

    public void emprestarLivro(int idLivro, int idUsuario) {
        Livro livroEncontrado = buscarLivroPorId(idLivro);
        Usuario usuarioEncontrado = buscarUsuariosPorId(idUsuario);
        if (livroEncontrado == null && usuarioEncontrado == null) {
            System.out.println("ERRO: livro ou usuário não encontrado");
        }
        emprestimoService.emprestarLivro(usuarioEncontrado, livroEncontrado);
            if (!livroEncontrado.isDisponivel()){
                RegistroEmprestimo novoRegistro = new RegistroEmprestimo(idUsuario, idLivro);
                historicoEmprestimos.add(novoRegistro);
                System.out.println("Hisórico gerado: Transação #"+ novoRegistro.getIdTransacao());
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
            pagamentoService.processarPagamentoTotal(usuarioEcontrado);
        } else {
            System.out.println("Erro: Usuário: " + id + "não encontrado.");
        }
    }
}

