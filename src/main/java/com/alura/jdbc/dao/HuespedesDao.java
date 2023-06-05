package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.model.Huespedes;

public class HuespedesDao {

	private Connection con;

	public HuespedesDao(Connection con) {
		this.con = con;
	}
	
	public int eliminar(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id = ?");
					try(statement){
						statement.setInt(1, id);
						statement.execute();
			
			int updateCount = statement.getUpdateCount();
			
			return updateCount;
			
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int actualizarH(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		try {
			final   PreparedStatement pState = con.prepareStatement(
				"UPDATE huespedes SET "
				+ " nombre = ?, "
				+ " apellido = ?, "
				+ " fecha_nacimiento = ?, "
				+ " nacionalidad = ?, "
				+ " telefono = ?, "
				+ " id_reserva = ? "
				+ " WHERE id = ?");

			try (pState) {
				pState.setString(1, nombre);
				pState.setString(2, apellido);
				pState.setObject(3, fechaNacimiento);
				pState.setString(4, nacionalidad);
				pState.setString(5, telefono);
				pState.setInt(6, idReserva);
				pState.setInt(7, id);
				pState.execute();
				
				int updateCount = pState.getUpdateCount();
				return updateCount;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	

	
	public void guardar(Huespedes huespedes) {

		try {
			
			final PreparedStatement statement = con.prepareStatement( 
				"INSERT INTO huespedes "
				+ "(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva)"
				+ " VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setString(1, huespedes.getNombre());
				statement.setString(2, huespedes.getApellido());
				statement.setObject(3, huespedes.getFechaNacimiento());
				statement.setString(4, huespedes.getNacionalidad());
				statement.setString(5, huespedes.getTelefono());
				statement.setInt(6, huespedes.getIdReserva());
				statement.execute();
				try (ResultSet rst = statement.getGeneratedKeys()) {
					while (rst.next()) {
						huespedes.setId(rst.getInt(1));
					}
				}

			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void modificarResultado(List<Huespedes> huespedes, PreparedStatement statement) throws SQLException {
		try (ResultSet rst = statement.executeQuery()) {
			while (rst.next()) {
				int id = rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");
				LocalDate fechaNacimiento = rst.getDate("fecha_nacimiento").toLocalDate().plusDays(0);
				String nacionalidad = rst.getString("nacionalidad");
				String telefono = rst.getString("telefono");
				int idReserva = rst.getInt("id_reserva");

				Huespedes huesped = new Huespedes(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono,
						idReserva);
				huespedes.add(huesped);

			}
		}
	}



	public List<Huespedes> mostrar() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";

			try (PreparedStatement pState = con.prepareStatement(sql)) {
				pState.execute();
				modificarResultado(huespedes, pState);

			}
			return huespedes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> buscarId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, "
					+ "nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";

			try (PreparedStatement pState = con.prepareStatement(sql)) {
				pState.setString(1, id);
				pState.execute();
				modificarResultado(huespedes, pState);
			}
			return huespedes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}



	

	
	
