package modelo;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tablas extends JPanel {
	private JComboBox comboTablas;
	/**
	 * Create the panel.
	 */
	public Tablas() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		
		comboTablas = new JComboBox();
		comboTablas.setModel(new DefaultComboBoxModel(new String[] {"-Tablas-", "Clientes", "Personal", "Proveedores"}));
		comboTablas.setBounds(234, 248, 262, 38);
		add(comboTablas);
		
		JButton botonSalir = new JButton("Buscar");
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboTablas.getSelectedItem().toString().compareToIgnoreCase("Clientes")==0) {
					
				}
			}
		});
		botonSalir.setBounds(321, 358, 89, 23);
		add(botonSalir);
		
		JLabel labelDiwi = new JLabel("");
		labelDiwi.setIcon(new ImageIcon("img\\diwi.png"));
		labelDiwi.setBounds(0, 11, 188, 63);
		add(labelDiwi);
		
		JLabel labelCuadro1 = new JLabel("");
		labelCuadro1.setIcon(new ImageIcon("img\\cuadro1.PNG"));
		labelCuadro1.setBounds(196, 69, 340, 382);
		add(labelCuadro1);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
	}
}




