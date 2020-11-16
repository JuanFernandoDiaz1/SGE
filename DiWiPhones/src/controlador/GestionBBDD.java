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
		ArrayList<Cliente> modelos = new ArrayList<Cliente>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "",
					"");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select dni, nombre, direccion, email "
					+ "from clientes");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Cliente c1 = new Cliente();
				// guardamos los campos en el objeto modelo
				c1.setDni(registro.getString("DNI"));
				c1.setNombre(registro.getString("Nombre"));
				c1.setDireccion(registro.getString("Direccion"));
				c1.setEmail(registro.getString("Email"));
				// añadimos modelos al arrayList
				modelos.add(c1);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return modelos;
	}
}
