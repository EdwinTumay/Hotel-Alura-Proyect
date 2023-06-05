package com.alura.jdbc.controllers;

import java.time.LocalDate;
import java.util.List;

import com.alura.jdbc.dao.HuespedesDao;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.model.Huespedes;

public class HuespedesController {
	
	private HuespedesDao huespedesDao;
	
	public HuespedesController() {
		this.huespedesDao = new HuespedesDao(new ConnectionFactory().recuperaConexion());
		
	}
	
	public int actualizarH(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		return huespedesDao.actualizarH(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void eliminar(Integer idReserva) {
		this.huespedesDao.eliminar(idReserva);
	}
	
	public List<Huespedes> mostrarH(){
		return huespedesDao.mostrar();
	}
	
	public List<Huespedes> buscarHuesped(String id){
		return this.huespedesDao.buscarId(id);
	}
	
	public void guardar(Huespedes huespedes) {
		huespedesDao.guardar(huespedes);
	}
	
	
}
