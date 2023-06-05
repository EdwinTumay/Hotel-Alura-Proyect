package com.alura.jdbc.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.controllers.HuespedesController;
import com.alura.jdbc.controllers.ReservasController;
import com.alura.jdbc.model.Huespedes;
import com.alura.jdbc.model.Reserva;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservasController reservasController;
	private HuespedesController huespedesController;
	private ReservasView reservasView;
	String reserva;
	String huespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {

		this.reservasView = new ReservasView();
		this.reservasController = new ReservasController();
		this.huespedesController = new HuespedesController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 243, 244));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(80, 142, 163));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(311, 62, 320, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");

		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarTablaReservas();

		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendar.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		mostrarTablaHuespedes();

		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/search.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Hh100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(new Color(240, 243, 244));
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(new Color(240, 243, 244));
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(new Color(240, 243, 244));
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("⬅");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(new Color(240, 243, 244));
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(240, 243, 244));
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

//		JPanel btnbuscar = new JPanel();  // el de abajo boton, este es el boton original
		JButton btnbuscar = new JButton();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				} else {
					mostrarTablaReservasId();
					mostrarTablaHuespedesId();
				}

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(SystemColor.textHighlight);
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(new Color(80, 142, 163));
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

//		JPanel btnEditar = new JPanel();
		JButton btnEditar = new JButton();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int rowReservas = tbReservas.getSelectedRow();
				int rowHuespedes = tbHuespedes.getSelectedRow();
				
				if (rowReservas >= 0) {
					actualizarReservas();
//					limpiarTabla();
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				} else if (rowHuespedes >= 0) {
					actualizarHuespedes();
//					limpiarTabla();
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(SystemColor.textHighlight);
		btnEditar.setBounds(635, 504, 122, 44);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setBounds(0, 0, 122, 44);
		lblEditar.setForeground(new Color(80, 142, 163));
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		btnEditar.add(lblEditar);

//		JPanel btnEliminar = new JPanel();
		JButton btnEliminar = new JButton();

		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int rowReservas = tbReservas.getSelectedRow();
				int rowHuespedes = tbHuespedes.getSelectedRow();

				if (rowReservas >= 0) {
					reserva = tbReservas.getValueAt(rowReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "Desea Eliminar la reserva?");
					if (confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(rowReservas, 0).toString();
						reservasController.eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Resgistro eliminado con exito!");
						limpiarTabla();
						mostrarTablaReservas();
						mostrarHuespedes();
						mostrarHuespedes();
						mostrarTablaHuespedes();
					}
				} else if (rowHuespedes >= 0) {
					huespedes = tbHuespedes.getValueAt(rowHuespedes, 0).toString();
					int confirmarH = JOptionPane.showConfirmDialog(null, "Desea Borrar al huesped");

					if (confirmarH == JOptionPane.YES_OPTION) {
						String valor = tbHuespedes.getValueAt(rowHuespedes, 0).toString();
						huespedesController.eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Huesped borrado con exito!");
						limpiarTabla();
						mostrarHuespedes();
						mostrarTablaHuespedes();
						mostrarTablaReservas();
						mostrarHuespedes();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Hubo un error al eliminar");
				}
			}
		});

		btnEliminar.setBackground(SystemColor.textHighlight);
		btnEliminar.setBounds(767, 504, 122, 44);
		contentPane.add(btnEliminar);
		btnEliminar.setLayout(null);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setBounds(0, 0, 122, 44);
		btnEliminar.add(lblEliminar);
		lblEliminar.setForeground(new Color(80, 142, 163));
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		setResizable(false);
	}

	// CRUD Reservas
	private List<Reserva> mostrarReservas() {
		return this.reservasController.mostrarR();
	}

	private List<Reserva> buscarIdReserva() {
		return this.reservasController.buscar(txtBuscar.getText());
	}

	private void mostrarTablaReservas() {
		List<Reserva> reserva = mostrarReservas();
		modelo.setRowCount(0);
		try {
			for (Reserva reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getDataE(), reservas.getDataS(),
						reservas.getValor(), reservas.getFormaPago() });
			}

		} catch (Exception e) {
			throw e;
		}
	}

	private void mostrarTablaReservasId() {
		List<Reserva> reserva = buscarIdReserva();
		modelo.setRowCount(0);
		try {
			for (Reserva reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getDataE(), reservas.getDataS(),
						reservas.getValor(), reservas.getFormaPago() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void actualizarReservas() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresent(fila -> {
					LocalDate dataE;
					LocalDate dataS;

					try {
						DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyy-MM-dd");
						dataE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(),
								dateFormat);
						dataS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(),
								dateFormat);
					} catch (DateTimeException e) {
						throw new RuntimeException(e);
					}
					this.reservasView.limpiarValor();

					String valor = calcularValorReserva(dataE, dataS);
					String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
					Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

					if (tbReservas.getSelectedColumn() == 0) {
						JOptionPane.showMessageDialog(this, "No se puede editar los ID");
					} else {
						this.reservasController.actualizarReserva(dataE, dataS, valor, formaPago, id);
						JOptionPane.showMessageDialog(this, String.format("Registro modificado con Exito"));
					}
				});
	}

	public String calcularValorReserva(LocalDate dataE, LocalDate dataS) {

		if (dataE != null && dataS != null) {
			int dias = (int) ChronoUnit.DAYS.between(dataE, dataS);
			int noche = 100000;
			int valor = dias * noche;
			String result = Integer.toString(valor);
			return result + " pesos";
		} else {
			return "";
		}
	}

	// CRUD Huesped
	private List<Huespedes> mostrarHuespedes() {
		return this.huespedesController.mostrarH();
	}

	private List<Huespedes> buscarHuespedId() {
		return this.huespedesController.buscarHuesped(txtBuscar.getText());
	}

	private void mostrarTablaHuespedes() {
		List<Huespedes> huesped = mostrarHuespedes();
		modeloHuesped.setRowCount(0);
		try {
			for (Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] { huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(),
						huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono(),
						huespedes.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void mostrarTablaHuespedesId() {
		List<Huespedes> huesped = buscarHuespedId();
		modeloHuesped.setRowCount(0);
		try {
			for (Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] { huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(),
						huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono(),
						huespedes.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void actualizarHuespedes() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(rowHuespedes -> {

					String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
					LocalDate fechaNacimiento = LocalDate
							.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
					String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
					String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
					Integer id_reserva = Integer
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
					Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow() ,0).toString());

					
					if(tbHuespedes.getSelectedColumn() == 6) {
						JOptionPane.showMessageDialog(this, "No se pueden modificar los id");
					}else {
					this.huespedesController.actualizarH(nombre, apellido, fechaNacimiento, nacionalidad, telefono,
							id_reserva, id);
					JOptionPane.showMessageDialog(this, String.format("Registro modificado con exito!"));
					
					}

				}, () -> JOptionPane.showInternalMessageDialog(this, "Hola"));
	}

	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);

	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
