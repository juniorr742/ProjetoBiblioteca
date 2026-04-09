public class  Professor extends Usuario {
    public Professor(String nome, int id){
        super(nome, id);
    }

    @Override
    public double getLimiteSaldo(){
        return 100;
    }
    @Override
    public int getLimiteLivros(){return 10;}

}
