package controller;

import service.UsuarioService;
import dao.UsuarioDao;
import model.UsuarioModel;
import config.UsuarioConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class usuarioController {
    
    private UsuarioService usuarioService;
    private Connection connection;
    
    public usuarioController() throws SQLException {
        // Obter conexão do config
        this.connection = UsuarioConfig.getConnection();
        
        // Criar DAO e Service
        UsuarioDao usuarioDao = new UsuarioDao(this.connection);
        this.usuarioService = new UsuarioService(usuarioDao);
    }
    
    public void executar() {
        try {
            // Criar um usuário
            UsuarioModel novoUsuario = new UsuarioModel();
            novoUsuario.setNome("pedro");
            novoUsuario.setEmail("pedro@email.com");
            novoUsuario.setSenha("sV09mda");
            
            usuarioService.criarUsuario(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } finally {
            fecharConexao();
        }
    }
    
    private void fecharConexao() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
