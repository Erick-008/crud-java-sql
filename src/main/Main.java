package main;

import service.UsuarioService;
import dao.UsuarioDao;
import model.UsuarioModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main { 
    
    public static void main(String[] args) {
        try {
            // Configuração da conexão com o banco de dados
            String url = "jdbc:mysql://localhost:3306/crud_sql";
            String usuario = "root";
            String senha = "2008";
            
            Connection connection = DriverManager.getConnection(url, usuario, senha);
            
            // Criar DAO e Service
            UsuarioDao usuarioDao = new UsuarioDao(connection);
            UsuarioService usuarioService = new UsuarioService(usuarioDao);
            
            // Criar um usuário
            UsuarioModel novoUsuario = new UsuarioModel();
            novoUsuario.setNome("Erick");
            novoUsuario.setEmail("erick@example.com");
            novoUsuario.setSenha("senha123");
            
            usuarioService.criarUsuario(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
            
            connection.close();
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        }
    }
}
