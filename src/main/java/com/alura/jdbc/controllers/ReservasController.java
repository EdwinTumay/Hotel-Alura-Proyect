package com.alura.jdbc.controllers;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import com.alura.jdbc.dao.ReservasDao;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.model.Reserva;

public class ReservasController {

	private ReservasDao reservaDao;
	
	public ReservasController() {
		Connection con = new ConnectionFactory().recuperaConexion();
		this.reservaDao = new ReservasDao(con);
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDao.guardar(reserva);
	}
	
	public List<Reserva> mostrarR(){
		return this.reservaDao.mostrar();
	}
	
	public List<Reserva> buscar(String id){
		return this.reservaDao.buscarId(id);
	}
	
	public void actualizarReserva(LocalDate dataE, LocalDate dataS, String valor, String formaPago, Integer id) {
		this.reservaDao.actualizar(dataE, dataS, valor, formaPago, id);
	}
	
	public void eliminar (Integer id) {
		this.reservaDao.eliminar(id);
	}
}
