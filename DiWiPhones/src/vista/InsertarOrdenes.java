package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.OrdenesFavM;

import javax.swing.DefaultComboBoxModel;

public class InsertarOrdenes extends JPanel {
	private JCalendar calendarioInicio;
	private JCalendar calendarioFin;
	private JTextField txtUnidades;
	private JButton btnInsert;
	private JComboBox comboEscandallos;
	private JComboBox comboPersonal;
	private JComboBox comboEstado;
	private GestionBBDD gestor = new GestionBBDD();

	/**
	 * Create the panel.
	 */
	public InsertarOrdenes() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		calendarioInicio = new JCalendar();
		calendarioInicio.setBounds(88, 122, 230, 136);
		// calendario.setMaxSelectableDate(2020);
		add(calendarioInicio);
		
		calendarioFin = new JCalendar();
		calendarioFin.setBounds(410, 122, 230, 136);
		// calendario.setMaxSelectableDate(2020);
		add(calendarioFin);
		
		comboEscandallos = new JComboBox();
		comboEscandallos.setModel(cargaEscandallo());
		comboEscandallos.setBounds(88, 327, 140, 20);
		add(comboEscandallos);
		
		comboPersonal = new JComboBox();
		comboPersonal.setModel(cargaPersonal());
		comboPersonal.setBounds(249, 327, 140, 20);
		add(comboPersonal);
		
		comboEstado = new JComboBox();
		comboEstado.setModel(cargaEstado());
		comboEstado.setBounds(410, 327, 140, 20);
		add(comboEstado);
		
		txtUnidades = new JTextField();
		txtUnidades.setColumns(10);
		txtUnidades.setBounds(150, 371, 86, 20);
		add(txtUnidades);
		
		btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrdenesFavM ordenes = new OrdenesFavM();
				ordenes = recogerDatos();
				
				if(comboEstado.getSelectedIndex() == 0 || comboPersonal.getSelectedIndex() == 0 || comboEscandallos.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecciona un todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
				}else if(ordenes.getUnidades() <= 0 || ordenes.getUnidades()>= 100000) {
					JOptionPane.showMessageDialog(null, "Selecciona unas unidades validas", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					gestor.insertOrdenes(ordenes);
					gestor.sumarStock(ordenes.getUnidades(), ordenes.getEscandallo());
					OrdenesFav ord = new OrdenesFav();
					nuevoPanel(ord);
				}
				
			}
		});
		btnInsert.setBounds(319, 435, 89, 23);
		add(btnInsert);
		
		JLabel lblUnidades = new JLabel("Unidades:");
		lblUnidades.setBounds(88, 373, 69, 14);
		add(lblUnidades);
		
		JLabel lblFin = new JLabel("Fecha fin");
		lblFin.setBounds(410, 88, 107, 23);
		add(lblFin);
		
		JLabel lblInicio = new JLabel("Fecha inicio");
		lblInicio.setBounds(88, 88, 107, 23);
		add(lblInicio);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
	}
	public DefaultComboBoxModel cargaEscandallo() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Escandallo-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select nombre, Escandallo.id_escandallo from productos inner join escandallo on Productos.id_producto = Escandallo.id_producto ");
			while (registro.next()) {
				listaModelo.addElement(registro.getInt(2) + "|" + registro.getString("nombre"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}
	public DefaultComboBoxModel cargaPersonal() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Personal-");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select dni from personal");
			while (registro.next()) {
				listaModelo.addElement(registro.getString("dni"));
			}
			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}

		return listaModelo;
	}
	public DefaultComboBoxModel cargaEstado() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("-Estado-");
		listaModelo.addElement("Acabado");
		listaModelo.addElement("En proceso");

		return listaModelo;
	}
	public OrdenesFavM recogerDatos() {
		OrdenesFavM ordenes = new OrdenesFavM();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		String linea = comboEscandallos.getSelectedItem().toString();
		String tokens[] = linea.split("|");
		
		
		try {
			ordenes.setEscandallo(Integer.parseInt(tokens[0]));
			ordenes.setUnidades(Integer.parseInt(txtUnidades.getText()));
		}catch(NumberFormatException e) {
			ordenes.setUnidades(0);
		}
		
		ordenes.setFechaInicio(dFormat.format(calendarioInicio.getDate()));
		ordenes.setFechaFin(dFormat.format(calendarioFin.getDate()));
		ordenes.setPersonal(comboPersonal.getSelectedItem().toString());
		ordenes.setEstado(comboEstado.getSelectedItem().toString());
		return ordenes;
	}
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
	}
}
