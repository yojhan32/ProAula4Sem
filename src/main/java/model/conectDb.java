package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectDb {
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/loginbflow";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection conectar() {
        try {
            // Intento de conexión
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);; 
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error de conexión a la Base de Datos. " + e.getMessage(), 
                "ERROR CRÍTICO", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
    
    // Puedes dejar el main para pruebas rápidas
    public static void main(String[] args) {
        new conectDb().conectar();
    }
}