package com.pe.farmacia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Grupo 6
 */
public class Conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3307/db_farmacia?serverTimezone=UTC";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567";
    //Patrón de diseño Singleton
    // El campo para almacenar la instancia singleton debe
    // declararse estático.
    private static Connection con = null;
    
    // El constructor del singleton siempre debe ser privado
    // para evitar llamadas de construcción directas con el
    // operador `new`.
    private Conexion(){
        
    }
    

    //Para obtener la instancia unicamente por este metodo
    //La palabra reservada static hace posibler el acceso mediante Class.metodo
    public static  Connection getConnection() {
        try {
            Class.forName(DRIVER);
            if (con == null) {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error" + e);
        }
        return con;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
/*
    private static final String URL = "jdbc:mysql://localhost:3306/bd_farmasalud?serverTimezone=UTC";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    //Patrón de diseño Singleton
    // El campo para almacenar la instancia singleton debe
    // declararse estático.
    private static Connection con;
    
    private static Conexion instancia;
    
    // El constructor del singleton siempre debe ser privado
    // para evitar llamadas de construcción directas con el
    // operador `new`.
    private Conexion(){
        
    }
    

    //Para obtener la instancia unicamente por este metodo
    //La palabra reservada static hace posibler el acceso mediante Class.metodo
    public Connection conectar() {
        try {
            Class.forName(DRIVER);
            if (con == null) {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error" + e);
        }
        return con;
    }
    
    //Singleton
    
    public static Conexion getConexion(){
        if (instancia == null)  {
            instancia = new Conexion();
        }
        return instancia;
    }*/

}
