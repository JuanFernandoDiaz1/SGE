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

import modelo.Compras;


public class InsertarCompras extends JPanel{
	private JTextField txtFactura;
	private JTable tableProductos;
	private JComboBox cmbEmpleados;
	private JComboBox cmbProveedor;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JComboBox comboBox;
	private JTextField txtUnidades;
	ArrayList<Compras> compras = new ArrayList<>();
	private JCalendar calendario;
	private boolean valid = false;
	public InsertarCompras() {
		
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

	JLabel lblFactura = new JLabel("Factura: ");
	lblFactura.setBounds(28, 79, 61, 14);
	add(lblFactura);

	JButton btnInsert = new JButton("Insertar");
	btnInsert.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Compras c = new Compras();
			c=pideDatosCompra();
			if(c.getFactura()<=0) {
				JOptionPane.showMessageDialog(null, "introduce una factura valida", "Error", JOptionPane.WARNING_MESSAGE);
			}else if(cmbProveedor.getSelectedIndex()==0||cmbEmpleados.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "introduce una factura valida", "Error", JOptionPane.WARNING_MESSAGE);
			}else if(compras.size()<1){
				JOptionPane.showMessageDialog(null, "Selecciona productos vendidos", "Error", JOptionPane.WARNING_MESSAGE);
			}else {
				insertCompra(c.getFactura(), c.getFechaTotal(), c.getDniProveedor(), c.getDniPersonal());
				if(valid==true) {
					insertProductosCompras();
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

	cmbProveedor = new JComboBox();
	cmbProveedor.setModel(cargaProveedor());
	cmbProveedor.setBounds(90, 325, 188, 22);
	add(cmbProveedor);

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

public void insertCompra(int factura, String fecha, String nif, String dniP) {
	try {
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

		Statement consulta = conexion.createStatement();
		consulta.executeUpdate("insert into compras (factura, fecha, id_proveedor, id_personal) values (" + factura + ", '"
				+ fecha + "',  (select id_proveedor from proveedores where nif='"+nif + "'), (select id_personal from personal where dni='" + dniP + "'))");
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
	int unidades = 0;
	try {
		unidades = Integer.parseInt(txtUnidades.getText().toString());
	} catch (NumberFormatException e) {
		unidades = -1;
	}
	if (unidades != -1 || unidades>0) {
		Compras c = new Compras();
		c.setUnidades(unidades);
		c.setProducto(comboBox.getSelectedItem().toString());
		boolean validar=false;
		for(int x = 0; x<compras.size();x++) {
			if(compras.get(x).getProducto().compareTo(c.getProducto())==0) {
				compras.get(x).setUnidades(compras.get(x).getUnidades()+c.getUnidades());
				validar=true;
			}
		}
		if(c.getProducto().compareTo("-Productos-")!=0&&validar==false) {
			compras.add(c);
		}
	}

}

public Compras pideDatosCompra() {
	Compras c = new Compras();
	SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
	c.setProveedor(cmbProveedor.getSelectedItem().toString());
	c.setDniPersonal(cmbEmpleados.getSelectedItem().toString());
	c.setFechaTotal(dFormat.format(calendario.getDate()));
	
	try {
		c.setFactura(Integer.parseInt(txtFactura.getText()));
	} catch (NumberFormatException e) {
		c.setFactura(-1);
	}
	
	return c;
}

public void insertProductosCompras() {
	for(int x=0; x<compras.size();x++) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into productos_compras (id_producto, id_compra, unidades) values "
					+ "((select id_producto from productos where nombre='"+compras.get(x).getProducto()+"'), "
							+ "(select id_compras from compras where factura="+txtFactura.getText()+"),"+compras.get(x).getUnidades()+")");
			conexion.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

}
