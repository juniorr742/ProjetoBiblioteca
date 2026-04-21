/*package br.com.biblioteca.dao;
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

    public void salvar(Usuario usuario){
        String sql = "INSERT INTO usuarios (nome, tipo, saldo_devedor) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, usuario.getNome());

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

   /* public Usuario buscarporId(int id){
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
}*/
