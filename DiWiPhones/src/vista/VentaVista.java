package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.Compras;
import modelo.Fecha;
import modelo.Productos;
import modelo.Venta;

public class VentaVista extends JPanel {
	private JTable tableVentas;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();

	/**
	 * Create the panel.
	 */
	public VentaVista() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertarVentas iv = new InsertarVentas();
				nuevoPanel(iv);
			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar el modelo?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableVentas.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona una venta para modificar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						ModificarVentas mi = new ModificarVentas(); 
						mi.setVenta(pideDatos());
						nuevoPanel(mi);
						cargarTabla();
					}
				}
			}
		});
		btnModificar.setBounds(315, 381, 89, 23);
		add(btnModificar);

		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar el cliente?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableVentas.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona una venta para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						sumarStock(eliminarVenta());
						eliminarProdVentas();
						eliminarVentas();
						cargarTabla();
					}
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 311);
		add(scrollPane);

		tableVentas = new JTable();
		scrollPane.setViewportView(tableVentas);

		modeloTabla.setColumnIdentifiers(new Object[] { "Factura", "Fecha", "Cliente", "DNI Cliente", "Personal", "DNI Personal","Precio Venta"});
		tableVentas.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	
	}
	

	public void cargarTabla() {
		modeloTabla.setRowCount(0);
		for (Venta v : gestor.consultaVenta()) {
			modeloTabla.addRow(
					new Object[] { v.getFactura(), v.getFechaTotal(), v.getCliente(), v.getDniCliente(), v.getPersonal(), v.getDniPersonal(),v.getPrecioTotal() });
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
	
	public void eliminarVentas() {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from venta where factura ="
					+ tableVentas.getValueAt(tableVentas.getSelectedRow(), 0).toString());
			
			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Venta Eliminada correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe la venta", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void eliminarProdVentas() {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			consulta.executeUpdate("delete from productos_ventas where id_venta = "
					+ tableVentas.getValueAt(tableVentas.getSelectedRow(), 0).toString());
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Venta> eliminarVenta() {
		ArrayList<Venta> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery(
					"SELECT Nombre, Unidades FROM productos_ventas inner join productos on productos_ventas.ID_Producto = productos.ID_Producto WHERE ID_venta="
							+ tableVentas.getValueAt(tableVentas.getSelectedRow(), 0).toString());

			// si existe lo que estamos buscando
			while (registro.next()) {
				Venta venta = new Venta();

				venta.setProducto(registro.getString("Nombre"));
				venta.setUnidades(registro.getInt("Unidades"));
				// añadimos modelos al arrayList
				productos.add(venta);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return productos;
	}
	public void sumarStock(ArrayList<Venta> venta) {
			try {
			
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			for (int i = 0; i < venta.size(); i++) {
					
				int calculo = recogerStock(i,venta) + venta.get(i).getUnidades();
				consulta.executeUpdate("update productos set stock = " + calculo + " where nombre = '"
					+ venta.get(i).getProducto() + "'");

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int recogerStock(int x, ArrayList<Venta> venta) {
		int stock = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			ResultSet registro = consulta
					.executeQuery("select stock from productos where nombre='" + venta.get(x).getProducto() + "'");

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
	public Venta pideDatos() {
		Venta venta = new Venta();
		venta.setFactura(Integer.parseInt(tableVentas.getValueAt(tableVentas.getSelectedRow(), 0).toString()));
		venta.setFechaTotal(tableVentas.getValueAt(tableVentas.getSelectedRow(), 1).toString());
		venta.setCliente(tableVentas.getValueAt(tableVentas.getSelectedRow(), 2).toString());
		venta.setDniCliente(tableVentas.getValueAt(tableVentas.getSelectedRow(), 3).toString());
		venta.setPersonal(tableVentas.getValueAt(tableVentas.getSelectedRow(), 4).toString());
		venta.setDniPersonal(tableVentas.getValueAt(tableVentas.getSelectedRow(), 5).toString());
		return venta;
	}
}

