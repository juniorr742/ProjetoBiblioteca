public class Aluno extends Usuario{
    public Aluno(String nome){
        super(nome);
    }

    @Override
    public double getLimiteSaldo() {
        return 15;
    }

    @Override
    public int getLimiteLivros(){
        return 3;
    }
}
