package controlador;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Home extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWi\\img\\logo-diwi.PNG"));
		setTitle("DiWi");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 535);
		contentPane = new JPanel();
			
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelDiwi = new JLabel("");
		labelDiwi.setIcon(new ImageIcon("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWi\\img\\diwi.png"));
		labelDiwi.setBounds(0, 11, 188, 63);
		contentPane.add(labelDiwi);
		
		
		JButton botonTablas = new JButton("Tablas");
		botonTablas.setBounds(237, 244, 262, 38);
		contentPane.add(botonTablas);
		
		JButton botonOperaciones = new JButton("Operaciones");
		botonOperaciones.setBounds(237, 311, 262, 38);
		contentPane.add(botonOperaciones);
		
		JButton botonSalir = new JButton("Salir");
		botonSalir.setBounds(322, 384, 89, 23);
		contentPane.add(botonSalir);
		
		JLabel labelCuadro1 = new JLabel("");
		labelCuadro1.setIcon(new ImageIcon("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWi\\img\\cuadro1.PNG"));
		labelCuadro1.setBounds(196, 69, 340, 382);
		contentPane.add(labelCuadro1);
		
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setIcon(new ImageIcon("C:\\Users\\juanfernando.diaz.JUANXXIII_23\\Documents\\GitHub\\SGE\\DiWi\\img\\fondo.jpg"));
		labelFondo.setBounds(0, 0, 723, 496);
		contentPane.add(labelFondo);
		
		
		
		
		
		
		
		
		
		
		
	}
}
