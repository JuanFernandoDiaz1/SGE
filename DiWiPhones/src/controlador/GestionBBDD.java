package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import modelo.Cliente;

public class GestionBBDD {

	public ArrayList<Cliente> consulta() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select dni, nombre, direccion, email, numero "
					+ "from clientes inner join telefonos on " + "telefonos.id_cliente = clientes.id_cliente");

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
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into clientes (nombre, dni, direccion, email) values ('" + nombre + "', '"
					+ dni + "', '" + direccion + "', '" + email + "')");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la BBDD");
		}
	}

	public int obtenerIdCliente() {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta
					.executeQuery("select id_cliente " + "from clientes order by id_cliente desc limit 1");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt("id_cliente");
			} else {
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
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(
					"insert into telefonos (numero, id_" + tipo + ") values (" + numero + ", " + id + ")");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la telefono");
		}
	}

	public void modificarCliente(Cliente cli, JTable tabla) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update clientes set nombre = '" + cli.getNombre() + "', DNI='"
					+ cli.getDni() + "', direccion='" + cli.getDireccion() + "', email= '" + cli.getEmail() + "'"
					+ " where dni = '" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");

			} else {
				JOptionPane.showMessageDialog(null, "No existe el cliente que desea modificar", "Error",
						JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarTelefono(int telefono, String tipo, String documento, String entidad, JTable tabla) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update telefonos set numero = " + telefono + " where id_" + tipo
					+ " = (select id_" + tipo + " from " + entidad + " where " + documento + "= '"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "')");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Telefono modificado correctamente");

			} else {
				JOptionPane.showMessageDialog(null, "Error con el telefono", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void borrarCliente(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from clientes where dni ='"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");
			
			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Cliente borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el cliente", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void borrarTel(String tipo, String documento, String entidad, JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from telefonos where id_"+tipo+" = (select id_" + tipo + " from " + entidad + " where " + documento + "= '"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "')");
			
			if (valor == 1) {
				JOptionPane.showMessageDialog(null, " Telefono borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el cliente", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

}
