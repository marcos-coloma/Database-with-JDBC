/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.with.jdbc;

/**
 *
 * @author Marcos
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperacionesDB {
    
    // Método para agregar información (INSERT)
    public void insertarEmpleado(String nombre, String departamento) {
        String sql = "INSERT INTO empleados (nombre, departamento) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, departamento);
            pstmt.executeUpdate();
            System.out.println("Empleado '" + nombre + "' insertado correctamente.");
            
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }
    
    
    // Método para modificar información (UPDATE)
    public void actualizarDepartamento(int id, String nuevoDepartamento) {
        String sql = "UPDATE empleados SET departamento = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoDepartamento);
            pstmt.setInt(2, id);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("El departamento del empleado con ID " + id + " fue actualizado a: " +
                nuevoDepartamento);
            } else {
                System.out.println("No se encontró un empleado con el ID: " + id);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }
    
    
    // Método para borrar información (DELETE)
    public void borrarEmpleado(int id) {
        
        String sql = "DELETE FROM empleados WHERE id = ?";
        
        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("El empleado con ID " + id + " ha sido borrado exitosamente.");
            } else {
                System.out.println("No se encontró un empleado con el ID: " + id);
            }
            
        } catch (SQLException e) {
        System.out.println("Error al borrar: " + e.getMessage());
        }
   } 
}
