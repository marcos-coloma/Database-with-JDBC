/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package database.with.jdbc;

/**
 *
 * @author Marcos
 */
public class App {

    /**
     * @param args the command line arguments
     */
    // Método principal para poner a prueba todas las funciones
    public static void main(String[] args) {
        OperacionesDB ops = new OperacionesDB();
        
        // 1. Ejecutar un INSERT
        ops.insertarEmpleado("Ana García", "Recursos Humanos");
        
        // 2. Ejecutar un UPDATE (Suponiendo que a Ana se le asignó el ID 1)
        ops.actualizarDepartamento(1, "Finanzas");
        
        // 3. Ejecutar un DELETE
        ops.borrarEmpleado(1);
    }

    
}
