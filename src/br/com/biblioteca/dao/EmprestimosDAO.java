/*package br.com.biblioteca.dao;
import java.sql.*;
import java.time.LocalDate;

public class EmprestimosDAO {
    private String url = "jdbc:mysql://localhost:3306/biblioteca_db";
    private String user = "root";
    private String password = "admin123";

    public void salvar(int idLivro, int idUsuario){
        String sql = "INSERT INTO emprestimos (id_Livro, id_Usuario, data_Emprestimo) VALUE (?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idLivro);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));

            System.out.println("Registro salvo com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}*/
