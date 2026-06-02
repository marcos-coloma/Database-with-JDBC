package views;

import controller.EmpleadoDAO;
import models.Departamento;
import models.Empleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class ventanaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtId;
    private JTextField txtNombre;

    private JComboBox<Departamento> cbDepartamento;

    private JLabel lblFoto;
    private JButton btnFoto;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;

    private String rutaFoto;

    private EmpleadoDAO dao;

    public ventanaPrincipal(EmpleadoDAO dao) {

        this.dao = dao;

        setTitle("Gestión de Empleados");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        init();
        loadDepartamentos();
        loadTable();

        setVisible(true);
    }

    private void init() {

        setLayout(new BorderLayout());

        // FORM
        JPanel form = new JPanel(new GridLayout(10, 1, 5, 5));

        form.add(new JLabel("ID"));
        txtId = new JTextField();
        txtId.setEditable(false);
        form.add(txtId);

        form.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        form.add(txtNombre);

        form.add(new JLabel("Departamento"));
        cbDepartamento = new JComboBox<>();
        form.add(cbDepartamento);

        btnFoto = new JButton("Buscar Foto");
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(120, 120));

        form.add(btnFoto);
        form.add(lblFoto);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        form.add(btnAgregar);
        form.add(btnModificar);
        form.add(btnEliminar);

        add(form, BorderLayout.WEST);

        // TABLE
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Departamento");
        modelo.addColumn("Foto");

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // EVENTS
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int row = tabla.getSelectedRow();

                if (row != -1) {
                    txtId.setText(modelo.getValueAt(row, 0).toString());
                    txtNombre.setText(modelo.getValueAt(row, 1).toString());

                    rutaFoto = modelo.getValueAt(row, 3) != null
                            ? modelo.getValueAt(row, 3).toString()
                            : "";

                    if (!rutaFoto.isEmpty()) {
                        lblFoto.setIcon(new ImageIcon(rutaFoto));
                    }
                }
            }
        });

        btnFoto.addActionListener(e -> choosePhoto());

        btnAgregar.addActionListener(e -> addEmployee());
        btnModificar.addActionListener(e -> updateEmployee());
        btnEliminar.addActionListener(e -> deleteEmployee());
    }

    // ---------------- FOTO ----------------
    private void choosePhoto() {

        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            java.io.File file = chooser.getSelectedFile();
            rutaFoto = file.getAbsolutePath();

            lblFoto.setIcon(new ImageIcon(rutaFoto));
        }
    }

    // ---------------- DATA ----------------
    private void loadDepartamentos() {

        ArrayList<Departamento> list = dao.consultarDepartamentos();

        cbDepartamento.removeAllItems();

        for (Departamento d : list) {
            cbDepartamento.addItem(d);
        }
    }

    private void loadTable() {

        modelo.setRowCount(0);

        ArrayList<Empleados> list = dao.consultarTodos();

        for (Empleados e : list) {

            modelo.addRow(new Object[]{
                    e.getId(),
                    e.getNombre(),
                    e.getDepartamento(),
                    e.getFoto()
            });
        }
    }

    // ---------------- CRUD ----------------
    private void addEmployee() {

        String nombre = txtNombre.getText().trim();

        Departamento d = (Departamento) cbDepartamento.getSelectedItem();

        dao.insertarEmpleado(nombre, d.getId(), rutaFoto);

        loadTable();
        clear();
    }

    private void updateEmployee() {

        int id = Integer.parseInt(txtId.getText());

        String nombre = txtNombre.getText().trim();

        Departamento d = (Departamento) cbDepartamento.getSelectedItem();

        dao.actualizarEmpleado(id, nombre, d.getId(), rutaFoto);

        loadTable();
        clear();
    }

    private void deleteEmployee() {

        int id = Integer.parseInt(txtId.getText());

        dao.borrarEmpleado(id);

        loadTable();
        clear();
    }

    private void clear() {

        txtId.setText("");
        txtNombre.setText("");
        rutaFoto = "";

        lblFoto.setIcon(null);
    }
}