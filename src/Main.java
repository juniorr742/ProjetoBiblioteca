 class Main {

    public static void main(String[] args ) {


        Usuario Alun1 = new Aluno("João", 1);
        Usuario prof1 = new Professor("Carlos Miguel", 9);

        Livro livro1 = new Livro("Dom Casmurro", "Machado de assis");

        System.out.println("Testando aluno = ");
        Alun1.pegarLivro(livro1);
        System.out.println("Livros do aluno: " + Alun1.getlivroEmprestado().size());

        System.out.println("Testando Professor = ");
        prof1.pegarLivro(livro1);
        System.out.println("Livros do Professor: " + prof1.getlivroEmprestado().size());
    }
}

