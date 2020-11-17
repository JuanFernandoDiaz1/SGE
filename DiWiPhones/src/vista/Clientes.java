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
				if (txtNombre.getText().compareTo("") == 0 || txtDni.getText().compareTo("") == 0
						|| txtEmail.getText().compareTo("") == 0 || txtDireccion.getText().compareTo("") == 0) {
					JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (txtTelefono.getText().compareTo("") == 0
						|| Integer.parseInt(txtTelefono.getText()) < 100000000
						|| Integer.parseInt(txtTelefono.getText()) > 999999999) {
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
		btnInsert.setBounds(245, 381, 89, 23);
		add(btnInsert);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(364, 381, 89, 23);
		add(btnModificar);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWiPhones\\img\\actualizado.png"));
		btnNewButton.setBounds(22, 11, 40, 35);
		add(btnNewButton);

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
		lblLogo.setIcon(new ImageIcon("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWiPhones\\img\\diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
	}

	public void cargarTabla() {
		GestionBBDD gestor = new GestionBBDD();
		modeloTabla.setRowCount(0);
		for (Cliente c : gestor.consulta()) {
			modeloTabla.addRow(new Object[] { c.getNombre(), c.getDni(), c.getDireccion(), c.getEmail(), c.getTelefono() });
		}
	}
	
	public void reemplazar() {
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtDni.setText("");
	}
}
