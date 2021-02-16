package vista;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import modelo.Prueba;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class PruebaReport extends JPanel {
	private JTextField txtNombre;

	/**
	 * Create the panel.
	 */
	public PruebaReport() {
		setBounds(0, 0, 723, 507);
		
		JButton boton = new JButton("New button");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Prueba p = new Prueba();
				p.setNombre(txtNombre.getText());
				JasperReport reporte;
				String path = "reportes\\Reporte1.jrxml";
				List<Prueba> lista = new ArrayList<>(); 
				lista.add(p);
				try {
					reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
					JasperPrint jprint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista));
					JasperViewer viewer = new JasperViewer(jprint, false);
					viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					viewer.setVisible(true);
				}catch(JRException e2) {
					e2.printStackTrace();
				}
			}
		});
		boton.setBounds(180, 146, 89, 23);
		add(boton);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(183, 80, 86, 20);
		add(txtNombre);
		txtNombre.setColumns(10);

	}
}
