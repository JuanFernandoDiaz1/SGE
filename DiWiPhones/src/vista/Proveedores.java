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
import modelo.Personal;
import modelo.Proveedor;

public class Proveedores extends JPanel {
	private JTable tableProveedor;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtNif;
	/**
	 * Create the panel.
	 */
	public Proveedores() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionBBDD gestor = new GestionBBDD();
				Proveedor proveedor = new Proveedor();
				proveedor=pideDatosProveedor();
				if (proveedor.getNif().compareTo("")==0||proveedor.getNombre().compareTo("") == 0 || proveedor.getDireccion().compareTo("") == 0 || proveedor.getEmail().compareTo("") == 0) {
					JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
							JOptionPane.WARNING_MESSAGE);
				
				} else if (proveedor.getTelefono() < 100000000
						|| proveedor.getTelefono() > 999999999) { 
					JOptionPane.showMessageDialog(null,
							"Introduce un telefono valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {

					int telefono = Integer.parseInt(txtTelefono.getText());
					gestor.insertProveedor(txtNombre.getText(), txtNif.getText(),txtDireccion.getText(),
							txtEmail.getText());
					int id = gestor.obtenerIdProveedor();
					gestor.insertTel("proveedor", telefono, id);
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
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar este Proveedor?");
				if (JOptionPane.OK_OPTION == valor) {
					modificarProveedor();
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
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este Proveedor?");
				if (JOptionPane.OK_OPTION == valor) {
					eliminarProveedor();
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);
		
		txtNif = new JTextField();
		txtNif.setColumns(10);
		txtNif.setBounds(367, 286, 126, 20);
		add(txtNif);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(560, 286, 80, 20);
		add(txtTelefono);

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

		tableProveedor = new JTable();
		scrollPane.setViewportView(tableProveedor);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre","NIF", "Direccion", "Email", "telefono" });
		tableProveedor.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(94, 288, 65, 17);
		add(lblNombre);

		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(322, 325, 40, 20);
		add(lblEmail);

		JLabel lblDireccion = new JLabel("Direccion: ");
		lblDireccion.setBounds(94, 326, 63, 14);
		add(lblDireccion);

		JLabel lblTel = new JLabel("Telefono: ");
		lblTel.setBounds(506, 289, 55, 14);
		add(lblTel);
		
		JLabel lblNif_1 = new JLabel("NIF:");
		lblNif_1.setBounds(322, 289, 46, 14);
		add(lblNif_1);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img\\diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		ListSelectionModel model = tableProveedor.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(tableProveedor.getSelectedRow()!=-1) {
				txtNombre.setText(tableProveedor.getValueAt(tableProveedor.getSelectedRow(), 0).toString());
				txtNif.setText(tableProveedor.getValueAt(tableProveedor.getSelectedRow(), 1).toString());
				txtDireccion.setText(tableProveedor.getValueAt(tableProveedor.getSelectedRow(), 2).toString());
				txtEmail.setText(tableProveedor.getValueAt(tableProveedor.getSelectedRow(), 3).toString());
				txtTelefono.setText(tableProveedor.getValueAt(tableProveedor.getSelectedRow(), 4).toString());	
			}
		}
	});
	}
	

	public void cargarTabla() {
		GestionBBDD gestor = new GestionBBDD();
		modeloTabla.setRowCount(0);
		for (Proveedor c : gestor.consultaProveedor()) {
			modeloTabla.addRow(new Object[] { c.getNombre(),c.getNif(), c.getDireccion(), c.getEmail(), c.getTelefono() });
		}
	}
	
	public void reemplazar() {
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtNif.setText("");
	}
	
	public void modificarProveedor() {
		Proveedor proveedor = new Proveedor();
		proveedor=pideDatosProveedor();
		
		if (tableProveedor.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un proveedor para modificar", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (proveedor.getNif().compareTo("")==0||proveedor.getNombre().compareTo("") == 0|| proveedor.getDireccion().compareTo("") == 0 || 
				proveedor.getEmail().compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (proveedor.getTelefono() < 100000000
				|| proveedor.getTelefono() > 999999999) { 
			JOptionPane.showMessageDialog(null,
					"Introduce un telefono valido", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			GestionBBDD gest = new GestionBBDD();
			gest.modificarTelefono(proveedor.getTelefono(), "proveedor", "nif", "proveedores", tableProveedor);
			gest.modificarProveedor(proveedor, tableProveedor);
			cargarTabla();
			reemplazar();
		}
	}
	public void eliminarProveedor() {
		if (tableProveedor.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un proveedor para eliminar", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {
			GestionBBDD gest = new GestionBBDD();
			gest.borrarTel("proveedor", "nif", "proveedores", tableProveedor);
			gest.borrarProveedor(tableProveedor);
			cargarTabla();
		}
	}
	public Proveedor pideDatosProveedor() {
		Proveedor proveedor = new Proveedor();
		proveedor.setNombre(txtNombre.getText());
		proveedor.setDireccion(txtDireccion.getText());
		proveedor.setEmail(txtEmail.getText());
		proveedor.setNif(txtNif.getText());
		try {
			proveedor.setTelefono(Integer.parseInt(txtTelefono.getText()));
		} catch (NumberFormatException e) {
			proveedor.setTelefono(-1);
		}
		return proveedor;
	}
}
