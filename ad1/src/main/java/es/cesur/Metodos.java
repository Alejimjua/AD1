package es.cesur;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Metodos {

    static String driver = "org.mariadb.jdbc.Driver";
    static String url = "jdbc:mariadb://localhost:3306/";
    static String user = "root";
    static String password = "Triana";

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
