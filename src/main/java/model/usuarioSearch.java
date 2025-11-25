package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class usuarioSearch {
    
    public String accesoUsuarioYRol(String user, char[] passw) {
        String pass = new String(passw);
        
        conectDb db = new conectDb();
        String rolUsuario = null;

        rolUsuario = verificarCredencialesYRol(db, "usuarios", "nombre_usuario", user, pass);
        java.util.Arrays.fill(passw, '0');
        
        if (rolUsuario == null) {
            JOptionPane.showMessageDialog(null,
                "Usuario no encontrado o credenciales incorrectas",
                "Error de autenticación",
                JOptionPane.WARNING_MESSAGE);
        }
        
        return rolUsuario;
    }

    private String verificarCredencialesYRol(conectDb db, String tabla, String columnaUsuario, String user, String pass) {
        // Aqui se selecciona la columna "rol"
        String sql = "SELECT rol FROM " + tabla + " WHERE " + columnaUsuario + " = ? AND contrasena = ?";
        
        try (Connection cn = db.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {
                
            pst.setString(1, user);
            pst.setString(2, pass);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol"); 
                }
                return null;
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al verificar credenciales en base de datos. Consulta los detalles de la consola.",
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
    
    // NOTA: El método original public boolean accesoUsuario... ya no se usa, 
    // pero si lo necesitas, puedes dejarlo o eliminarlo. La nueva lógica está en accesoUsuarioYRol.
}