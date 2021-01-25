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
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.Escandallo;
import modelo.Venta;

public class Escandallos extends JPanel {

	private JTable tableEscandallos;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();

	/**
	 * Create the panel.
	 */
	public Escandallos() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertarEscandallo ie = new InsertarEscandallo();
				nuevoPanel(ie);
			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);
		
		JButton btnNewButton = new JButton("Ver Escandallo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEscandallos.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecciona un escandallo para ver", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					VerEscandallo ve = new VerEscandallo();
					ve.setEscanda(pideDatos());
					nuevoPanel(ve);
				}
				
			}
		});
		btnNewButton.setBounds(315, 381, 89, 23);
		add(btnNewButton);


		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar el Escandallo?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableEscandallos.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona un escandallo para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						gestor.borrarEscandalloMaterial(tableEscandallos);
						gestor.borrarEscandallo(tableEscandallos);
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

		tableEscandallos = new JTable();
		scrollPane.setViewportView(tableEscandallos);

		modeloTabla.setColumnIdentifiers(new Object[] { "Escandallo", "Producto"});
		tableEscandallos.setModel(modeloTabla);
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
		for (Escandallo e : gestor.consultaEscandallo()) {
			modeloTabla.addRow(
					new Object[] { e.getIdEscandallo(), e.getProducto()});
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
	public Escandallo pideDatos() {
		Escandallo escandallo = new Escandallo();
		escandallo.setIdEscandallo(Integer.parseInt(tableEscandallos.getValueAt(tableEscandallos.getSelectedRow(), 0).toString())); 
		return escandallo;
	}
}
