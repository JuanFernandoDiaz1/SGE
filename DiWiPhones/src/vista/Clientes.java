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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

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
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);

		txtDni = new JTextField();
		txtDni.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);

		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setToolTipText("");
		txtNombre.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();

		tableClientes = new JTable();
		scrollPane.setViewportView(tableClientes);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "DNI", "Direccion", "Email", "telefono" });
		tableClientes.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

		JLabel lblNombre = new JLabel("Nombre: ");

		JLabel lblDni = new JLabel("DNI:");

		JLabel lblEmail = new JLabel("Email:");

		JLabel lblDireccion = new JLabel("Direccion:");

		JLabel lblTel = new JLabel("Telefono:");

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(322)
					.addComponent(lblDni, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(94)
					.addComponent(lblDireccion, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(210)
					.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(494)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(315)
					.addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(84)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
					.addGap(90))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(158)
					.addComponent(txtDireccion, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(429)
					.addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(367)
					.addComponent(txtDni, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(270, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(158)
					.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(448, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(539)
					.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(322)
					.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(199)
					.addComponent(btnInsert, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(474)
					.addComponent(lblTel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(94)
					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
				.addComponent(lblFondo, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(286)
					.addComponent(lblDni, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblDireccion))
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(394)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(381)
					.addComponent(btnModificar))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(323)
					.addComponent(txtDireccion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(381)
					.addComponent(btnEliminar))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(286)
					.addComponent(txtDni, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(286)
					.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(286)
					.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(325)
					.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(381)
					.addComponent(btnInsert))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(289)
					.addComponent(lblTel))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(288)
					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addComponent(lblFondo, GroupLayout.PREFERRED_SIZE, 507, GroupLayout.PREFERRED_SIZE)
		);
		setLayout(groupLayout);

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
			int id = gest.obtenerIdGeneral("cliente", "clientes", "dni",
					tableClientes.getValueAt(tableClientes.getSelectedRow(), 1).toString());
			if (Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 4).toString()) == 0) {
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
			int contador = gest.contarVentas("venta", "cliente", "clientes", "dni", tableClientes);
			System.out.println(contador);
			if(contador==0) {
				gest.borrarTel("cliente", "dni", "clientes", tableClientes);
				gest.borrarCliente(tableClientes);
				cargarTabla();
			}else {
				JOptionPane.showMessageDialog(null, "El cliente tiene ventas no se puede eliminar", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
			
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
