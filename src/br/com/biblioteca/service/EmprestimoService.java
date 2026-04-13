package br.com.biblioteca.service;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.List;

public class EmprestimoService {

    public void emprestarLivro(Usuario usuario, Livro livro){
        boolean jaPossuiEsseExemplar = usuario.getlivroEmprestado().stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (jaPossuiEsseExemplar){
            System.out.println("O usuário já está em posse desse livro!");
            return;
        }

        if (usuario.getSaldo().getSaldoDevedor() > usuario.getLimiteSaldo() || usuario.getlivroEmprestado().size() > usuario.getLimiteLivros()){
            System.out.println("Limite atingido , por favor realize o pagamento!");
            return;
        }
            usuario.getSaldo().registrarEmprestimo();
            usuario.getlivroEmprestado().add(livro);
            System.out.println("Livro" + livro.getTitulo() + "emprestado com sucesso!");
    }

    public void realizarDevolucao(Usuario usuario, Livro livro, int diasCorridos){
        boolean jaPossuiEsseExemplar = usuario.getlivroEmprestado().stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (!jaPossuiEsseExemplar){
            System.out.println("Livro não encontrado com esse usuário!");
            return;
        }

        int prazo = 7;

        if (diasCorridos > prazo){
            double valorMulta = (diasCorridos - prazo) * 2;
            usuario.getSaldo().registrarMulta(valorMulta);
            System.out.println("Multa de R$:" + valorMulta + "aplicada por atraso.");
            return;
        }

        usuario.getlivroEmprestado().remove(livro);
        System.out.println("Livro devolvido no prazo!");
    }

    public List<Livro> livrosAtivosDoUsuario(Usuario usuario){
        return usuario.getlivroEmprestado();// fazer verificação o ExceptionPointer!!!!
    }
}
