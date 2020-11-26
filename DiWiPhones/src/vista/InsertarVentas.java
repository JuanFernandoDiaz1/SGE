package vista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class InsertarVentas extends JPanel {
	private JTextField textField;
	private JTable tableProductos;

	/**
	 * Create the panel.
	 */
	public InsertarVentas() {
		setLayout(null);
		setBounds(0, 0, 723, 507);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(90, 76, 86, 20);
		add(textField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(429, 75, 144, 22);
		add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 114, 241, 233);
		add(scrollPane);
		
		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);
		
		JLabel lblFactura = new JLabel("Factura: ");
		lblFactura.setBounds(28, 79, 61, 14);
		add(lblFactura);
		
		JButton btnInsert = new JButton("Insertar");
		btnInsert.setBounds(315, 394, 89, 23);
		add(btnInsert);
		
		JCalendar calendario = new JCalendar();
		calendario.setBounds(90, 119, 230, 136);
		add(calendario);
		
		JComboBox cmbClientes = new JComboBox();
		cmbClientes.setModel(new DefaultComboBoxModel(new String[] {"-Cliente-"}));
		cmbClientes.setBounds(90, 325, 188, 22);
		add(cmbClientes);
		
		JComboBox cmbEmpleados = new JComboBox();
		cmbEmpleados.setModel(new DefaultComboBoxModel(new String[] {"-Personal-"}));
		cmbEmpleados.setBounds(90, 280, 188, 22);
		add(cmbEmpleados);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		JButton btnCesta = new JButton("A\u00F1adir");
		btnCesta.setBounds(584, 75, 89, 23);
		add(btnCesta);
		
	}
	
	public void insertCliente(String nombre, String dni, String direccion, String email) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into clientes (nombre, dni, direccion, email) values ('" + nombre + "', '"
					+ dni + "', '" + direccion + "', '" + email + "')");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la BBDD");
		}
	}
}
