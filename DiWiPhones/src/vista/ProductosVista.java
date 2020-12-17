package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import modelo.Cliente;
import modelo.Productos;
import modelo.Proveedor;

import javax.swing.JComboBox;

public class ProductosVista extends JPanel {
	private JTable tableProductos;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtStock;
	private JComboBox comboBox;
	GestionBBDD gestor = new GestionBBDD();
	private JTextField txtPrecioVenta;

	/**
	 * Create the panel.
	 */
	public ProductosVista() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Productos productos = recogerDatos();
				if (productos.getNombre().compareTo("") == 0 || productos.getDescripcion().compareTo("") == 0) {
					JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (productos.getPrecio() <= 0 || productos.getStock() <= 0) {
					JOptionPane.showMessageDialog(null, "Los campos Precio y Stock no pueden ser 0 o negativos",
							"Error", JOptionPane.WARNING_MESSAGE);
				}else if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecciona un proveedor valido", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					gestor.comprobarProductos(productos);
					cargarTabla();
				}

			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar el modelo?");
				if (JOptionPane.OK_OPTION == valor) {
					Productos productos = recogerDatos();
					if (productos.getNombre().compareTo("") == 0 || productos.getDescripcion().compareTo("") == 0) {
						JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
								JOptionPane.WARNING_MESSAGE);
					} else if (productos.getPrecio() <= 0 || productos.getStock() <= 0|| productos.getPrecioVenta() <= 0) {
						JOptionPane.showMessageDialog(null, "Los campos Precio y Stock no pueden ser 0 o negativos",
								"Error", JOptionPane.WARNING_MESSAGE);
					}else if (comboBox.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(null, "Selecciona un proveedor valido", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						gestor.modificarProducto(recogerDatos(), tableProductos);
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

				txtNombre.setText("");
				txtDescripcion.setText("");
				txtStock.setText("");
				txtPrecio.setText("");
				txtPrecioVenta.setText("");
				comboBox.setSelectedIndex(0);
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar el cliente?");
				if (JOptionPane.OK_OPTION == valor) {
					gestor.borrarProducto(tableProductos);
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(338, 286, 126, 20);
		add(txtDescripcion);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(141, 323, 117, 20);
		add(txtPrecio);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(338, 323, 126, 20);
		add(txtStock);

		txtNombre = new JTextField();
		txtNombre.setToolTipText("");
		txtNombre.setColumns(10);
		txtNombre.setBounds(141, 286, 117, 20);
		add(txtNombre);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 210);
		add(scrollPane);

		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "Descripcion", "Precio Compra","Precio Venta", "Stock", "Proveedor" });
		tableProductos.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(68, 288, 65, 17);
		add(lblNombre);

		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(268, 286, 82, 20);
		add(lblDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(268, 323, 40, 20);
		add(lblStock);

		JLabel lblPrecio = new JLabel("Precio Compra:");
		lblPrecio.setBounds(68, 326, 73, 14);
		add(lblPrecio);
		
		txtPrecioVenta = new JTextField();
		txtPrecioVenta.setColumns(10);
		txtPrecioVenta.setBounds(560, 323, 117, 20);
		add(txtPrecioVenta);
		
		JLabel lblPrecioVenta = new JLabel("Precio Venta:");
		lblPrecioVenta.setBounds(474, 326, 76, 14);
		add(lblPrecioVenta);

		comboBox = new JComboBox();
		comboBox.setBounds(516, 286, 126, 21);
		add(comboBox);
		comboBox.setModel(gestor.cargaProveedores());

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

		ListSelectionModel model = tableProductos.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (tableProductos.getSelectedRow() != -1) {
					txtNombre.setText(tableProductos.getValueAt(tableProductos.getSelectedRow(), 0).toString());
					txtDescripcion.setText(tableProductos.getValueAt(tableProductos.getSelectedRow(), 1).toString());
					txtPrecio.setText(tableProductos.getValueAt(tableProductos.getSelectedRow(), 2).toString());
					txtPrecioVenta.setText(tableProductos.getValueAt(tableProductos.getSelectedRow(), 3).toString());
					txtStock.setText(tableProductos.getValueAt(tableProductos.getSelectedRow(), 4).toString());
					comboBox.setSelectedItem(tableProductos.getValueAt(tableProductos.getSelectedRow(), 5).toString());
				}
			}
		});
	}

	public Productos recogerDatos() {
		Productos productos = new Productos();
		productos.setProveedor((String) comboBox.getSelectedItem());
		productos.setNombre(txtNombre.getText());
		productos.setDescripcion(txtDescripcion.getText());
		try {
			productos.setPrecio(Integer.parseInt(txtPrecio.getText()));
			productos.setStock(Integer.parseInt(txtStock.getText()));
			productos.setPrecioVenta(Integer.parseInt(txtPrecioVenta.getText()));
		}catch(NumberFormatException a) {
			JOptionPane.showMessageDialog(null, "Introduzca los campos correctamente");
		}
		
		return productos;
	}

	public void cargarTabla() {
		modeloTabla.setRowCount(0);
		for (Productos p : gestor.consultaProductos()) {
			modeloTabla.addRow(
					new Object[] { p.getNombre(), p.getDescripcion(), p.getPrecio(),p.getPrecioVenta(), p.getStock(), p.getProveedor() });
		}
	}
}
