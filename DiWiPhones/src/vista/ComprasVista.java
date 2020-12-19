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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.Compras;
import modelo.Venta;

public class ComprasVista extends JPanel {
	private JTable tableCompras;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();
	/**
	 * Create the panel.
	 */
	public ComprasVista() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertarCompras ic = new InsertarCompras();
				nuevoPanel(ic);
			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar el modelo?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableCompras.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona una venta para modificar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						ModificarCompras mv = new ModificarCompras(); 
						mv.setCompras(pideDatos());
						nuevoPanel(mv);
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
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar la compra?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableCompras.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona una compra para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						restarStock(eliminarCompra());
						eliminarProdCompras();
						eliminarCompras();
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

		tableCompras = new JTable();
		scrollPane.setViewportView(tableCompras);

		modeloTabla.setColumnIdentifiers(new Object[] { "Factura", "Fecha", "Proveedor", "NIF Proveedor", "Personal", "DNI Personal","Precio Compra"});
		tableCompras.setModel(modeloTabla);
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
		for (Compras v : gestor.consultaCompras()) {
			modeloTabla.addRow(
					new Object[] { v.getFactura(), v.getFechaTotal(), v.getProveedor(), v.getNifProveedor(), v.getPersonal(), v.getDniPersonal(),v.getPrecioTotal() });
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
	
	public void eliminarCompras() {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			consulta.executeUpdate("delete from compra where factura ="
					+ tableCompras.getValueAt(tableCompras.getSelectedRow(), 0).toString());			

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

			consulta.executeUpdate("delete from productos_compra where id_compra = "
					+ tableCompras.getValueAt(tableCompras.getSelectedRow(), 0).toString());
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Compras> eliminarCompra() {
		ArrayList<Compras> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery(
					"SELECT Nombre, Unidades FROM productos_compra inner join productos on productos_compra.ID_Producto = productos.ID_Producto WHERE ID_compra="
							+ tableCompras.getValueAt(tableCompras.getSelectedRow(), 0).toString());

			// si existe lo que estamos buscando
			while (registro.next()) {
				Compras compra = new Compras();

				compra.setProducto(registro.getString("Nombre"));
				compra.setUnidades(registro.getInt("Unidades"));
				// añadimos modelos al arrayList
				productos.add(compra);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return productos;
	}
	public void restarStock(ArrayList<Compras> compra) {
			try {
			
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			for (int i = 0; i < compra.size(); i++) {
					
				int calculo = recogerStock(i,compra) - compra.get(i).getUnidades();
				consulta.executeUpdate("update productos set stock = " + calculo + " where nombre = '"
					+ compra.get(i).getProducto() + "'");

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int recogerStock(int x, ArrayList<Compras> compra) {
		int stock = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			ResultSet registro = consulta
					.executeQuery("select stock from productos where nombre='" + compra.get(x).getProducto() + "'");

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
	
	public Compras pideDatos() {
		Compras compra = new Compras();
		compra.setFactura(Integer.parseInt(tableCompras.getValueAt(tableCompras.getSelectedRow(), 0).toString()));
		compra.setFechaTotal(tableCompras.getValueAt(tableCompras.getSelectedRow(), 1).toString());
		compra.setProveedor(tableCompras.getValueAt(tableCompras.getSelectedRow(), 2).toString());
		compra.setNifProveedor(tableCompras.getValueAt(tableCompras.getSelectedRow(), 3).toString());
		compra.setPersonal(tableCompras.getValueAt(tableCompras.getSelectedRow(), 4).toString());
		compra.setDniPersonal(tableCompras.getValueAt(tableCompras.getSelectedRow(), 5).toString());
		return compra;
	}
}
