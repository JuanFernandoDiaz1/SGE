package modelo;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Clientes extends JPanel {

	/**
	 * Create the panel.
	 */
	public Clientes() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		JLabel lblNewLabel = new JLabel("Clientes");
		lblNewLabel.setBounds(180, 140, 67, 14);
		add(lblNewLabel);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
	}
}
