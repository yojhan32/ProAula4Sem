package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class usuarioSearch {

    public boolean accesoUsuario(String user, char[] passw) {
        String pass = new String(passw); 
        
        conectDb db = new conectDb();
        boolean accesoCorrecto = false;
        
        accesoCorrecto = verificarCredenciales(db, "usuarios", "nombre_usuario", user, pass); 
        java.util.Arrays.fill(passw, '0'); 
        
        if (!accesoCorrecto) {
            JOptionPane.showMessageDialog(null, 
                "Usuario no encontrado o credenciales incorrectas", 
                "Error de autenticación", 
                JOptionPane.WARNING_MESSAGE);
        }
        
        return accesoCorrecto;
    }

    private boolean verificarCredenciales(conectDb db, String tabla, String columnaUsuario, String user, String pass) {
        String sql = "SELECT 1 FROM " + tabla + " WHERE " + columnaUsuario + " = ? AND contrasena = ?";
        
        try (Connection cn = db.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            
            pst.setString(1, user);
            pst.setString(2, pass);
            
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Retorna true si encuentra al menos una fila
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al verificar credenciales en base de datos. Consulta los detalles de la consola.", 
                "Error de base de datos", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}