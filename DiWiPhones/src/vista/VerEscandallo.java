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
import modelo.Compras;
import modelo.Escandallo;

public class VerEscandallo extends JPanel {

	private JTable tableVerEscandallo;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();
	Escandallo escanda = new Escandallo();
	int id;


	/**
	 * Create the panel.
	 */
	public VerEscandallo() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 311);
		add(scrollPane);

		tableVerEscandallo = new JTable();
		scrollPane.setViewportView(tableVerEscandallo);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "Unidades"});
		tableVerEscandallo.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Escandallos esc = new Escandallos();
				nuevoPanel(esc);
			}
		});
		btnVolver.setBounds(313, 383, 106, 23);
		add(btnVolver);


		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	
	}
	public Escandallo getEscanda() {
		return escanda;
	}


	public void setEscanda(Escandallo escanda) {
		this.escanda = escanda;

		cargarTabla(escanda.getIdEscandallo());
	}

	public void cargarTabla(int id) {

		modeloTabla.setRowCount(0);
		for (Escandallo e : gestor.consultaVerEscandallo(id)) {
			modeloTabla.addRow(
					new Object[] { e.getProducto(), e.getUnidades()});
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
}
