package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.DefaultComboBoxModel;

public class InsertarOrdenes extends JPanel {
	private JCalendar calendarioInicio;
	private JCalendar calendarioFin;
	private JTextField txtUnidades;
	private JButton btnInsert;

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
		
		JComboBox comboEscandallos = new JComboBox();
		comboEscandallos.setModel(new DefaultComboBoxModel(new String[] {"Escandallo"}));
		comboEscandallos.setBounds(88, 327, 140, 20);
		add(comboEscandallos);
		
		JComboBox comboPersonal = new JComboBox();
		comboPersonal.setModel(new DefaultComboBoxModel(new String[] {"Personal"}));
		comboPersonal.setBounds(249, 327, 140, 20);
		add(comboPersonal);
		
		JComboBox comboEstado = new JComboBox();
		comboEstado.setModel(new DefaultComboBoxModel(new String[] {"Estado"}));
		comboEstado.setBounds(410, 327, 140, 20);
		add(comboEstado);
		
		txtUnidades = new JTextField();
		txtUnidades.setColumns(10);
		txtUnidades.setBounds(150, 371, 86, 20);
		add(txtUnidades);
		
		btnInsert = new JButton("Insertar");
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
}
