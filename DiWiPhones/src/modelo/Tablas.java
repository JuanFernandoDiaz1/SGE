package modelo;

import javax.swing.JPanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		setBounds(0, 0, 729, 535);
		
		comboTablas = new JComboBox();
		comboTablas.setModel(new DefaultComboBoxModel(new String[] {"-Tablas-", "Clientes", "Personal", "Proveedores"}));
		comboTablas.setBounds(234, 248, 262, 38);
		add(comboTablas);
		
		JButton botonSalir = new JButton("Buscar");
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboTablas.getSelectedIndex()==1) {
					Clientes clientes = new Clientes();
					nuevoPanel(clientes);
				}else if(comboTablas.getSelectedItem().toString().compareToIgnoreCase("Personal")==0){
					Personal personal = new Personal();
					nuevoPanel(personal);
				}else if(comboTablas.getSelectedItem().toString().compareToIgnoreCase("Proveedores")==0){
					Proveedores proveedores = new Proveedores();
					nuevoPanel(proveedores);
				}else {
					JOptionPane.showMessageDialog(null, "Selecciona una tabla");

				}
			}
		});
		botonSalir.setBounds(321, 358, 89, 23);
		add(botonSalir);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		btnNewButton.setBounds(455, 82, 70, 23);
		add(btnNewButton);
		
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
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
}




