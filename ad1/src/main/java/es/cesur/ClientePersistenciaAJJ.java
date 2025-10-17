package es.cesur;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ClientePersistenciaAJJ {

    static String driver = "org.mariadb.jdbc.Driver";
    static String url = "jdbc:mariadb://localhost:3306/";
    static String user = "root";
    static String password = "Triana";
    
    public static int crearCliente(String nombre, String apellidos, String email, String dni, String clave) {
        String sql = "INSERT INTO clienteAJJ (nombre, apellidos, email, dni, clave) VALUES ("
                + "'" + nombre + "', "
                + "'" + apellidos + "', "
                + "'" + email + "', "
                + "'" + dni + "', "
                + "'" + clave + "'"
                + ")";

        int idGenerado = 0;

        try (Connection conn = conexion("hotelAJJ");
             Statement stmt = conn.createStatement()) {

            int filasAfectadas = stmt.executeUpdate(sql);

            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.executeQuery("SELECT MAX(idCliente) FROM clienteAJJ")) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        System.out.println("Cliente creado con éxito. ID: " + idGenerado);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear cliente.");
            e.printStackTrace();
        }
        return idGenerado;
    }

    
    public static String leerCliente(int idCliente,String campo) {
        String sql = "SELECT * FROM clienteAJJ WHERE idCliente = " + idCliente;
        String resultado = "";

        try (Connection conn = conexion("hotelAJJ");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                resultado = rs.getString(campo);
            }
        } catch (SQLException e) {
            System.err.println("Error al leer cliente.");
            e.printStackTrace();
        }
        return resultado;
    }

    public static boolean actualizarCliente(int idCliente, String campo, String nuevoValor) {
        String sql = "UPDATE clienteAJJ SET " + campo + " = '" + nuevoValor + "' WHERE idCliente = " + idCliente;
        boolean exito = false;

        try (Connection conn = conexion("hotelAJJ");
             Statement stmt = conn.createStatement()) {

            int filasAfectadas = stmt.executeUpdate(sql);
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente.");
            e.printStackTrace();
        }
        return exito;
    }


    public static boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clienteAJJ WHERE idCliente = " + idCliente;
        boolean exito = false;

        try (Connection conn = conexion("hotelAJJ");
             Statement stmt = conn.createStatement()) {

            int filasAfectadas = stmt.executeUpdate(sql);
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al borrar cliente.");
            e.printStackTrace();
        }
        return exito;
    }

    public static Connection conexion(String bbdd) {
        Connection conexion = null;
        try{
            Class.forName(driver);
            conexion = DriverManager.getConnection(url+bbdd, user, password);
            System.out.println("Conexion realizada");
        } catch (SQLException e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error de driver");
            e.printStackTrace();
        }
        return conexion;
    }


}
