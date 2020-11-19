package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import modelo.Personal;

public class PersonalVista extends JPanel {
	private JTable tablePersonal;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	
	/**
	 * Create the panel.
	 */
	public PersonalVista() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionBBDD gestor = new GestionBBDD();
				Personal persona = new Personal();
				persona=pideDatosPersonal();
				if (persona.getNombre().compareTo("") == 0 || persona.getDni().compareTo("") == 0
						|| persona.getDireccion().compareTo("") == 0 || persona.getEmail().compareTo("") == 0) {
					JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if(persona.getDni().length()<8) {
					JOptionPane.showMessageDialog(null,
							"Introduce un DNI valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (persona.getTelefono() < 100000000
						|| persona.getTelefono() > 999999999) { 
					JOptionPane.showMessageDialog(null,
							"Introduce un telefono valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {

					int telefono = Integer.parseInt(txtTelefono.getText());
					gestor.insertPersonal(txtNombre.getText(), txtDni.getText(), txtDireccion.getText(),
							txtEmail.getText());
					int id = gestor.obtenerIdPersonal();
					gestor.insertTel("personal", telefono, id);
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
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar este empleado?");
				if (JOptionPane.OK_OPTION == valor) {
					modificarPersonal();
					cargarTabla();
				}
			}
		});
		btnModificar.setBounds(315, 381, 89, 23);
		add(btnModificar);
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**/
				cargarTabla();
			}
		});
		
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este empleado?");
				if (JOptionPane.OK_OPTION == valor) {
					eliminarPersonal();
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		//btnRefresh.setIcon(new ImageIcon("")); Icono Recarga
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

		tablePersonal = new JTable();
		scrollPane.setViewportView(tablePersonal);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "DNI", "Direccion", "Email", "telefono" });
		tablePersonal.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(94, 288, 65, 17);
		add(lblNombre);

		JLabel lblDni = new JLabel("DNI: ");
		lblDni.setBounds(322, 286, 32, 20);
		add(lblDni);

		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(322, 325, 40, 20);
		add(lblEmail);

		JLabel lblDireccion = new JLabel("Direccion: ");
		lblDireccion.setBounds(94, 326, 63, 14);
		add(lblDireccion);

		JLabel lblTel = new JLabel("Telefono: ");
		lblTel.setBounds(474, 289, 55, 14);
		add(lblTel);
		
		JLabel lblLogo = new JLabel("");
		//lblLogo.setIcon(new ImageIcon("")); icono logo 
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		ListSelectionModel model = tablePersonal.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(tablePersonal.getSelectedRow()!=-1) {
				txtNombre.setText(tablePersonal.getValueAt(tablePersonal.getSelectedRow(), 0).toString());
				txtDni.setText(tablePersonal.getValueAt(tablePersonal.getSelectedRow(), 1).toString());
				txtDireccion.setText(tablePersonal.getValueAt(tablePersonal.getSelectedRow(), 2).toString());
				txtEmail.setText(tablePersonal.getValueAt(tablePersonal.getSelectedRow(), 3).toString());
				txtTelefono.setText(tablePersonal.getValueAt(tablePersonal.getSelectedRow(), 4).toString());
			}else {
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
		for (Personal c : gestor.consultaPersonal()) {
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
	
	public void modificarPersonal() {
		Personal persona = new Personal();
		persona=pideDatosPersonal();
		
		if (tablePersonal.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un empleado para modificar", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (persona.getNombre().compareTo("") == 0 || persona.getDni().compareTo("") == 0
				|| persona.getDireccion().compareTo("") == 0 || persona.getEmail().compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (persona.getTelefono() < 100000000
				|| persona.getTelefono() > 999999999) { 
			JOptionPane.showMessageDialog(null,
					"Introduce un telefono valido", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			GestionBBDD gest = new GestionBBDD();
			gest.modificarTelefono(persona.getTelefono(), "personal", "dni", "personal", tablePersonal);
			gest.modificarPersonal(persona, tablePersonal);
			cargarTabla();
			reemplazar();
		}
	}
	public void eliminarPersonal() {
		if (tablePersonal.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un empleado para eliminar", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {
			GestionBBDD gest = new GestionBBDD();
			gest.borrarTel("personal", "dni", "personal", tablePersonal);
			gest.borrarPersonal(tablePersonal);
			cargarTabla();
		}
	}
	public Personal pideDatosPersonal() {
		Personal persona = new Personal();
		persona.setNombre(txtNombre.getText());
		persona.setDni(txtDni.getText());
		persona.setDireccion(txtDireccion.getText());
		persona.setEmail(txtEmail.getText());
		try {
			persona.setTelefono(Integer.parseInt(txtTelefono.getText()));
		} catch (NumberFormatException e) {
			persona.setTelefono(-1);
		}
		return persona;
	}
}
