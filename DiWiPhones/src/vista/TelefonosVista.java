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
import modelo.Proveedor;
import modelo.Telefono;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class TelefonosVista extends JPanel {
	private JTable tableTelefono;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTextField txtTelefono;
	private JTextField txtDni;
	private JComboBox comboBox;
	/**
	 * Create the panel.
	 */
	public TelefonosVista() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionBBDD gestor = new GestionBBDD();
				Telefono tel = new Telefono();
				tel=pideDatosTelefono();
				if(comboBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "Seleciona Cliente/Personal/Proveedor", "Error",
							JOptionPane.WARNING_MESSAGE);
				}else if (tel.getNumero() < 100000000
						|| tel.getNumero() > 999999999) { 
					JOptionPane.showMessageDialog(null,
							"Introduce un telefono valido", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if(tableTelefono.getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null,
							"Selecciona una fila", "Error",
							JOptionPane.WARNING_MESSAGE);
				}else {

					gestor.insertTelefono(321321321, "dni", "cliente", "clientes", "11111111");
					
					//gestor.insertTel("cliente", 555555555, 11);
					reemplazar();
				}

			}
		});
		btnInsert.setBounds(196, 360, 89, 23);
		add(btnInsert);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Modificar este Proveedor?");
				if (JOptionPane.OK_OPTION == valor) {
					/*modificarProveedor();
					cargarTabla();*/
				}
			}
		});
		btnModificar.setBounds(312, 360, 89, 23);
		add(btnModificar);
		
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este Proveedor?");
				if (JOptionPane.OK_OPTION == valor) {
					eliminarProveedor();
					//cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(426, 360, 89, 23);
		add(btnEliminar);
		
		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(436, 308, 126, 20);
		add(txtDni);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(208, 308, 80, 20);
		add(txtTelefono);

		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 74, 549, 210);
		add(scrollPane);

		tableTelefono = new JTable();
		scrollPane.setViewportView(tableTelefono);

		modeloTabla.setColumnIdentifiers(new Object[] { "Numero","Documento", "Nombre"});
		tableTelefono.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "Selecciona una opcion");
				}else if(comboBox.getSelectedIndex()==1) {
					cargarTabla("cliente", "dni", "clientes");
				}else if(comboBox.getSelectedIndex()==2) {
					cargarTabla("personal", "dni", "personal");
				}else if(comboBox.getSelectedIndex()==3) {
					cargarTabla("proveedor", "nif", "proveedores");
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"-Seleciona una opcion-", "Clientes", "Personal", "Proveedores"}));
		comboBox.setBounds(266, 41, 167, 22);
		add(comboBox);

		JLabel lblTel = new JLabel("Telefono: ");
		lblTel.setBounds(154, 311, 55, 14);
		add(lblTel);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(391, 311, 46, 14);
		add(lblDni);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img\\diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		ListSelectionModel model = tableTelefono.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(tableTelefono.getSelectedRow()!=-1) {
				txtDni.setText(tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
				txtTelefono.setText(tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 0).toString());
			}else {
				txtTelefono.setText("");
				txtDni.setText("");
			}
		}
	});
	}
	

	public void cargarTabla(String tipo, String documento, String entidad) {
		GestionBBDD gestor = new GestionBBDD();
		modeloTabla.setRowCount(0);
		for (Telefono t : gestor.consultaTelefono(tipo, documento, entidad)) {
			modeloTabla.addRow(new Object[] { t.getNumero(),t.getDni(), t.getTitular() });
		}
	}
	
	public void reemplazar() {
		txtTelefono.setText("");
		txtDni.setText("");
	}
	
	public void modificarTelefono() {
		Telefono tel = new Telefono();
		tel=pideDatosTelefono();
		
		if (tableTelefono.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un proveedor para modificar", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (tel.getDni().compareTo("")==0||tel.getTitular().compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "Introduce todos los campos", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else if (tel.getNumero() < 100000000
				|| tel.getNumero() > 999999999) { 
			JOptionPane.showMessageDialog(null,
					"Introduce un telefono valido", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			GestionBBDD gest = new GestionBBDD();
			//modificar
			//
			//cargarTabla();
			reemplazar();
		}
	}
	public void eliminarProveedor() {
		if (tableTelefono.getSelectedRow() == -1) {//
			JOptionPane.showMessageDialog(null, "Selecciona un proveedor para eliminar", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {
			GestionBBDD gest = new GestionBBDD();
			gest.borrarTel("proveedor", "nif", "proveedores", tableTelefono);
			gest.borrarProveedor(tableTelefono);
			//cargarTabla();
		}
	}
	public Telefono pideDatosTelefono() {
		Telefono tel = new Telefono();
		try {
			tel.setNumero(Integer.parseInt(txtTelefono.getText()));
		} catch (NumberFormatException e) {
			tel.setNumero(-1);
		}
		return tel;
	}
}