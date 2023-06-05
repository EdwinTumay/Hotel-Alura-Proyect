package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.alura.jdbc.model.Reserva;

public class ReservasDao {

	private Connection con;

	public ReservasDao(Connection con) {
		super();
		this.con = con;
	}
	
	public void eliminar (Integer id) {
		try {
			Statement state = con.createStatement();
			state.execute("SET FOREIGN_KEY_CHECKS = 0");
			
				PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE id = ?");
				statement.setInt(1, id);
				statement.execute();
				state.execute("SET FOREIGN_KEY_CHECKS=1");
				
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_de_pago) "
					+"VALUES (?,?,?,?)";
			try(PreparedStatement statement = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)){
				
				statement.setObject(1, reserva.getDataE());
				statement.setObject(2, reserva.getDataS());
				statement.setObject(3, reserva.getValor());
				statement.setObject(4, reserva.getFormaPago());
				statement.executeUpdate();
				
				try(ResultSet resultSet = statement.getGeneratedKeys()){
					while(resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
			

		}
	}
	
	public List<Reserva> mostrar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas";
			
			try(PreparedStatement statement = con.prepareStatement(sql)){
				statement.execute();
				modificarResultado(reservas, statement);
				
			}
			return reservas;

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscarId(String id){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id=?";
			
			try(PreparedStatement statement = con.prepareStatement(sql)){
				statement.setString(1,id);
				statement.execute();
				modificarResultado(reservas, statement);
				
			}
			return reservas;

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}	
	
	public void actualizar(LocalDate dataE, LocalDate dataS, String valor, String formaPago, Integer id) {
		try(PreparedStatement statement = con.prepareStatement("UPDATE reservas SET "
				+ "fecha_entrada = ?, fecha_salida = ?, forma_de_pago = ? WHERE id = ?")){
			statement.setObject(1, java.sql.Date.valueOf(dataE));
			statement.setObject(2, java.sql.Date.valueOf(dataS));
//			pState.setString(3, valor);
			statement.setString(3, formaPago);
			statement.setInt(4, id);
			statement.execute();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private void modificarResultado(List<Reserva> reserva, PreparedStatement statement) throws SQLException {
		try(ResultSet rst = statement.getResultSet()){
			while(rst.next()) {
				int id =rst.getInt("id");
				LocalDate fechaE = rst.getDate("fecha_entrada").toLocalDate().plusDays(0);
				LocalDate fechaS = rst.getDate("fecha_salida").toLocalDate().plusDays(0);
				String valor = rst.getString("valor");
				String formaPago = rst.getString("forma_de_pago");
				
				Reserva producto = new Reserva(id, fechaE, fechaS, valor, formaPago);
				reserva.add(producto);
				
			}
		}
	}

}
