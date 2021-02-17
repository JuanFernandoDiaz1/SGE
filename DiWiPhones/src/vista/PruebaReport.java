package vista;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import controlador.GestionBBDD;
import modelo.BuscarProductoM;


import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class PruebaReport extends JPanel {
	private JTextField txtNombre;
	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    

	/**
	 * Create the panel.
	 */
	public PruebaReport() {
		setBounds(0, 0, 723, 507);
		
		JButton boton = new JButton("New button");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPDF(calculos());
				abrirPDF();
			}
		});
		boton.setBounds(180, 146, 89, 23);
		add(boton);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(183, 80, 86, 20);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		

	}
	
	public void crearPDF(ArrayList<BuscarProductoM> array) {
		Document documento = new Document();
		 try {
			PdfWriter.getInstance(documento, new FileOutputStream("reporteSupremo.pdf"));
			documento.open();
			/*
			Anchor anchor = new Anchor("Tabla de los movmientos de "+txtNombre.getText().toUpperCase(), categoryFont);            
			
			DottedLineSeparator dottedline = new DottedLineSeparator();
	        dottedline.setOffset(-2);
	        dottedline.setGap(2f);
	        anchor.add(dottedline);*/
   
			
			PdfPTable table = new PdfPTable(6); 
			
			PdfPCell columnHeader;
			// Fill table rows (rellenamos las filas de la tabla).                
			
			columnHeader = new PdfPCell(new Phrase("Tipo"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("Fecha"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("Proveedor/Cliente"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("Unidades"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("Precio"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("Stock"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			table.setHeaderRows(1);
			                
			for (int fila = 0; fila < array.size(); fila++) {
			    for (int column = 0; column < 6; column++) {
			    	switch(column) {
			    		case 0:
			    			table.addCell(array.get(fila).getTipo());
			    			break;
			    		case 1:
			    			table.addCell(array.get(fila).getFecha());
			    			break;
			    		case 2:
			    			table.addCell(array.get(fila).getPersonal());
			    			break;
			    		case 3:
			    			table.addCell(array.get(fila).getUnidades()+"");
			    			break;
			    		case 4:
			    			table.addCell(array.get(fila).getPrecio()+"");
			    			break;
			    		case 5:
			    			table.addCell(array.get(fila).getStock()+"");
			    			break;
			    	}
			        
			    }
			}			
			
			documento.add(table);
			documento.close();
			
			documento.close();		
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	public ArrayList<BuscarProductoM> calculos() {
		GestionBBDD gestor = new GestionBBDD();
		OrdenarProductos ordenar = new OrdenarProductos();
		ArrayList<BuscarProductoM> productosC = gestor.consultaBuscarProductoV(txtNombre.getText().toString());
		gestor.consultaBuscarProductoC(productosC, txtNombre.getText().toString());
		gestor.stockFabricaSimple(productosC, txtNombre.getText().toString());
		ordenar.getOrdenarArrrayList(productosC);

		return productosC;
	}
	
	public static void abrirPDF() {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler "+ "reporteSupremo.pdf");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
