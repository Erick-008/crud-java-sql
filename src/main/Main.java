package main;

import controller.usuarioController;
import java.sql.SQLException;

public class Main { 
    
    public static void main(String[] args) {
        try {
            usuarioController controller = new usuarioController();
            controller.executar();
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        }
    }
}
