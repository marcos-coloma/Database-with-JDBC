package views;

import models.Empleados;
import controller.EmpleadoDAO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

public class ventanaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDepartamento;

    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnAgregar;


    private EmpleadoDAO dao;

    public ventanaPrincipal(EmpleadoDAO dao) {

        this.dao = dao;

        setTitle("Gestión de Empleados");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarTabla();

        setVisible(true);
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        // ---------------- PANEL FORM ----------------
        JPanel panelFormulario = new JPanel(new GridLayout(7, 1, 5, 5));

        panelFormulario.add(new JLabel("ID"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Departamento"));
        txtDepartamento = new JTextField();
        panelFormulario.add(txtDepartamento);

        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnAgregar = new JButton("Agregar");

        panelFormulario.add(btnModificar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnAgregar);

        add(panelFormulario, BorderLayout.WEST);

        // ---------------- TABLE ----------------
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Departamento");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---------------- EVENTS ----------------
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {
                    txtId.setText(modelo.getValueAt(fila, 0).toString());
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtDepartamento.setText(modelo.getValueAt(fila, 2).toString());
                }
            }
        });

        btnModificar.addActionListener(e -> modificarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnAgregar.addActionListener(e -> agregarEmpleado());
    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        ArrayList<Empleados> empleados = dao.consultarTodos();

        for (Empleados emp : empleados) {
            modelo.addRow(new Object[]{
                    emp.getId(),
                    emp.getNombre(),
                    emp.getDepartamento()
            });
        }
    }

    private void modificarEmpleado() {

        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un empleado primero.");
                return;
            }

            String nombre = txtNombre.getText().trim();
            String departamento = txtDepartamento.getText().trim();

            if (nombre.isEmpty() || departamento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y departamento no pueden estar vacíos.");
                return;
            }

            int id = Integer.parseInt(txtId.getText());

            dao.actualizarDepartamento(id, departamento);

            cargarTabla();

            JOptionPane.showMessageDialog(this, "Empleado modificado correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar empleado: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {

        try {

            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un empleado primero.");
                return;
            }

            int id = Integer.parseInt(txtId.getText());

            dao.borrarEmpleado(id);

            cargarTabla();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar empleado: " + ex.getMessage());
        }
    }

    private void agregarEmpleado() {

    try {
        String nombre = txtNombre.getText().trim();
        String departamento = txtDepartamento.getText().trim();

        if (nombre.isEmpty() || departamento.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre y departamento no pueden estar vacíos.");
            return;
        }

        dao.insertarEmpleado(nombre, departamento);

        cargarTabla();
        limpiarCampos();

        JOptionPane.showMessageDialog(this,
                "Empleado agregado correctamente.");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al agregar empleado: " + ex.getMessage());
    }
}
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDepartamento.setText("");
    }
}

