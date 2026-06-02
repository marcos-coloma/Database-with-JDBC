package controller;

import conection.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Empleados;

public abstract class EmpleadoDAO implements OperacionesDAO {
    
    //implementar la interfaz usar PreparedStatement para evitar inyecciones SQL

    // Método para insertar un nuevo empleado (INSERT)
    
    @Override
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

    // Método para actualizar el departamento de un empleado (UPDATE)
    @Override
    public void actualizarDepartamento(int id, String nuevoDepartamento) {
        String sql = "UPDATE empleados SET departamento = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoDepartamento);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Departamento actualizado correctamente para el empleado con ID " + id);
        } catch (SQLException e) {
            System.out.println("Error al actualizar departamento: " + e.getMessage());
        }
    }



    // Método para borrar un empleado (DELETE)
    @Override
    public void borrarEmpleado(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado con ID " + id + " borrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al borrar empleado: " + e.getMessage());
        }
    }

    // Método para consultar todos los empleados (SELECT) iterar ResultSet y devolver un ArrayList<empleado>
    @Override
    public ArrayList<Empleados> consultarTodos() {

    ArrayList<Empleados> lista = new ArrayList<>();

    String sql = "SELECT * FROM empleados";

    try (Connection conn = ConexionDB.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {

            Empleados emp = new Empleados(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("departamento")
            );

            lista.add(emp);
        }

    } catch (SQLException e) {
        System.out.println("Error al consultar empleados: " + e.getMessage());
    }

    return lista;
}

}