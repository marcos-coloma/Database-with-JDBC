/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author Marcos & Fabian
 */


import controller.EmpleadoDAO;
import views.ventanaPrincipal;

public class App {
    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO();
        new ventanaPrincipal(dao);
    }
}