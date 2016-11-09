package com.workup.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	//localhost
	private final String URL = "jdbc:mysql://localhost:3306/aplicativotcc";
	
	//amazon
	//private final String URL = "jdbc:mysql://54.201.17.139:3306/aplicativotcc";

	private final String USUARIO = "root";
	private final String SENHA = "1234";

	public Connection connection;
	
	/**
     * Método que devolve conexão
     * @return 
     */
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USUARIO, SENHA);
            
            return connection;
        } catch (Exception e) {
            System.out.println("Erro ao conectar. Motivo: " + e.getMessage());
            return null;
        }        
    }
    
    
    /**
     * Método que desconecta
     */
    public void desconectar(){
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao desconectar. Motivo: " + e.getMessage());
        }
    }
}
