package controller;

import java.util.ArrayList;
import models.Empleados;
import models.Departamento;

public interface OperacionesDAO {

    void insertarEmpleado(String nombre, int idDepto, String foto);

    void actualizarEmpleado(int id, String nombre, int idDepto, String foto);

    void borrarEmpleado(int id);

    ArrayList<Empleados> consultarTodos();

    ArrayList<Departamento> consultarDepartamentos();
}

