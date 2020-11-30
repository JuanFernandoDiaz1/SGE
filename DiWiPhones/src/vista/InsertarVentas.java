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

import modelo.Venta;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertarVentas extends JPanel {
	private JTextField txtFactura;
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

		txtFactura = new JTextField();
		txtFactura.setColumns(10);
		txtFactura.setBounds(90, 76, 86, 20);
		add(txtFactura);

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

		JLabel lblFactura = new JLabel("Factura: ");
		lblFactura.setBounds(28, 79, 61, 14);
		add(lblFactura);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Venta v = new Venta();
				v=pideDatosVenta();
				if(v.getFactura()<=0) {
					JOptionPane.showMessageDialog(null, "introduce una factura valida", "Error", JOptionPane.WARNING_MESSAGE);
				}else if(cmbClientes.getSelectedIndex()==0||cmbEmpleados.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "introduce una factura valida", "Error", JOptionPane.WARNING_MESSAGE);
				}else if(ventas.size()<1){
					JOptionPane.showMessageDialog(null, "Selecciona productos vendidos", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					insertVenta(v.getFactura(), v.getFechaTotal(), v.getDniCliente(), v.getDniPersonal());
					if(valid==true) {
						insertProductosVentas();
					}
				}
			}
		});
		btnInsert.setBounds(315, 394, 89, 23);
		add(btnInsert);

		calendario = new JCalendar();
		calendario.setBounds(90, 119, 230, 136);
		//calendario.setMaxSelectableDate(2020);
		add(calendario);

		cmbClientes = new JComboBox();
		cmbClientes.setModel(cargaClientes());
		cmbClientes.setBounds(90, 325, 188, 22);
		add(cmbClientes);

		cmbEmpleados = new JComboBox();
		cmbEmpleados.setModel(cargaPersonal());
		cmbEmpleados.setBounds(90, 280, 188, 22);
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

	public void insertVenta(int factura, String fecha, String dniC, String dniP) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into ventas (factura, fecha, id_cliente, id_personal) values (" + factura + ", '"
					+ fecha + "',  (select id_cliente from clientes where dni='"+dniC + "'), (select id_personal from personal where dni='" + dniP + "'))");
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
		
		try {
			v.setFactura(Integer.parseInt(txtFactura.getText()));
		} catch (NumberFormatException e) {
			v.setFactura(-1);
		}
		
		return v;
	}
	
	public void insertProductosVentas() {
		for(int x=0; x<ventas.size();x++) {
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

				Statement consulta = conexion.createStatement();
				consulta.executeUpdate("insert into productos_ventas (id_producto, id_venta, unidades) values "
						+ "((select id_producto from productos where nombre='"+ventas.get(x).getProducto()+"'), "
								+ "(select id_ventas from ventas where factura="+txtFactura.getText()+"),"+ventas.get(x).getUnidades()+")");
				conexion.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
