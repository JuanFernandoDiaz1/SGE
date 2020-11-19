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

					if(comboBox.getSelectedIndex()==1) {
						int id=gestor.obtenerIdGeneral("cliente", "Clientes", "dni", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
						gestor.insertTelefono(tel.getNumero(), id, "cliente", "clientes");
						reemplazar();
						cargarTabla("cliente", "dni", "clientes");
					}else if(comboBox.getSelectedIndex()==2) {
						int id=gestor.obtenerIdGeneral("personal", "personal", "dni", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
						System.out.println(id);
						gestor.insertTelefono(tel.getNumero(), id, "personal", "personal");
						reemplazar();
						cargarTabla("personal", "dni", "personal");
					}else if(comboBox.getSelectedIndex()==3) {
						int id=gestor.obtenerIdGeneral("proveedor", "proveedores", "nif", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
						System.out.println(id);
						gestor.insertTelefono(tel.getNumero(), id, "proveedor", "proveedores");
						reemplazar();
						cargarTabla("proveedor", "nif", "proveedores");
					}
					
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
					modificarTelefono();
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

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(337, 309, 80, 21);
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
		lblTel.setBounds(283, 312, 55, 18);
		add(lblTel);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img\\diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		ListSelectionModel model = tableTelefono.getSelectionModel();
		
		
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
	}
	
	public void modificarTelefono() {
		Telefono tel = new Telefono();
		tel=pideDatosTelefono();
		GestionBBDD gestor = new GestionBBDD();
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
					"Selecciona un numero para modificar", "Error",
					JOptionPane.WARNING_MESSAGE);
		}else {

			if(comboBox.getSelectedIndex()==1) {
				int id=gestor.obtenerIdGeneral("cliente", "Clientes", "dni", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
				gestor.modificarTelefonoV2(tel, tableTelefono, "cliente", id);
				reemplazar();
				cargarTabla("cliente", "dni", "clientes");
			}else if(comboBox.getSelectedIndex()==2) {
				int id=gestor.obtenerIdGeneral("personal", "personal", "dni", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
				gestor.modificarTelefonoV2(tel, tableTelefono, "personal", id);
				reemplazar();
				cargarTabla("personal", "dni", "personal");
			}else if(comboBox.getSelectedIndex()==3) {
				int id=gestor.obtenerIdGeneral("proveedor", "proveedores", "nif", tableTelefono.getValueAt(tableTelefono.getSelectedRow(), 1).toString());
				System.out.println(id);
				gestor.modificarTelefonoV2(tel, tableTelefono, "proveedor", id);
				reemplazar();
				cargarTabla("proveedor", "nif", "proveedores");
			}
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