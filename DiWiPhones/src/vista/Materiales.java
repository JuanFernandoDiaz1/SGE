package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.GestionBBDD;
import modelo.Cliente;
import modelo.Escandallo;
import modelo.Material;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Materiales extends JPanel {

	/**
	 * Create the panel.
	 */
	private JTable tableMateriales;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();
	private JTextField textNombre;
	private JTextField textStock;

	/**
	 * Create the panel.
	 */
	public Materiales() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Material mat = new Material();
				mat = recogerDatos();
				gestor.insertarMaterial(mat);
				cargarTabla();
			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);


		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere Eliminar el Material?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableMateriales.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona un material para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						gestor.borrarMaterial(tableMateriales);
						cargarTabla();
					}
				}
			}
		});
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(146, 326, 46, 14);
		add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(199, 323, 86, 20);
		add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(378, 326, 46, 14);
		add(lblStock);
		
		textStock = new JTextField();
		textStock.setBounds(434, 323, 86, 20);
		add(textStock);
		textStock.setColumns(10);
		
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 252);
		add(scrollPane);

		tableMateriales = new JTable();
		scrollPane.setViewportView(tableMateriales);

		modeloTabla.setColumnIdentifiers(new Object[] { "Nombre", "Stock"});
		tableMateriales.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

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
		for (Material m : gestor.consultaMateriales()) {
			modeloTabla.addRow(
					new Object[] { m.getNombre(), m.getStock()});
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
		
	}
	public Material recogerDatos() {
		Material mat = new Material();
		mat.setNombre(textNombre.getText());
		mat.setStock(Integer.parseInt(textStock.getText()));
		return mat;
	}
	
	
}
