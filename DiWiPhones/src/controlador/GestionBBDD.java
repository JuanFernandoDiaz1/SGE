package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.Cliente;

public class GestionBBDD {

	public ArrayList<Cliente> consulta() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root",
					"");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select dni, nombre, direccion, email, numero "
					+ "from clientes inner join telefonos on "
					+ "telefonos.id_cliente = clientes.id_cliente");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Cliente c1 = new Cliente();
				// guardamos los campos en el objeto modelo
				c1.setDni(registro.getString("DNI"));
				c1.setNombre(registro.getString("Nombre"));
				c1.setDireccion(registro.getString("Direccion"));
				c1.setEmail(registro.getString("Email"));
				c1.setTelefono(registro.getInt("numero"));
				// añadimos modelos al arrayList
				clientes.add(c1);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return clientes;
	}
	
	public void insertCliente(String nombre, String dni, String direccion, String email) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root",
					"");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(
					"insert into clientes (nombre, dni, direccion, email) values ('" + nombre + "', '"
							+ dni + "', '"
									+ direccion + "', '"
											+ email + "')");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la BBDD");
		}
	}
	
	public int obtenerIdCliente() {
		int id=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root",
					"");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select id_cliente "
					+ "from clientes order by id_cliente desc limit 1");

			// si existe lo que estamos buscando
			if(registro.next()) {
				id=registro.getInt("id_cliente");
			}else {
				System.out.println("Error");
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return id;
	}
	public void insertTel(String tipo, int numero, int id) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root",
					"");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(
					"insert into telefonos (numero, id_"+tipo+") values ("+ numero + ", "+ id+")");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la telefono");
		}
	}
}
