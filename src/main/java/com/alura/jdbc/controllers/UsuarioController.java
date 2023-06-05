package com.alura.jdbc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.alura.jdbc.model.Usuario;
import com.alura.jdbc.views.Login;
import com.alura.jdbc.views.MenuUsuario;

public class UsuarioController implements ActionListener {
	
	private Login vista;
	 
	public UsuarioController(Login vista) {
		this.vista = vista;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombre = vista.getNombre();
		String contrasenha = vista.getContrasenha();
		
		if(Usuario.validarUsuario(nombre, contrasenha)) {
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			vista.dispose();
		}else {
			JOptionPane.showConfirmDialog(vista, "Usuario o Contrasenha no validos");
		}
	}

}
