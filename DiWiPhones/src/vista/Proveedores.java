package vista;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Proveedores extends JPanel {

	/**
	 * Create the panel.
	 */
	public Proveedores() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		JLabel lblNewLabel = new JLabel("Proveedores");
		lblNewLabel.setBounds(180, 140, 46, 14);
		add(lblNewLabel);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
	}

}
