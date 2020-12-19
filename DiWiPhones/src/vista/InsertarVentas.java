package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.Venta;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertarVentas extends JPanel {
	private JTable tableProductos;
	private JComboBox cmbEmpleados;
	private JComboBox cmbClientes;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JComboBox comboBox;
	private JTextField txtUnidades;
	ArrayList<Venta> ventas = new ArrayList<>();
	private JCalendar calendario;
	private boolean valid = false;

	/**
	 * Create the panel.
	 */
	public InsertarVentas() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnCesta = new JButton("A\u00F1adir");
		btnCesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargar();
				cargarTabla();
			}
		});

		txtUnidades = new JTextField();
		txtUnidades.setBounds(487, 44, 86, 20);
		add(txtUnidades);
		txtUnidades.setColumns(10);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProductos.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar de la cesta", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					ventas.remove(tableProductos.getSelectedRow());
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(583, 358, 89, 23);
		add(btnEliminar);

		JLabel lblUd = new JLabel("Unidades: ");
		lblUd.setBounds(429, 47, 75, 14);
		add(lblUd);
		btnCesta.setBounds(583, 75, 89, 23);
		add(btnCesta);

		comboBox = new JComboBox();
		comboBox.setModel(cargaProductos());
		comboBox.setBounds(429, 75, 144, 22);
		add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 114, 241, 233);
		add(scrollPane);

		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);
		modeloTabla.setColumnIdentifiers(new Object[] { "Producto", "Unidades" });
		tableProductos.setModel(modeloTabla);
		modeloTabla.setRowCount(0);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Venta v = new Venta();
				v=pideDatosVenta();
				if(cmbClientes.getSelectedIndex()==0||cmbEmpleados.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "introduce una cliente valido", "Error", JOptionPane.WARNING_MESSAGE);
				}else if(ventas.size()<1){
					JOptionPane.showMessageDialog(null, "Selecciona productos vendidos", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					for(int x=0;x<ventas.size();x++) {
						restarStock(x);
						v.setPrecioTotal(v.getPrecioTotal()+(ventas.get(x).getUnidades()*ventas.get(x).getPrecio()));
						
					}
					insertVenta(v.getFechaTotal(), v.getDniCliente(), v.getDniPersonal(),v.getPrecioTotal());
					if(valid==true) {
						insertProductosVentas();
						for(int x=0;x<ventas.size();x++) {
							restarStock(x);
						}
					}
					JOptionPane.showMessageDialog(null, "Venta Realizada");
					VentaVista vv = new VentaVista();
					nuevoPanel(vv);
				}
			}
		});
		btnInsert.setBounds(315, 394, 89, 23);
		add(btnInsert);

		calendario = new JCalendar();
		calendario.setBounds(90, 44, 230, 136);
		//calendario.setMaxSelectableDate(2020);
		add(calendario);

		cmbClientes = new JComboBox();
		cmbClientes.setModel(cargaClientes());
		cmbClientes.setBounds(90, 325, 188, 22);
		add(cmbClientes);

		cmbEmpleados = new JComboBox();
		cmbEmpleados.setModel(cargaPersonal());
		cmbEmpleados.setBounds(90, 220, 188, 22);
		add(cmbEmpleados);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	}

	public void insertVenta(String fecha, String dniC, String dniP, int precioTotal) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into venta (fecha, id_cliente, id_personal, precioVenta) values ('"
					+ fecha + "',  (select id_cliente from clientes where dni='"+dniC + "'), (select id_personal from personal where dni='" + dniP + "'),"+precioTotal+")");
			valid=true;
			conexion.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Factura ya registrada inserte otra", "Error", JOptionPane.WARNING_MESSAGE);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	public DefaultComboBoxModel cargaProductos() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Productos-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre from productos");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("nombre"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}

	public DefaultComboBoxModel cargaPersonal() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Personal-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre, dni from personal");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("dni"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}
	
	public DefaultComboBoxModel cargaClientes() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Clientes-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre, dni from clientes");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("dni"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}

	public void cargarTabla() {
		int unidades = 0;
		try {
			unidades = Integer.parseInt(txtUnidades.getText().toString());
		} catch (NumberFormatException e) {
			unidades = -1;
		}
		if (unidades == -1 || unidades <= 0) {
			JOptionPane.showMessageDialog(null, "Introduce unidades validas", "Error", JOptionPane.WARNING_MESSAGE);
		} else if (comboBox.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Selecciona un producto", "Error", JOptionPane.WARNING_MESSAGE);
		} else {
			modeloTabla.setRowCount(0);
			for (Venta v : ventas) {
				modeloTabla.addRow(new Object[] { v.getProducto(), v.getUnidades() });
			}

		}
	}

	public void cargar() {
		GestionBBDD gestor = new GestionBBDD();
		int unidades = 0;
		try {
			unidades = Integer.parseInt(txtUnidades.getText().toString());
		} catch (NumberFormatException e) {
			unidades = -1;
		}
		if (unidades != -1 || unidades>0) {
			Venta e = new Venta();
			e.setUnidades(unidades);
			e.setProducto(comboBox.getSelectedItem().toString());
			e.setPrecio(gestor.consultaPrecioCompra(e.getProducto()));
			boolean validar=false;
			for(int x = 0; x<ventas.size();x++) {
				if(ventas.get(x).getProducto().compareTo(e.getProducto())==0) {
					ventas.get(x).setUnidades(ventas.get(x).getUnidades()+e.getUnidades());
					validar=true;
				}
			}
			if(e.getProducto().compareTo("-Productos-")!=0&&validar==false) {
				ventas.add(e);
			}
		}

	}
	
	public Venta pideDatosVenta() {
		Venta v = new Venta();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		v.setDniCliente(cmbClientes.getSelectedItem().toString());
		v.setDniPersonal(cmbEmpleados.getSelectedItem().toString());
		v.setFechaTotal(dFormat.format(calendario.getDate()));
		
		return v;
	}
	
	public void insertProductosVentas() {
		for(int x=0; x<ventas.size();x++) {
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

				Statement consulta = conexion.createStatement();
				consulta.executeUpdate("insert into productos_ventas (id_producto, id_venta, unidades) values "
						+ "((select id_producto from productos where nombre='"+ventas.get(x).getProducto()+"'), "
								+recogerFactura()+","+ventas.get(x).getUnidades()+")");
				conexion.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int recogerFactura() {
		int id=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta
					.executeQuery("select factura from venta order by id_cliente desc limit 1");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt("factura");
			} else {
				System.out.println("Error");
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return id;
	}
	
	public void restarStock(int x) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update productos set stock = " + recogerStock(x)+"-"+ventas.get(x).getUnidades()
					+ " where nombre = '" + ventas.get(x).getProducto() + "'");


			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int recogerStock(int x) {
		int stock=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("select stock from productos where nombre='" + ventas.get(x).getProducto()+"'");


			if (registro.next()) {
				stock = registro.getInt("stock");
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return stock;
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
}
