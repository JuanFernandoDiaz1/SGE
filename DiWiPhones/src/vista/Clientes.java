package vista;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Clientes extends JPanel {
	private JTable tableClientes;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	/**
	 * Create the panel.
	 */
	public Clientes() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		
		JButton btnClientes = new JButton("Buscar");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnClientes.setBounds(39, 26, 89, 23);
		add(btnClientes);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(85, 72, 549, 210);
		add(scrollPane);
		
		tableClientes = new JTable();
		scrollPane.setViewportView(tableClientes);
		
		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "DNI", "Direccion", "Email" });
		tableClientes.setModel(modeloTabla);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
	}
}
