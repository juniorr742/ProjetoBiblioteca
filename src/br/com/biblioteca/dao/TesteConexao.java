package br.com.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/biblioteca_db";
        String usuario = "root";
        String senha = "admin123";

        System.out.println("Iniciando conexão...");

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)){
            System.out.println("\n----------------");
            System.out.println("Sucesso!");
        }catch (SQLException e){
            System.err.println("\nAlgo deu errado");
            if (e.getMessage().contains("Acess denied")){
                System.err.println("Senha de root incorreta!");
            }else if (e.getMessage().contains("Communications link failure")){
                System.err.println("O MySQL está desligado ou  endereço está errado");
            }else {
                e.printStackTrace();
            }
        }


    }
}
