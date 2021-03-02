package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.Cliente;
import modelo.Compras;
import modelo.Personal;
import modelo.Proveedor;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class InformeFacturaC extends JPanel{
	
	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private GestionBBDD gest = new GestionBBDD();
	private JLabel labelSlider1; 
	private JLabel labelSlider2;
	
	ArrayList<Proveedor> proveedores = gest.consultaProveedor();
	private JTextField textCorreo;
	private JTable table_1;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	GestionBBDD gestor = new GestionBBDD();
	/**
	 * Create the panel.
	 */
	public InformeFacturaC() {
		setBounds(0, 0, 723, 507);

		JButton boton = new JButton("GenerarPDF");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_1.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecciona una compra para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					crearPDF(consultaProveedor((int)table_1.getValueAt(table_1.getSelectedRow(), 0)),table_1);
					abrirPDF();
				}
				

			}
		});
		setLayout(null);
		boton.setBounds(161, 421, 118, 23);
		add(boton);

		JButton btnEnviar = new JButton("Enviar por Correo");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarCorreo(textCorreo.getText());
			}
		});
		btnEnviar.setBounds(426, 421, 118, 23);
		add(btnEnviar);
		
		labelSlider2 = new JLabel("");
		labelSlider2.setBounds(648, 339, 46, 23);
		add(labelSlider2);
		
		labelSlider1 = new JLabel("");
		labelSlider1.setBounds(301, 336, 46, 26);
		add(labelSlider1);
		
		textCorreo = new JTextField();
		textCorreo.setBounds(402, 390, 169, 20);
		add(textCorreo);
		textCorreo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 97, 522, 245);
		add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		modeloTabla.setColumnIdentifiers(new Object[] { "Factura", "Fecha", "Proveedor", "NIF Proveedor", "Personal", "DNI Personal","Precio Compra"});
		table_1.setModel(modeloTabla);
		modeloTabla.setRowCount(0);
		cargarTabla();

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(0, 0, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);
		
		

	}

	public void crearPDF(Proveedor p, JTable tabla) {
		Document documento = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("reporteSupremo.pdf"));
			documento.open();

			Image img = Image.getInstance("img\\\\diwi.png");
			img.scaleToFit(100, 100);
			documento.add(img);
			documento.add(new Paragraph("DiWi phones S.L"));
			documento.add(new Paragraph("Calle Falsa 123"));
			documento.add(new Paragraph("21021 Alcorcon, Madrid"));
			documento.add(Chunk.NEWLINE);
	
			
			PdfPTable table2 = new PdfPTable(3);
			table2.setWidthPercentage(100);
			table2.addCell(getCell("Facturar a", PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell("Enviar a", PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("Nª de factura   "+tabla.getValueAt(tabla.getSelectedRow(), 0).toString(), PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell("Alcorcon", PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell(p.getNombre(), PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("Fecha   "+tabla.getValueAt(tabla.getSelectedRow(), 1).toString(), PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell("Sapopeta 34", PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell(p.getDireccion(), PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell("28921", PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell(p.getNif(), PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
			
			documento.add(table2);

			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			
			PdfPTable table = new PdfPTable(4);
			table.getDefaultCell().setBorder(0);
			PdfPCell columnHeader;
			
			columnHeader = new PdfPCell(new Phrase("Cantidad"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			columnHeader.setBorder(0);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Descripcion"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			columnHeader.setBorder(0);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Precio Unitario"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			columnHeader.setBorder(0);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Importe"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			columnHeader.setBorder(0);
			table.addCell(columnHeader);
			
			table.setHeaderRows(1);
			for (int fila = 0; fila < 1; fila++) {
				for (int column = 0; column < 4; column++) {
					switch (column) {
					case 0:
						table.addCell(new Phrase("juan"));
						break;
					case 1:
						table.addCell(new Phrase("mario"));
						break;
					case 2:
						table.addCell(new Phrase("1"));
						break;
					case 3:
						table.addCell(new Phrase("21/01/2012"));
						break;
					}
				}
			}

			documento.add(table);
			

			documento.close();
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void abrirPDF() {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "reporteSupremo.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*public ArrayList<Compras> consultaCompras() {
		ArrayList<Compras> compras = new ArrayList<Compras>();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		String fechaInicio = dFormat.format(calendario.getDate());
		String fechaFin = dFormat.format(calendario2.getDate());
		System.out.println(fechaInicio + " " + fechaFin);
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("SELECT Factura, fecha, proveedores.Nombre, proveedores.nif, PrecioCompra from compra inner join proveedores "
					+ "on compra.id_proveedor=proveedores.ID_Proveedor where (fecha BETWEEN '"+dFormat.format(calendario.getDate())+"' and '"+dFormat.format(calendario2.getDate())+"') and (compra.id_proveedor BETWEEN "+sliderI.getValue() +" and " +sliderf.getValue()+") "
					+ "order by 2");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Compras compra = new Compras();
				// guardamos los campos en el objeto modelo
				compra.setFactura(registro.getInt("Factura"));
				compra.setFechaTotal(registro.getString("Fecha"));
				compra.setProveedor(registro.getString("proveedores.Nombre"));
				compra.setNifProveedor(registro.getString("proveedores.NIF"));
				compra.setPrecioTotal(registro.getInt("precioCompra"));
				// añadimos modelos al arrayList
				compras.add(compra);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return compras;
	}*/
	
	public void enviarCorreo(String correo){
		String[] archivoAdjunto;
        File fichero = new File("reporteSupremo.pdf");

        final String username = "mario.jimenez@juanxxiii.net";
        final String password = "remedios69";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mario.jimenez@juanxxiii.net"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo));
            message.setSubject("Adjunto Pdf DiwisPhones");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Buenos dias, se le adjunta el pdf solicictado.");

            MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource("reporteSupremo.pdf");
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("archivoAdjunto.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correcto!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	public PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
	public void cargarTabla() {
		modeloTabla.setRowCount(0);
		for (Compras v : gestor.consultaCompras()) {
			modeloTabla.addRow(
					new Object[] { v.getFactura(), v.getFechaTotal(), v.getProveedor(), v.getNifProveedor(), v.getPersonal(), v.getDniPersonal(),v.getPrecioTotal() });
		}
	}
	public Proveedor consultaProveedor(int factura) {
		Proveedor p = new Proveedor();
		int id_Personal=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select id_Proveedor from compra where factura = "+factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				 id_Personal = registro.getInt("id_Proveedor");
			}
			ResultSet registro2 = consulta.executeQuery("select nombre, Nif, direccion, email from Proveedores where id_Proveedor = "+id_Personal);
			while (registro2.next()) {
				 p.setNombre(registro2.getString("nombre"));
				 p.setNif(registro2.getString("Nif"));
				 p.setDireccion(registro2.getString("direccion"));
				 p.setEmail(registro2.getString("email"));
			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return p;
	}
	
	
}
