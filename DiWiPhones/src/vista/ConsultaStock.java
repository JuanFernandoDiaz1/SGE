package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.BuscarProductoM;
import modelo.Escandallo;
import javax.swing.JTextField;

public class ConsultaStock extends JPanel {

	private JTable tableConsultaStock;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();
	private JTextField txtBuscar;

	public ConsultaStock() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 112, 549, 281);
		add(scrollPane);

		tableConsultaStock = new JTable();
		scrollPane.setViewportView(tableConsultaStock);

		modeloTabla.setColumnIdentifiers(new Object[] { "Tipo", "Fecha", "Personal", "Unidades", "Precio", "Stock"});
		tableConsultaStock.setModel(modeloTabla);
		modeloTabla.setRowCount(0);

		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(133, 62, 179, 20);
		add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTabla();
			}
		});
		btnBuscar.setBounds(393, 61, 89, 23);
		add(btnBuscar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(494, 394, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		
		
		

	
	}
	public void cargarTabla() {
		modeloTabla.setRowCount(0);
		for (BuscarProductoM e : calculos()) {
			modeloTabla.addRow(
					new Object[] {e.getTipo(), e.getFecha(), e.getPersonal(), e.getUnidades(), e.getPrecio(), e.getStock()});
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
	public Escandallo pideDatos() {
		Escandallo escandallo = new Escandallo();
		escandallo.setIdEscandallo(Integer.parseInt(tableConsultaStock.getValueAt(tableConsultaStock.getSelectedRow(), 0).toString())); 
		return escandallo;
	}
	public ArrayList<BuscarProductoM> calculos() {
		GestionBBDD gestor = new GestionBBDD();
		OrdenarProductos ordenar = new OrdenarProductos();
		ArrayList <BuscarProductoM> productosC = gestor.consultaBuscarProductoV(txtBuscar.getText().toString());
		gestor.consultaBuscarProductoC(productosC, txtBuscar.getText().toString());
		
		ordenar.getOrdenarArrrayList(productosC);
		
		return productosC;
	}
}
