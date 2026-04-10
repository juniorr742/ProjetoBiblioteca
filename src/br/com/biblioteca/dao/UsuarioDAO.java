package br.com.biblioteca.dao;
import br.com.biblioteca.model.Aluno;
import br.com.biblioteca.model.Professor;
import br.com.biblioteca.model.Usuario;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class UsuarioDAO {
    private String url = "jdbc:mysql://localhost:3306/biblioteca_db";
    private String user =  "root";
    private String password = "admin123";

//todo: na classe de acessso a banco de dados, seria interessante manter apenas os métodos relacionados ao acesso a dados, como salvar, buscar por id, atualizar, deletar, etc.
// e as regras de negócio relacionadas a usuario, como pegarLivro(), devolverLivro(), etc, poderiam ser movidas para a classe UsuarioService, assim o código fica mais organizado e fácil de manter.
    public void salvar(Usuario usuario){
        String sql = "INSERT INTO usuarios (nome, tipo, saldo_devedor) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, usuario.getNome());

            //todo: aqui por exemplo, existe regra pra entender o tipo do usuario, e isso é uma regra de negócio, 
            // então o ideal seria ter um método na classe UsuarioService que retornasse o tipo do usuário como string, 
            // e aqui no DAO só chamaria esse método para setar o tipo no banco. Assim, o código fica mais organizado e fácil de manter.
            if (usuario instanceof Aluno){
                stmt.setString(2, "Aluno");
            }else {
                stmt.setString(2, "Professor");
            }
            stmt.setDouble(3, usuario.getSaldo().getSaldoDevedor());
            stmt.executeUpdate();


            System.out.println("Sucesso!! " + usuario.getNome() + " cadastrado com sucesso no banco");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Usuario buscarporId(int id){
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                double saldoNoBanco = rs.getDouble("saldo_devedor");

                Usuario u = tipo.equalsIgnoreCase("ALUNO") ? new Aluno(nome) : new Professor(nome);
                u.setId(rs.getInt("id"));
                u.getSaldo().setSaldoDevedor(saldoNoBanco);
                return u;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
