/*package br.com.biblioteca.dao;

import br.com.biblioteca.model.Livro;

import java.sql.*;
import java.util.ArrayList;

public class LivroDAO {
      private String url = "jdbc:mysql://localhost:3306/biblioteca_db";
      private String user = "root";
      private String password = "admin123";

      public void salvar(Livro livro){
          String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)"; //O COMANDO

          try (Connection conn = DriverManager.getConnection(url, user, password);
          PreparedStatement stmt = conn.prepareStatement(sql)) {                    //A CONEXAO

              stmt.setString(1, livro.getTitulo());
              stmt.setString(2, livro.getAutor());     //Preenchimento
              stmt.setBoolean(3, livro.isDisponivel());
              stmt.executeUpdate();

              System.out.println("----SUCESSO-------");
              System.out.println("O livro: " + livro.getTitulo() + " foi guardado no MySQL");
          } catch (SQLException e){
              System.out.println("Erro ao salvar no banco de dados: " + e.getMessage());
          }

      }

      public java.util.List<Livro> listarTodos(){
          java.util.List<Livro> lista = new ArrayList<>();
          String sql = "SELECT * FROM livros";

          try (Connection conn = DriverManager.getConnection(url, user, password);
          PreparedStatement stmt =  conn.prepareStatement(sql);
          java.sql.ResultSet rs = stmt.executeQuery()){
              while (rs.next()){
                  Livro l = new Livro(rs.getString("titulo"), rs.getString("autor"));
                  lista.add(l);
              }
          }catch (SQLException e){
              System.err.println("Erro ao listar: " + e.getMessage());
          }
          return lista;
      }

      public Livro buscarPorId(int id){
          String sql = "SELECT * FROM livros WHERE id = ?";
          try (Connection conn = DriverManager.getConnection(url, user, password);
          PreparedStatement stmt = conn.prepareStatement(sql)){
              stmt.setInt(1, id);
              ResultSet rs = stmt.executeQuery();
              if (rs.next()){
                  Livro l = new Livro(rs.getString("titulo"), rs.getString("autor"));
                  l.setId(rs.getInt("id"));
                  l.setDisponivel(rs.getBoolean("disponivel"));
                  return l;
              }
          }catch (SQLException e){
              e.printStackTrace();
          }
          return null;
      }
}
