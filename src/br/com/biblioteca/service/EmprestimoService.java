package br.com.biblioteca.service;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.List;

public class EmprestimoService {
    private ValidadorEmprestimo validador;
    private CalcularMulta calcMulta;

    public EmprestimoService(){
        this.validador = new ValidadorEmprestimo();
        this.calcMulta = new CalcularMulta();
    }


    public void emprestarLivro(Usuario usuario, Livro livro){
        if (!validador.podeEmprestar(usuario,livro)){
            System.out.println("Empréstimo cancelado, erro de validação");
        }else {
            usuario.getSaldo().registrarEmprestimo();
            usuario.adicionarLivro(livro);
            System.out.println("Livro" + livro.getTitulo() + "emprestado com sucesso!");
        }
    }

    public void realizarDevolucao(Usuario usuario, Livro livro, int diasCorridos){
        boolean jaPossuiEsseExemplar = usuario.getlivroEmprestado().stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (!jaPossuiEsseExemplar){
            System.out.println("Livro não encontrado com esse usuário!");
            return;
        }

        double valorMulta = calcMulta.valorCalculado(diasCorridos);
        if (valorMulta > 0){
            System.out.println("Multa de R$:"+valorMulta);
            usuario.getSaldo().registrarMulta(valorMulta);
        }else {
            System.out.println("Livro devolvido no prazo!");
        }
        usuario.removerLivro(livro);
        System.out.println("O livro "+ livro.getTitulo() + " foi devolvido com sucesso!");
    }

    public List<Livro> livrosAtivosDoUsuario(Usuario usuario){
        return usuario.getlivroEmprestado();// fazer verificação o ExceptionPointer!!!!
    }
}
