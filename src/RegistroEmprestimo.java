import java.time.LocalDate;

public class RegistroEmprestimo {
    private static int contadorId = 1;
    int idTransacao;
    private int idUsuario;
    private int idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean finalizado;

    public RegistroEmprestimo(int idUsuario, int idLivro){
        this.idTransacao = contadorId++;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = LocalDate.now();
        this.finalizado = false;
    }

    public void finalizarEmprestimo(){
        this.dataDevolucao = LocalDate.now();
        this.finalizado = true;
    }

    @Override
    public String toString(){
        return String.format("Transação: %d | Usuário ID: %d | Livro ID: %d | Data: %s | Status: %s",
                idTransacao, idUsuario, idLivro, dataEmprestimo, (finalizado ? "Devolvido":"Ativo"));
    }




}
