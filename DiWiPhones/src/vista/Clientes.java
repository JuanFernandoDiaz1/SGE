package vista;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.Cliente;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Clientes extends JPanel {
	private JTable tableClientes;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtTelefono;

	/**
	 * Create the panel.
	 */
	public Clientes() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionBBDD gestor = new GestionBBDD();
				Cliente cli = new Cliente();
				cli = pideDatosCliente();
				if (cli.getNombre().compareTo("") == 0 || cli.getDni().compareTo("") == 0
						|| cli.getDireccion().compareTo("") == 0 || cli.getEmail().compareTo("") == 0) {
					JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (cli.getDni().length() < 8) {
					JOptionPane.showMessageDialog(null, "Introduce un DNI valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (cli.getTelefono() < 100000000 || cli.getTelefono() > 999999999) {
					JOptionPane.showMessageDialog(null, "Introduce un telefono valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {

					int telefono = Integer.parseInt(txtTelefono.getText());
					gestor.insertCliente(txtNombre.getText(), txtDni.getText(), txtDireccion.getText(),
							txtEmail.getText());
					int id = gestor.obtenerIdCliente();
					gestor.insertTel("cliente", telefono, id);
					cargarTabla();
					reemplazar();
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
					modificarCliente();
					cargarTabla();
				}
			}
		});
		btnModificar.setBounds(315, 381, 89, 23);
		add(btnModificar);

		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableClientes.getSelectedRow() != -1) {
					txtNombre.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString());
					txtDni.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 1).toString());
					txtDireccion.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 2).toString());
					txtEmail.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 3).toString());
					txtTelefono.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 4).toString());
				} else {
					txtNombre.setText("");
					txtDni.setText("");
					txtDireccion.setText("");
					txtEmail.setText("");
					txtTelefono.setText("");
				}
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar el cliente?");
				if (JOptionPane.OK_OPTION == valor) {
					eliminarCliente();
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(539, 286, 86, 20);
		add(txtTelefono);

		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(367, 286, 86, 20);
		add(txtDni);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(367, 325, 126, 20);
		add(txtEmail);

		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(158, 323, 117, 20);
		add(txtDireccion);

		txtNombre = new JTextField();
		txtNombre.setToolTipText("");
		txtNombre.setColumns(10);
		txtNombre.setBounds(158, 286, 117, 20);
		add(txtNombre);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 210);
		add(scrollPane);

		tableClientes = new JTable();
		scrollPane.setViewportView(tableClientes);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "DNI", "Direccion", "Email", "telefono" });
		tableClientes.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(94, 288, 65, 17);
		add(lblNombre);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(322, 286, 32, 20);
		add(lblDni);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(322, 325, 40, 20);
		add(lblEmail);

		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(94, 326, 63, 14);
		add(lblDireccion);

		JLabel lblTel = new JLabel("Telefono:");
		lblTel.setBounds(474, 289, 55, 14);
		add(lblTel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

		ListSelectionModel model = tableClientes.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (tableClientes.getSelectedRow() != -1) {
					txtNombre.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString());
					txtDni.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 1).toString());
					txtDireccion.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 2).toString());
					txtEmail.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 3).toString());
					txtTelefono.setText(tableClientes.getValueAt(tableClientes.getSelectedRow(), 4).toString());
				} else {
					txtNombre.setText("");
					txtDni.setText("");
					txtDireccion.setText("");
					txtEmail.setText("");
					txtTelefono.setText("");
				}
			}
		});

	}

	public void cargarTabla() {
		GestionBBDD gestor = new GestionBBDD();
		modeloTabla.setRowCount(0);
		for (Cliente c : gestor.consulta()) {
			modeloTabla.addRow(
					new Object[] { c.getNombre(), c.getDni(), c.getDireccion(), c.getEmail(), c.getTelefono() });
		}
	}

	public void reemplazar() {
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtDni.setText("");
	}

	public void modificarCliente() {
		Cliente cli = new Cliente();
		cli = pideDatosCliente();

		if (tableClientes.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un cliente para modificar", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (cli.getNombre().compareTo("") == 0 || cli.getDni().compareTo("") == 0
				|| cli.getDireccion().compareTo("") == 0 || cli.getEmail().compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
		} else if (cli.getTelefono() < 100000000 || cli.getTelefono() > 999999999) {
			JOptionPane.showMessageDialog(null, "Introduce un telefono valido", "Error", JOptionPane.WARNING_MESSAGE);
		} else {
			GestionBBDD gest = new GestionBBDD();
			int id = gest.obtenerIdGeneral("cliente", "clientes", "dni", tableClientes.getValueAt(tableClientes.getSelectedRow(), 1).toString());
			if (Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 4).toString())==0) {
				gest.insertTel("cliente", cli.getTelefono(), id);
				gest.modificarCliente(cli, tableClientes);
				cargarTabla();
				reemplazar();
			} else {
				gest.modificarTelefono(cli.getTelefono(), "cliente", "dni", "clientes", tableClientes);
				gest.modificarCliente(cli, tableClientes);
				cargarTabla();
				reemplazar();
			}

		}
	}

	public void eliminarCliente() {
		if (tableClientes.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			GestionBBDD gest = new GestionBBDD();
			gest.borrarTel("cliente", "dni", "clientes", tableClientes);
			
			gest.borrarCliente(tableClientes);
			cargarTabla();
		}
	}

	public Cliente pideDatosCliente() {
		Cliente cli = new Cliente();
		cli.setNombre(txtNombre.getText());
		cli.setDni(txtDni.getText());
		cli.setDireccion(txtDireccion.getText());
		cli.setEmail(txtEmail.getText());
		try {
			cli.setTelefono(Integer.parseInt(txtTelefono.getText()));
		} catch (NumberFormatException e) {
			cli.setTelefono(-1);
		}
		return cli;
	}

}
