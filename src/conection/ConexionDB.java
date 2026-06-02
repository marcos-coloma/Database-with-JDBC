package conection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConexionDB.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró config.properties");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar config.properties", e);
        }
    }

    public static Connection conectar() {

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Connection conexion = DriverManager.getConnection(url, user, password);
            System.out.println("¡Conexión a la base de datos establecida con éxito!");
            return conexion;

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
}