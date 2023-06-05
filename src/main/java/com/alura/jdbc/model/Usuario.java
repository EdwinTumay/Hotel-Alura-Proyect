package com.alura.jdbc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class Usuario {

	private String nombre;
	private String contrasenha;
	
	public Usuario(String nombre, String contrasenha) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	
	public static boolean validarUsuario(String nombre, String contrasenha) {
		ConnectionFactory conFac = new ConnectionFactory();
		Connection con = null;
		PreparedStatement state =null;
		ResultSet result = null;
		
		try {
			con = conFac.recuperaConexion();
			state = con.prepareStatement("SELECT * FROM usuarios WHERE nombre=? AND contrasenha =?");
			state.setString(1, nombre);
			state.setString(2, contrasenha);
			result = state.executeQuery();
			return result.next();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(result !=null)
					result.close();
				if(state !=null)
					state.close();
				if(con != null)
					con.close();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	
}
