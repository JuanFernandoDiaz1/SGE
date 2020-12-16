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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.Compras;
import modelo.Venta;

public class ModificarCompras extends JPanel {
	private Compras compra = new Compras();
	private JTable tableProductos;
	private JComboBox cmbProveedores;
	private JComboBox cmbClientes;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JComboBox comboBox;
	private JTextField txtUnidades;
	ArrayList<Compras> compras = new ArrayList<>();
	ArrayList<Compras> comprasAux = new ArrayList<>();
	private JCalendar calendario;
	private boolean valid = false;
	private int cont = 0;

	/**
	 * Create the panel.
	 */
	public ModificarCompras() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnCesta = new JButton("A\u00F1adir");
		btnCesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cont == 0) {
					for (int x = 0; x < tableProductos.getRowCount(); x++) {
						Compras c1 = new Compras();
						c1.setProducto(tableProductos.getValueAt(x, 0).toString());
						c1.setUnidades(Integer.parseInt(tableProductos.getValueAt(x, 1).toString()));
						comprasAux.add(c1);
						cont++;
					}
				}

				int unidades = 0;
				try {
					unidades = Integer.parseInt(txtUnidades.getText().toString());
				} catch (NumberFormatException z) {
					unidades = -1;
				}
				if (unidades == -1 || unidades <= 0) {
					JOptionPane.showMessageDialog(null, "Introduce unidades validas", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecciona un producto", "Error", JOptionPane.WARNING_MESSAGE);
				} else {
					cargar();
					cargarTabla();
				}
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

		JButton btnInsert = new JButton("Modificar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Compras v = new Compras();
				v = pideDatosCompra();
				if (cmbClientes.getSelectedIndex() == 0 || cmbProveedores.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "introduce una cliente valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (compras.size() < 1) {
					JOptionPane.showMessageDialog(null, "Selecciona productos vendidos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					eliminarProdCompras();
					eliminarCompras();
					insertCompra(v.getFechaTotal(), v.getNifProveedor(), v.getDniPersonal());
					if (valid == true) {
						insertProductosCompras();
					}
					for (int x = 0; x < comprasAux.size(); x++) {
						restarStock(x);
					}
					for (int x = 0; x < compras.size(); x++) {
						sumarStock(x);
					}
					JOptionPane.showMessageDialog(null, "Compra Modificada");
				}
			}
		});
		btnInsert.setBounds(315, 394, 89, 23);
		add(btnInsert);

		calendario = new JCalendar();
		calendario.setBounds(90, 44, 230, 136);
		// calendario.setMaxSelectableDate(2020);
		add(calendario);

		cmbClientes = new JComboBox();
		cmbClientes.setModel(cargaProveedores());
		cmbClientes.setBounds(90, 325, 188, 22);
		add(cmbClientes);

		cmbProveedores = new JComboBox();
		cmbProveedores.setModel(cargaPersonal());
		cmbProveedores.setBounds(90, 220, 188, 22);
		add(cmbProveedores);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	}

	public void insertCompra(String fecha, String nif, String dniP) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into compra (fecha, id_proveedor, id_personal) values ('" + fecha
					+ "',  (select id_proveedor from proveedores where nif='" + nif
					+ "'), (select id_personal from personal where dni='" + dniP + "'))");
			valid = true;
			conexion.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Factura ya registrada inserte otra", "Error",
					JOptionPane.WARNING_MESSAGE);
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

	public DefaultComboBoxModel cargaProveedores() {
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

		modeloTabla.setRowCount(0);
		for (Compras c : compras) {
			modeloTabla.addRow(new Object[] { c.getProducto(), c.getUnidades() });
		}

	}

	public void cargar() {
		int unidades = 0;
		try {
			unidades = Integer.parseInt(txtUnidades.getText().toString());
		} catch (NumberFormatException e) {
			unidades = -1;
		}
		if (unidades != -1 || unidades > 0) {
			Compras e = new Compras();
			e.setUnidades(unidades);
			e.setProducto(comboBox.getSelectedItem().toString());
			boolean validar = false;
			for (int x = 0; x < compras.size(); x++) {
				if (compras.get(x).getProducto().compareTo(e.getProducto()) == 0) {
					compras.get(x).setUnidades(compras.get(x).getUnidades() + e.getUnidades());
					validar = true;
				}
			}
			if (e.getProducto().compareTo("-Productos-") != 0 && validar == false) {
				compras.add(e);
			}
		}

	}

	public Compras pideDatosCompra() {
		Compras c = new Compras();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		c.setNifProveedor(cmbClientes.getSelectedItem().toString());
		c.setDniPersonal(cmbProveedores.getSelectedItem().toString());
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
			ResultSet registro = consulta.executeQuery("select factura from compra order by id_proveedor desc limit 1");

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

	public Compras getCompras() {
		return compra;
	}

	public void setCompras(Compras compra) {
		this.compra = compra;
		cargarFactura();
	}

	public void eliminarCompras() {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from compra where factura =" + compra.getFactura());

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void eliminarProdCompras() {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			consulta.executeUpdate("delete from productos_compra where id_compra = " + compra.getFactura());
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cargarFactura() {

		cmbClientes.setSelectedItem(compra.getNifProveedor());
		cmbProveedores.setSelectedItem(compra.getDniPersonal());
		GestionBBDD gestion = new GestionBBDD();
		modeloTabla.setRowCount(0);
		for (Compras c : gestion.listaCompra2(compra.getFactura())) {
			compras.add(c);
		}
		modeloTabla.setRowCount(0);
		for (Compras c : compras) {
			modeloTabla.addRow(new Object[] { c.getProducto(), c.getUnidades() });
		}

	}

	public void sumarStock(int x) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			int calculo = recogerStock(x) + compras.get(x).getUnidades();
			int valor = consulta.executeUpdate("update productos set stock = " + calculo + " where nombre = '"
					+ compras.get(x).getProducto() + "'");

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void restarStock(int x) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			System.out.println(recogerStock(x));
			int calculo = recogerStock(x) - comprasAux.get(x).getUnidades();
			int valor = consulta.executeUpdate("update productos set stock = " + calculo + " where nombre = '"
					+ comprasAux.get(x).getProducto() + "'");

			System.out.println(calculo);
			System.out.println(comprasAux.get(x).getUnidades());
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int recogerStock(int x) {
		int stock = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta
					.executeQuery("select stock from productos where nombre='" + compras.get(x).getProducto() + "'");

			// si existe lo que estamos buscando
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

}
