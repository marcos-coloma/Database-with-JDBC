package controller;

import conection.ConexionDB;
import models.Departamento;
import models.Empleados;

import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAO implements OperacionesDAO {

    // ---------------- DEPARTAMENTOS ----------------
    @Override
    public ArrayList<Departamento> consultarDepartamentos() {

        ArrayList<Departamento> lista = new ArrayList<>();

        String sql = "SELECT id_depto, nombre_depto FROM departamentos";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(new Departamento(
                        rs.getInt("id_depto"),
                        rs.getString("nombre_depto")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error departamentos: " + e.getMessage());
        }

        return lista;
    }

    // ---------------- INSERT ----------------
    @Override
    public void insertarEmpleado(String nombre, int idDepto, String foto) {

        String sql = "INSERT INTO empleados (nombre, id_depto, foto) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idDepto);
            ps.setString(3, foto);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error insert: " + e.getMessage());
        }
    }

    // ---------------- UPDATE ----------------
    @Override
    public void actualizarEmpleado(int id, String nombre, int idDepto, String foto) {

        String sql = "UPDATE empleados SET nombre=?, id_depto=?, foto=? WHERE id=?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idDepto);
            ps.setString(3, foto);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error update: " + e.getMessage());
        }
    }

    // ---------------- DELETE ----------------
    @Override
    public void borrarEmpleado(int id) {

        String sql = "DELETE FROM empleados WHERE id=?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error delete: " + e.getMessage());
        }
    }

    // ---------------- SELECT COMPLETO ----------------
    @Override
    public ArrayList<Empleados> consultarTodos() {

        ArrayList<Empleados> lista = new ArrayList<>();

        String sql = """
            SELECT e.id,
                   e.nombre,
                   d.nombre_depto,
                   e.foto
            FROM empleados e
            JOIN departamentos d ON e.id_depto = d.id_depto
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(new Empleados(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nombre_depto"),
                        rs.getString("foto")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error select: " + e.getMessage());
        }

        return lista;
    }
}