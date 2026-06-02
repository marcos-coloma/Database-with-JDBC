package controller;
import java.util.ArrayList;
import models.Empleados;
public interface OperacionesDAO {
    void insertarEmpleado(String nombre, String departamento);
    void actualizarDepartamento(int id, String nuevoDepartamento);
    void borrarEmpleado(int id);
    ArrayList<Empleados> consultarTodos();
}
