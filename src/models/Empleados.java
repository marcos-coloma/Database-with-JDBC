package models;

public class Empleados {

    private int id;
    private String nombre;
    private String departamento;
    private String foto;

    // constructor completo
    public Empleados(int id, String nombre, String departamento, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.foto = foto;
    }

    // constructor sin foto (opcional, útil para inserts)
    public Empleados(int id, String nombre, String departamento) {
        this(id, nombre, departamento, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}