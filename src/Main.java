 class Main {

    public static void main(String[] args ) {
        Livro livro1 = new Livro("Dom Casmurro", "Machado de assis");
        System.out.println(livro1.getTitulo());

        Usuario Usuario1 = new Usuario("João", 1);
        Biblioteca minhaBiblioteca = new Biblioteca();

        minhaBiblioteca.adicionarLivro(livro1);
        minhaBiblioteca.adicionarUsuario(Usuario1);

        minhaBiblioteca.emprestarLivro("Dom Casmurro", 1);

        System.out.println("Disponível: " + livro1.isDisponivel());

        minhaBiblioteca.devolverLivro("Dom Casmurro");

        System.out.println("Disponivel: " + livro1.isDisponivel());

     }
}

