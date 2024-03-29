package vista;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controlador.GestionBBDD;
import modelo.Cliente;
import modelo.Compras;
import modelo.Factura;
import modelo.Proveedor;
import modelo.Venta;

public class InformeFacturaV extends JPanel {

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
	public InformeFacturaV() {
		setBounds(0, 0, 723, 507);

		JButton boton = new JButton("GenerarPDF");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_1.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecciona una venta para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
				}else {
					crearPDF(consultaCliente((int)table_1.getValueAt(table_1.getSelectedRow(), 0)),table_1,gestor.consultaFacturaVenta((int)table_1.getValueAt(table_1.getSelectedRow(), 0)));
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
		
		modeloTabla.setColumnIdentifiers(new Object[] { "Factura", "Fecha", "Cliente", "DNI Cliente", "Personal", "DNI Personal","Precio Venta"});
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

	public void crearPDF(Cliente c, JTable tabla, ArrayList<Factura> facts) {
		Document documento = new Document();
		int contTotal=0;
	
		try {
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("reporteSupremo.pdf"));
			documento.open();

			Image img = Image.getInstance("img\\diwi.png");
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
			table2.addCell(getCell("N� de factura   "+tabla.getValueAt(tabla.getSelectedRow(), 0).toString(), PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell(c.getNombre(), PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell("Alcorcon", PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("Fecha   "+tabla.getValueAt(tabla.getSelectedRow(), 1).toString(), PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell(c.getDireccion(), PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell("Alcala 34", PdfPCell.ALIGN_CENTER));
			table2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
			table2.addCell(getCell(c.getDni(), PdfPCell.ALIGN_LEFT));
			table2.addCell(getCell("28921", PdfPCell.ALIGN_CENTER));
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
			for (int fila = 0; fila < facts.size(); fila++) {
				for (int column = 0; column < 4; column++) {
					switch (column) {
					case 0:
						table.addCell(new Phrase(facts.get(fila).getCantidad()+""));
						break;
					case 1:
						table.addCell(new Phrase(facts.get(fila).getDescripcion()+""));
						break;
					case 2:
						table.addCell(new Phrase(facts.get(fila).getImporte()+""));
						break;
					case 3:
						table.addCell(new Phrase(facts.get(fila).getImporteTotal()+""));
						break;
					}
				}
				contTotal+=facts.get(fila).getImporteTotal();
			}
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase("Subtotal "));
			table.addCell(new Phrase(contTotal+""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase("Iva 21% "));
			table.addCell(new Phrase((contTotal*0.21)+""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase(""));
			table.addCell(new Phrase("Total "));
			table.addCell(new Phrase((contTotal*1.21)+""));
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
		for (Venta v : gestor.consultaVenta()) {
			modeloTabla.addRow(
					new Object[] { v.getFactura(), v.getFechaTotal(), v.getCliente(), v.getDniCliente(), v.getPersonal(), v.getDniPersonal(),v.getPrecioTotal() });
		}
	}
	public Cliente consultaCliente(int factura) {
		Cliente p = new Cliente();
		int id_cliente=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select id_cliente from venta where factura = "+factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				 id_cliente = registro.getInt("id_cliente");
			}
			ResultSet registro2 = consulta.executeQuery("select nombre, dni, direccion, email from clientes where id_cliente = "+id_cliente);
			while (registro2.next()) {
				 p.setNombre(registro2.getString("nombre"));
				 p.setDni(registro2.getString("dni"));
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
