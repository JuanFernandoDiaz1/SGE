package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
					
					cargarTabla();
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

		modeloTabla.setColumnIdentifiers(new Object[] { "Factura", "Fecha", "Cliente", "DNI Cliente", "Personal", "DNI Personal"});
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
					new Object[] { v.getFactura(), v.getFechaTotal(), v.getCliente(), v.getDniCliente(), v.getPersonal(), v.getDniPersonal() });
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

			int valor = consulta.executeUpdate("delete from ventas where factura ="
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

			consulta.executeUpdate("delete from productos_ventas where id_venta = (select id_ventas from ventas where factura = "
					+ tableVentas.getValueAt(tableVentas.getSelectedRow(), 0).toString()+")");
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

