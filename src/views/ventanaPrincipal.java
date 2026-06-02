package views;

import models.Empleados;
import controller.EmpleadoDAO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import controller.EmpleadoDAO;

public class ventanaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDepartamento;

    private JButton btnModificar;
    private JButton btnEliminar;

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

        //----------------------------
        // PANEL IZQUIERDO
        //----------------------------

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(8, 1, 5, 5));

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

        panelFormulario.add(btnModificar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.WEST);

        //----------------------------
        // TABLA DERECHA
        //----------------------------

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Departamento");

        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        //----------------------------
        // EVENTOS
        //----------------------------

        tabla.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {

                    txtId.setText(
                            tabla.getValueAt(fila, 0).toString());

                    txtNombre.setText(
                            tabla.getValueAt(fila, 1).toString());

                    txtDepartamento.setText(
                            tabla.getValueAt(fila, 2).toString());
                }
            }
        });

        btnModificar.addActionListener(e -> modificarEmpleado());

        btnEliminar.addActionListener(e -> eliminarEmpleado());
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

            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String departamento = txtDepartamento.getText();

            Empleados emp = new Empleados(
                    id,
                    nombre,
                    departamento
            );

            dao.actualizarDepartamento(emp);

            cargarTabla();

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado modificado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al modificar empleado."
            );
        }
    }

    private void eliminarEmpleado() {

        try {

            int id = Integer.parseInt(txtId.getText());

            dao.borrarEmpleado(id);

            cargarTabla();

            limpiarCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado eliminado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar empleado."
            );
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtNombre.setText("");
        txtDepartamento.setText("");
    }
}