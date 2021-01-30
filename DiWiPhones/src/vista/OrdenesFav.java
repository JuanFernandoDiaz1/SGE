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
import modelo.Escandallo;
import modelo.OrdenesFavM;

public class OrdenesFav extends JPanel {

	private JTable tableEscandallos;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();

	/**
	 * Create the panel.
	 */
	public OrdenesFav() {
		setLayout(null);
		setBounds(0, 0, 723, 507);

		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertarOrdenes io = new InsertarOrdenes();
				nuevoPanel(io);
			}
		});
		btnInsert.setBounds(199, 381, 89, 23);
		add(btnInsert);
		
		JButton btnCambiarEstado = new JButton("Finalizar");
		btnCambiarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEscandallos.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecciona una Orden para Finalizar", "Error", JOptionPane.WARNING_MESSAGE);
				}else if(tableEscandallos.getValueAt(tableEscandallos.getSelectedRow(), 6).toString().compareToIgnoreCase("Acabado")==0) {
					JOptionPane.showMessageDialog(null, "Esta orden ya esta finalizada", "Error", JOptionPane.WARNING_MESSAGE);			
				}else {
					gestor.acabarOrden(recogerDatos());
					cargarTabla();
				}
			}
		});
		btnCambiarEstado.setBounds(317, 381, 89, 23);
		add(btnCambiarEstado);


		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar la Orden?");
				if (JOptionPane.OK_OPTION == valor) {
					if(tableEscandallos.getSelectedRow()==-1) {
						JOptionPane.showMessageDialog(null, "Selecciona una Orden para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
					}else {
						gestor.borrarOrdenes(tableEscandallos);
						cargarTabla();
					}
				}
			}
		});
		btnEliminar.setBounds(429, 381, 89, 23);
		add(btnEliminar);
		btnRefresh.setIcon(new ImageIcon("img/actualizado.png"));
		btnRefresh.setBounds(22, 11, 40, 35);
		add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 41, 549, 311);
		add(scrollPane);

		tableEscandallos = new JTable();
		scrollPane.setViewportView(tableEscandallos);

		modeloTabla.setColumnIdentifiers(new Object[] { "Orden", "Escandallo", "Unidades","Personal", "FechaIncio", "FechaFin", "Estado" });
		tableEscandallos.setModel(modeloTabla);
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
		for (OrdenesFavM o : gestor.consultaOrdenesFav()) {
			modeloTabla.addRow(
					new Object[] {o.getIdOrden(), o.getEscandallo(), o.getUnidades(), o.getPersonal(), o.getFechaInicio(), o.getFechaFin(), o.getEstado()});
		}
	}
	
	public void nuevoPanel(JPanel panelActual) {
		removeAll();
		add(panelActual);
		repaint();
		revalidate();
	}
	public int recogerDatos() {
		int idOrden;
		idOrden=(Integer.parseInt(tableEscandallos.getValueAt(tableEscandallos.getSelectedRow(), 0).toString()));
		return idOrden;
	}
}
