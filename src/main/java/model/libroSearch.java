package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class libroSearch {

    /**
     * Verifica si un libro con el título y año de publicación especificados existe en la base de datos.
     */
    public boolean buscarLibro(String titulo, int año_publicacion) {
        
        conectDb db = new conectDb();
        boolean libroEncontrado = false;
        String tabla = "librosdispo";
        
        libroEncontrado = verificarExistencia(db, tabla, titulo, año_publicacion);
        
        if (!libroEncontrado) {
            JOptionPane.showMessageDialog(null, 
                "El libro con el título: '" + titulo + "' y año " + año_publicacion + " no fue encontrado.", 
                "Búsqueda de Libro", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
             JOptionPane.showMessageDialog(null, 
                "¡Libro encontrado! Título: '" + titulo + "', Año: " + año_publicacion + ".", 
                "Búsqueda de Libro", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return libroEncontrado;
    }

    /**
     * Realiza una búsqueda en la tabla librosdispo por título y año de publicación.
     * * @param db El objeto de conexión a la base de datos.
     * @param tabla El nombre de la tabla (debe ser 'librosdispo').
     * @param titulo El valor del título a buscar.
     * @param anioPublicacion El valor del año de publicación a buscar.
     * @return true si se encuentra al menos una fila, false en caso contrario o si hay un error.
     */
    private boolean verificarExistencia(conectDb db, String tabla, String titulo, int año_publicacion) {
        
        String sql = "SELECT 1 FROM " + tabla + " WHERE titulo = ? AND año_publicacion = ?";
        
        try (Connection cn = db.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            
            pst.setString(1, titulo);
            pst.setInt(2, año_publicacion);
            
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al realizar la búsqueda en la base de datos. Consulta los detalles de la consola.", 
                "Error de base de datos", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}