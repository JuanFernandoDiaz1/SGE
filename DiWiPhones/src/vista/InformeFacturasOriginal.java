package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InformeFacturasOriginal extends JPanel {
	private JComboBox comboFacturas;
	/**
	 * Create the panel.
	 */
	public InformeFacturasOriginal() {

		setLayout(null);
		setBounds(0, 0, 729, 535);
		comboFacturas = new JComboBox();
		comboFacturas.setModel(new DefaultComboBoxModel(new String[] {"-Facturas-", "Compra", "Venta"}));
		comboFacturas.setBounds(234, 248, 262, 38);
		add(comboFacturas);
		
		JButton botonSalir = new JButton("Buscar");
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboFacturas.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "Selecciona una tabla");
				}else if(comboFacturas.getSelectedIndex()==1){
					InformeFacturaC compra = new InformeFacturaC();
					nuevoPanel(compra);
				}else if(comboFacturas.getSelectedIndex()==2){
					InformeFacturaV ventas = new InformeFacturaV();
					nuevoPanel(ventas);
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
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}

}
