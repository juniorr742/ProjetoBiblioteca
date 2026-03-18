class main {
    public static void main(String[] args) {

        Biblioteca minhaBiblioteca = new Biblioteca();

        Livro livro1 =new Livro("O senhor dos Áneis", "CHICO");
        Livro livro2 = new Livro("Dom Casmurro", "Machado de Assis");
        Livro livro3 = new Livro("Uma história de futebol", "Pelé");

        System.out.println("----Testando Cadastro----");
        minhaBiblioteca.adicionarLivro(livro1);
        minhaBiblioteca.adicionarLivro(livro2);
        minhaBiblioteca.adicionarLivro(livro3);

        minhaBiblioteca.listarAcervo();
    }
}