package controlador;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vista.ConsultaStock;
import vista.Fabrica;
import vista.InformeFactura;
import vista.InformesCompra;
import vista.InformesVenta;
import vista.Operaciones;
import vista.PruebaReport;
import vista.Tablas;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VntPrincipal extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void cargaVentana(VntPrincipal frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VntPrincipal frame = new VntPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VntPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\logo-diwi.PNG"));
		setTitle("DiWi");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 535);
		contentPane = new JPanel();
		setResizable(false);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 713, 21);
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem menuTablas = new JMenuItem("Tablas");
		menuTablas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Tablas tablas = new Tablas();
				nuevoPanel(tablas);
			}
		});
		menu.add(menuTablas);
		
		JMenuItem menuOperaciones = new JMenuItem("Operaciones");
		menuOperaciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Operaciones operaciones = new Operaciones();
				nuevoPanel(operaciones);
			}
		});
		menu.add(menuOperaciones);
		
		JMenuItem menuFabrica = new JMenuItem("Menu Fabrica");
		menuFabrica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fabrica fabrica = new Fabrica();
				nuevoPanel(fabrica);
			}
		});
		menu.add(menuFabrica);
		
		JMenuItem menuStock = new JMenuItem("Consulta Stock");
		menuStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaStock stock = new ConsultaStock();
				nuevoPanel(stock);
			}
		});
		menu.add(menuStock);
		
		JMenu mnNewMenu = new JMenu("Informes");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuInformeStock = new JMenuItem("Informe de stock");
		menuInformeStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PruebaReport p = new PruebaReport();
				nuevoPanel(p);
			}
		});
		mnNewMenu.add(menuInformeStock);
		
		JMenuItem menuInformeCompras = new JMenuItem("Informe compras");
		menuInformeCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InformesCompra ic = new InformesCompra();
				nuevoPanel(ic);
			}
		});
		mnNewMenu.add(menuInformeCompras);
		
		JMenuItem menuInformeVentas = new JMenuItem("Informe ventas");
		menuInformeVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InformesVenta iv = new InformesVenta();
				nuevoPanel(iv);
			}
		});
		mnNewMenu.add(menuInformeVentas);
		
		JMenuItem menuInformeFacturas = new JMenuItem("Informe factura");
		menuInformeFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InformeFactura ifa = new InformeFactura();
				nuevoPanel(ifa);
			}
		});
		mnNewMenu.add(menuInformeFacturas);
		
		JLabel labelDiwi = new JLabel("");
		labelDiwi.setIcon(new ImageIcon("img\\diwi.png"));
		labelDiwi.setBounds(0, 26, 188, 63);
		contentPane.add(labelDiwi);
		
		JLabel labelCuadro1 = new JLabel("");
		labelCuadro1.setIcon(new ImageIcon("img\\cuadro1.PNG"));
		labelCuadro1.setBounds(196, 69, 340, 382);
		contentPane.add(labelCuadro1);
		
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		labelFondo.setBounds(0, 0, 723, 507);
		contentPane.add(labelFondo);
		
	}
	
	public void nuevoPanel(JPanel panelActual) {
		contentPane.removeAll();
		contentPane.add(panelActual);
		contentPane.repaint();
		contentPane.revalidate();
		
	}
}
