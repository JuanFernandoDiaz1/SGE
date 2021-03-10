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
import com.toedter.calendar.JCalendar;

import controlador.GestionBBDD;
import modelo.Cliente;
import modelo.Compras;
import modelo.Proveedor;
import modelo.Venta;

public class InformesVenta extends JPanel{
	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private GestionBBDD gest = new GestionBBDD();
	private JCalendar calendario;
	private JCalendar calendario2;
	private JSlider sliderI;
	private JSlider sliderf;
	private JLabel labelSlider1; 
	private JLabel labelSlider2;
	
	ArrayList<Cliente> clientes = gest.consulta();
	private JTextField textCorreo;
	/**
	 * Create the panel.
	 */
	public InformesVenta() {
		setBounds(0, 0, 723, 507);

		JButton boton = new JButton("GenerarPDF");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				crearPDF(consultaCompras());
				abrirPDF();

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
		
		calendario = new JCalendar();
		calendario.setBounds(91, 143, 230, 136);
		add(calendario);
		
		calendario2 = new JCalendar();
		calendario2.setBounds(408, 143, 230, 136);
		add(calendario2);
		
		labelSlider2 = new JLabel("");
		labelSlider2.setBounds(648, 339, 46, 23);
		add(labelSlider2);
		
		labelSlider1 = new JLabel("");
		labelSlider1.setBounds(301, 336, 46, 26);
		add(labelSlider1);
		
		sliderI = new JSlider();
		sliderI.setValue(0);
		sliderI.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				labelSlider1.setText(sliderI.getValue()+"");
			}
		});
		sliderI.setBounds(91, 336, 200, 26);
		sliderI.setMaximum(clientes.get(clientes.size()-1).getId());
		add(sliderI);
		
		
		sliderf = new JSlider();
		sliderf.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				labelSlider2.setText(sliderf.getValue()+"");
			}
		});
		sliderf.setBounds(427, 336, 200, 26);
		sliderf.setValue(0);
		sliderf.setMaximum(clientes.get(clientes.size()-1).getId());
		add(sliderf);
		
		textCorreo = new JTextField();
		textCorreo.setBounds(402, 390, 169, 20);
		add(textCorreo);
		textCorreo.setColumns(10);
		
		JLabel lblpr = new JLabel("Rango Clientes");
		lblpr.setBounds(301, 300, 214, 14);
		add(lblpr);
		
		JLabel lblfi = new JLabel("Fecha inicio: ");
		lblfi.setBounds(91, 118, 94, 14);
		add(lblfi);
		
		JLabel lblff = new JLabel("Fecha fin: ");
		lblff.setBounds(408, 118, 103, 14);
		add(lblff);
		
		

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("img/diwi.png"));
		lblLogo.setBounds(0, 0, 199, 54);
		add(lblLogo);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 723, 507);
		add(lblFondo);

	}

	public void crearPDF(ArrayList<Venta> array) {
		Document documento = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("reporteSupremo.pdf"));

			documento.open();
			/*
			 * Anchor anchor = new
			 * Anchor("Tabla de los movmientos de "+txtNombre.getText().toUpperCase(),
			 * categoryFont);
			 * 
			 * DottedLineSeparator dottedline = new DottedLineSeparator();
			 * dottedline.setOffset(-2); dottedline.setGap(2f); anchor.add(dottedline);
			 */

			Image img = Image.getInstance("img\\\\diwi.png");
			img.scaleToFit(100, 100);
			documento.add(img);
			documento.add(new Paragraph("DiWi phones S.L"));
			documento.add(new Paragraph("Calle Falsa 123"));
			documento.add(new Paragraph("21021 Alcorcon, Madrid"));
			

			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(new Paragraph("                  Informe de ventas"));
			documento.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(7);

			PdfPCell columnHeader;
			// Fill table rows (rellenamos las filas de la tabla).

			columnHeader = new PdfPCell(new Phrase("Factura"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Fecha"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Cliente"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("DNI"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("Precio Sin IVA"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);

			columnHeader = new PdfPCell(new Phrase("IVA"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			
			columnHeader = new PdfPCell(new Phrase("precio total"));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
			

			table.setHeaderRows(1);
			int stock = 0;
			for (int fila = 0; fila < array.size(); fila++) {
				for (int column = 0; column <7; column++) {
					switch (column) {
					case 0:
						table.addCell(array.get(fila).getFactura()+"");
						System.out.println(array.get(fila).getFactura());
						break;
					case 1:
						table.addCell(array.get(fila).getFechaTotal()+"");
						System.out.println(array.get(fila).getFechaTotal());
						break;
					case 2:
						table.addCell(array.get(fila).getCliente());
						System.out.println(array.get(fila).getCliente());
						break;
					case 3:
						table.addCell(array.get(fila).getDniCliente());
						System.out.println(array.get(fila).getDniCliente());
						break;
					case 4:
						double precioSinIVA=(array.get(fila).getPrecioTotal()/1.21);
						table.addCell(String.format("%.2f", precioSinIVA) + "€");
						System.out.println(precioSinIVA+ "");
						break;
					case 5:
						double precioSinIVA2=(array.get(fila).getPrecioTotal()/1.21);
						double iva = (precioSinIVA2*21)/100;
						table.addCell(String.format("%.2f", iva) + "€");
						System.out.println(iva+ "iva");
						break;
					case 6:
						table.addCell(array.get(fila).getPrecioTotal() + "€");
						System.out.println(array.get(fila).getPrecioTotal() + "total");
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
	
	
	
	public ArrayList<Venta> consultaCompras() {
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
		String fechaInicio = dFormat.format(calendario.getDate());
		String fechaFin = dFormat.format(calendario2.getDate());
		System.out.println(fechaInicio + " " + fechaFin);
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("SELECT Factura, fecha, clientes.Nombre, clientes.dni, PrecioVenta from venta inner join clientes "
					+ "on venta.id_cliente=clientes.ID_cliente where (fecha BETWEEN '"+dFormat.format(calendario.getDate())+"' and '"+dFormat.format(calendario2.getDate())+"') and (venta.id_cliente BETWEEN "+sliderI.getValue() +" and " +sliderf.getValue()+") "
					+ "order by 2");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Venta v = new Venta();
				// guardamos los campos en el objeto modelo
				v.setFactura(registro.getInt("Factura"));
				v.setFechaTotal(registro.getString("Fecha"));
				v.setCliente(registro.getString("clientes.Nombre"));
				v.setDniCliente(registro.getString("clientes.dni"));
				v.setPrecioTotal(registro.getInt("precioVenta"));
				// añadimos modelos al arrayList
				ventas.add(v);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return ventas;
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
}
