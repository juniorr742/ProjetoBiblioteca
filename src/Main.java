 class Main {

    public static void main(String[] args ) {


        Usuario Alun1 = new Aluno("João", 1);
        Usuario prof1 = new Professor("Carlos Miguel", 9);

        Livro livro1 = new Livro("Dom Casmurro", "Machado de assis");

        Alun1.pegarLivro(livro1);

        System.out.println(Alun1.getSaldo().getSaldoDevedor());
        Alun1.devolverLivro(livro1, 10);
        System.out.println(Alun1.getSaldo().getSaldoDevedor());

    }
}

