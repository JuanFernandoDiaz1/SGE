package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.Cliente;
import modelo.Compras;

public class InsertarCompras extends JPanel {
	private JTable tableProductos;
	private JComboBox cmbEmpleados;
	private JComboBox cmbProveedor;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JComboBox comboBox;
	private JTextField txtUnidades;
	ArrayList<Compras> compras = new ArrayList<>();
	private JCalendar calendario;
	private boolean valid = false;

	/**
	 * Create the panel.
	 */
	public InsertarCompras() {
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
				if (tableProductos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar de la cesta", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					compras.remove(tableProductos.getSelectedRow());
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
				Compras c = new Compras();
				c = pideDatosCompra();
				if (cmbProveedor.getSelectedIndex() == 0 || cmbEmpleados.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecciona un proveedor valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (compras.size() < 1) {
					JOptionPane.showMessageDialog(null, "Selecciona productos vendidos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					for(int x=0;x<compras.size();x++) {
						sumarStock(x);
						c.setPrecioTotal(c.getPrecioTotal()+(compras.get(x).getUnidades()*compras.get(x).getPrecio()));
						
					}
					insertCompra(c.getFechaTotal(), c.getNifProveedor(), c.getDniPersonal(), c.getPrecioTotal());
					if (valid == true) {
						insertProductosCompras();
						
						
						
					}
					JOptionPane.showMessageDialog(null, "Compra A�adida");
					ComprasVista cv = new ComprasVista();
					nuevoPanel(cv);
				}
			}
		});
		btnInsert.setBounds(315, 394, 89, 23);
		add(btnInsert);

		calendario = new JCalendar();
		calendario.setBounds(90, 44, 230, 136);
		// calendario.setMaxSelectableDate(2020);
		add(calendario);

		cmbProveedor = new JComboBox();
		cmbProveedor.setModel(cargaProveedor());
		cmbProveedor.setBounds(90, 325, 188, 22);
		add(cmbProveedor);

		cmbEmpleados = new JComboBox();
		cmbEmpleados.setModel(cargaPersonal());
		cmbEmpleados.setBounds(90, 221, 188, 22);
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

	public void insertCompra(String fecha, String nif, String dniP, int precioTotal) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into compra (fecha, id_proveedor, id_personal, precioCompra) values ('" + fecha
					+ "', (select id_proveedor from proveedores where nif='" + nif
					+ "'), (select id_personal from personal where dni='" + dniP + "'), "+precioTotal+")");
			valid = true;
			conexion.close();
		} catch (SQLException e) {
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
			ResultSet registro = consulta.executeQuery("Select nombre from productos where tipo='simple'");
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

	public DefaultComboBoxModel cargaProveedor() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Proveedores-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre, nif from proveedores");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("nif"));
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
			for (Compras c : compras) {
				modeloTabla.addRow(new Object[] { c.getProducto(), c.getUnidades() });
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
		if (unidades != -1 || unidades > 0) {
			Compras c = new Compras();
			c.setUnidades(unidades);
			c.setProducto(comboBox.getSelectedItem().toString());
			c.setPrecio(gestor.consultaPrecioCompra(c.getProducto()));
			boolean validar = false;
			for (int x = 0; x < compras.size(); x++) {
				if (compras.get(x).getProducto().compareTo(c.getProducto()) == 0) {
					compras.get(x).setUnidades(compras.get(x).getUnidades() + c.getUnidades());
					validar = true;
				}
			}
			if (c.getProducto().compareTo("-Productos-") != 0 && validar == false) {
				compras.add(c);
			}
		}

	}

	public Compras pideDatosCompra() {
		Compras c = new Compras();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		c.setNifProveedor(cmbProveedor.getSelectedItem().toString());
		c.setDniPersonal(cmbEmpleados.getSelectedItem().toString());
		c.setFechaTotal(dFormat.format(calendario.getDate()));

		return c;
	}

	public void insertProductosCompras() {
		for (int x = 0; x < compras.size(); x++) {
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

				Statement consulta = conexion.createStatement();
				consulta.executeUpdate("insert into productos_compra (id_producto, id_compra, unidades) values "
						+ "((select id_producto from productos where nombre='" + compras.get(x).getProducto() + "'), "
						+ recogerFactura() + "," + compras.get(x).getUnidades() + ")");
				conexion.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int recogerFactura() {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select factura from compra order by factura desc limit 1");

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

	public void sumarStock(int x) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update productos set stock = " + recogerStock(x)+"+"+compras.get(x).getUnidades()
					+ " where nombre = '" + compras.get(x).getProducto() + "'");


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
			ResultSet registro = consulta.executeQuery("select stock from productos where nombre='" + compras.get(x).getProducto()+"'");


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
