package br.com.biblioteca.service;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.List;

public class EmprestimoService {
    private ValidadorEmprestimo validador;
    private CalculadoraMulta calculadora;
    private PagamentoService pagamento;

    public EmprestimoService(ValidadorEmprestimo validador, CalculadoraMulta calculadora, PagamentoService pagamento){
        this.validador = validador;
        this.calculadora = calculadora;
        this.pagamento = pagamento;
    }


    public void emprestarLivro(Usuario usuario, Livro livro){
        if (!validador.podeEmprestar(usuario,livro)){
            System.out.println("Empréstimo cancelado, erro de validação");
        }else {
            pagamento.aplicarTaxaEmprestimo(usuario);
            usuario.adicionarLivro(livro);
            livro.setDisponivel(false);
            System.out.println("Livro" + livro.getTitulo() + " emprestado com sucesso!");
        }
    }

    public void realizarDevolucao(Usuario usuario, Livro livro, int diasCorridos){
        boolean jaPossuiEsseExemplar = usuario.getlivroEmprestado().stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (!jaPossuiEsseExemplar){
            System.out.println("Livro não encontrado com esse usuário!");
            return;
        }

        double valorMulta = calculadora.valorCalculado(diasCorridos);
        if (valorMulta > 0){
            pagamento.aplicarMultaAtraso(usuario, valorMulta);
            System.out.println("Livro devolvido com sucesso!");
        }else {
            System.out.println("Livro devolvido no prazo!");
        }
        usuario.removerLivro(livro);
        livro.setDisponivel(true);
        System.out.println("O livro "+ livro.getTitulo() + " foi devolvido com sucesso!");
    }

    public List<Livro> livrosAtivosDoUsuario(Usuario usuario){
        return usuario.getlivroEmprestado();// fazer verificação o ExceptionPointer!!!!
    }
}
