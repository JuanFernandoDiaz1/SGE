package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import modelo.Escandallo;

public class InsertarEscandallo extends JPanel {
	private JTable tableProductos;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JComboBox comboBox;
	private JComboBox cmbProducto;
	private JTextField txtUnidades;
	GestionBBDD gest = new GestionBBDD();
	ArrayList<Escandallo> escandallos = new ArrayList<>();
	private boolean valid = false;
	/**
	 * Create the panel.
	 */
	public InsertarEscandallo() {
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
		txtUnidades.setBounds(344, 44, 86, 20);
		add(txtUnidades);
		txtUnidades.setColumns(10);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableProductos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar de la cesta", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					escandallos.remove(tableProductos.getSelectedRow());
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(170, 75, 89, 23);
		add(btnEliminar);

		JLabel lblUd = new JLabel("Unidades: ");
		lblUd.setBounds(236, 47, 75, 14);
		add(lblUd);
		btnCesta.setBounds(460, 75, 89, 23);
		add(btnCesta);
		
		cmbProducto = new JComboBox();
		cmbProducto.setModel(cargaProductos());
		cmbProducto.setBounds(170, 364, 144, 20);
		add(cmbProducto);
		
		JButton btnInsertNuevoP = new JButton("Nuevo Producto");
		btnInsertNuevoP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductosVista prod = new ProductosVista();
				nuevoPanel(prod);
			}
		});
		btnInsertNuevoP.setBounds(405, 363, 144, 23);
		add(btnInsertNuevoP);

		comboBox = new JComboBox();
		comboBox.setModel(cargaMateriales());
		comboBox.setBounds(286, 75, 144, 22);
		add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 108, 379, 233);
		add(scrollPane);

		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);
		modeloTabla.setColumnIdentifiers(new Object[] { "Material", "Unidades" });
		tableProductos.setModel(modeloTabla);
		modeloTabla.setRowCount(0);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (escandallos.size() < 1) {
					JOptionPane.showMessageDialog(null, "Selecciona los materiales que componen el escandallo", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if(cmbProducto.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Selecciona producto resultante del escandallo", "Error",
							JOptionPane.WARNING_MESSAGE);
				}else {
					insertEscandallo();
					if (valid == true) {
						insertMaterialesEscandallo();
						ArrayList<Escandallo> escandallos = new ArrayList<>();
						escandallos = gest.consultaVerEscandallo(recogerEscandallo());
						for (int i = 0; i < escandallos.size(); i++) {
							gest.restarStock(escandallos.get(i).getProducto(), escandallos.get(i).getUnidades());
						}
					}
					JOptionPane.showMessageDialog(null, "Escandallo Añadido");
					Escandallos cv = new Escandallos();
					nuevoPanel(cv);
				}
			}
		});
		btnInsert.setBounds(316, 411, 89, 23);
		add(btnInsert);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	}
	
	public void insertarNuevoProducto() {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into productos (id_producto) values ()");
			valid = true;
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertEscandallo() {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into escandallo (id_producto) values ((Select id_producto from productos where nombre='"+cmbProducto.getSelectedItem().toString()+"'))");
			valid = true;
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	public DefaultComboBoxModel cargaMateriales() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Materiales-");
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
			for (Escandallo c : escandallos) {
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
			Escandallo c = new Escandallo();
			c.setUnidades(unidades);
			c.setProducto(comboBox.getSelectedItem().toString());
			boolean validar = false;
			for (int x = 0; x < escandallos.size(); x++) {
				if (escandallos.get(x).getProducto().compareTo(c.getProducto()) == 0) {
					escandallos.get(x).setUnidades(escandallos.get(x).getUnidades() + c.getUnidades());
					validar = true;
				}
			}
			if (c.getProducto().compareTo("-Materiales-") != 0 && validar == false) {
				escandallos.add(c);
			}
		}

	}


	public void insertMaterialesEscandallo() {
		for (int x = 0; x < escandallos.size(); x++) {
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

				Statement consulta = conexion.createStatement();
				consulta.executeUpdate("insert into escandallos_materiales (id_escandallo, id_material, unidadesMaterial) values("+recogerEscandallo()+
						", (Select id_producto from productos where nombre = '"+tableProductos.getValueAt(x, 0)+"'), "+tableProductos.getValueAt(x, 1)+")");
				conexion.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public int recogerEscandallo() {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select id_escandallo from escandallo order by id_escandallo desc limit 1");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt("id_escandallo");
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



	
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
	}
	
	public DefaultComboBoxModel cargaProductos() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Productos-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre from productos where tipo='compuesto'");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("nombre"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}

}
