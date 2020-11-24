package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import modelo.Cliente;
import modelo.Personal;
import modelo.Productos;
import modelo.Proveedor;
import modelo.Telefono;

public class GestionBBDD {

	public ArrayList<Cliente> consulta() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select distinct dni, nombre, direccion, email, numero "
					+ "from clientes inner join telefonos on telefonos.id_cliente = clientes.id_cliente group by dni");

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
	
	public ArrayList<Personal> consultaPersonal() {
		ArrayList<Personal> personal = new ArrayList<Personal>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("select distinct dni, nombre, direccion, email, numero "
					+ "from personal inner join telefonos on " + "telefonos.id_personal = personal.id_personal group by dni");
			
			while (registro.next()) {
				Personal empleado = new Personal();
				empleado.setDni(registro.getString("DNI"));
				empleado.setNombre(registro.getString("Nombre"));
				empleado.setDireccion(registro.getString("Direccion"));
				empleado.setEmail(registro.getString("Email"));
				empleado.setTelefono(registro.getInt("numero"));
				personal.add(empleado);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return personal;
	}
	
	public ArrayList<Proveedor> consultaProveedor() {
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("select distinct nombre, nif, direccion, email, numero "
					+ "from proveedores inner join telefonos on " + "telefonos.id_proveedor = proveedores.id_proveedor group by nif");
			
			while (registro.next()) {
				Proveedor proveedor = new Proveedor();
				proveedor.setNombre(registro.getString("Nombre"));
				proveedor.setNif(registro.getString("nif"));
				proveedor.setDireccion(registro.getString("Direccion"));
				proveedor.setEmail(registro.getString("Email"));
				proveedor.setTelefono(registro.getInt("numero"));
				proveedores.add(proveedor);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return proveedores;
	}

	public ArrayList<Telefono> consultaTelefono(String tipo, String documento, String entidad) {
		ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select numero, nombre, "+ documento
					+ " from telefonos inner join "+ entidad +" on " + "telefonos.id_"+ tipo +" = "+entidad+".id_"+tipo);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Telefono tel = new Telefono();
				// guardamos los campos en el objeto modelo
				tel.setDni(registro.getString(documento));
				tel.setTitular(registro.getString("Nombre"));
				tel.setNumero(registro.getInt("numero"));
				// añadimos modelos al arrayList
				telefonos.add(tel);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return telefonos;
	}
	public ArrayList<Productos> consultaProductos() {
		ArrayList<Productos> productos = new ArrayList<Productos>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select distinct productos.nombre, productos.descripcion, productos.precio,"
					+ " productos.stock, proveedores.Nif  from productos inner join proveedores on productos.ID_Proveedor= proveedores.ID_Proveedor");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Productos producto = new Productos();
				// guardamos los campos en el objeto modelo
				producto.setNombre(registro.getString("Nombre"));
				producto.setDescripcion(registro.getString("Descripcion"));
				producto.setPrecio(registro.getInt("Precio"));
				producto.setStock(registro.getInt("Stock"));
				producto.setProveedor(registro.getString("NIF"));
				// añadimos modelos al arrayList
				productos.add(producto);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return productos;
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
	
	public void insertPersonal(String nombre, String dni, String direccion, String email) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into personal (nombre, dni, direccion, email) values ('" + nombre + "', '"
					+ dni + "', '" + direccion + "', '" + email + "')");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la BBDD");
		}
	}
	
	public void insertProveedor(String nombre, String nif, String direccion, String email) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into proveedores (nombre, nif, direccion, email) values ('" + nombre + "', '"+nif
					+ "', '" + direccion + "', '" + email + "')");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la BBDD");
		}
	}
	
	public void insertTelefono(int numero, int id, String tipo, String entidad) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(
					"insert into telefonos (numero, id_" + tipo + ") values (" + numero + ", "+id+ ")");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertProductos(Productos p) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate(
					"insert into productos (nombre, descripcion, precio, stock, id_proveedor) "
					+ "values ('"+p.getNombre()+"', '"+p.getDescripcion()+"', "+ p.getPrecio() +", "+p.getStock()
					+", (Select id_proveedor from proveedores where NIF ='"+p.getProveedor()+"'))");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int obtenerIdGeneral(String tipo, String entidad, String documento, String numeroDoc) {
		int id=0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta
					.executeQuery("select id_" +tipo+ " from "+entidad+ " where " + documento + "='" + numeroDoc+"'");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt("id_"+tipo);
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
	public int obtenerIdPersonal() {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta
					.executeQuery("select id_personal " + "from personal order by id_personal desc limit 1");

			if (registro.next()) {
				id = registro.getInt("id_personal");
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
	public int obtenerIdProveedor() {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta
					.executeQuery("select id_proveedor from proveedores order by id_proveedor desc limit 1");

			if (registro.next()) {
				id = registro.getInt("id_proveedor");
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
	public void modificarPersonal(Personal persona, JTable tabla) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update personal set nombre = '" + persona.getNombre() + "', DNI='"
					+ persona.getDni() + "', direccion='" + persona.getDireccion() + "', email= '" + persona.getEmail() + "'"
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
	
	public void modificarProveedor(Proveedor proveedor, JTable tabla) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update proveedores set nombre = '" + proveedor.getNombre() + "', nif='"+proveedor.getNif()+"', direccion='" + proveedor.getDireccion() + "', email= '" + proveedor.getEmail() + "'"
					+ " where nif = '" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Proveedor modificado correctamente");

			} else {
				JOptionPane.showMessageDialog(null, "No existe el Proveedor que desea modificar", "Error",
						JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarTelefonoV2(Telefono tel, JTable tabla, String tipo, int id) {
		int numero = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
		System.out.println(numero+"************");
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update telefonos set numero = " + tel.getNumero() + " where id_"+tipo+" = '" + id + "' and numero="+ numero);

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Telefono modificado correctamente");

			} else {
				JOptionPane.showMessageDialog(null, "No existe el Telefono que desea modificar", "Error",
						JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarTelefono(int telefono, String tipo, String documento, String entidad, JTable tabla) {
		int numero = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 4).toString());
		System.out.println(numero+"************");
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update telefonos set numero = " + telefono + " where id_" + tipo
					+ " = (select id_" + tipo + " from " + entidad + " where " + documento + "= '"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "' and numero= "+numero+")");

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
	public void modificarProducto(Productos p, JTable tabla) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update productos set nombre = '" + p.getNombre() + "', descripcion = '"+p.getDescripcion()+
					"' , precio = " +p.getPrecio()+", Stock = "+p.getStock()+", id_proveedor =  (Select id_proveedor from proveedores "
					+ "where NIF ='"+p.getProveedor()+"') where nombre='"+tabla.getValueAt(tabla.getSelectedRow(), 0)+"' and descripcion='"+ tabla.getValueAt(tabla.getSelectedRow(), 1)+"'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Producto modificado correctamente");

			} else {
				JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.WARNING_MESSAGE);
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
	public void borrarPersonal(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from personal where dni ='"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");
			
			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Empleado borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el cliente", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	//Juan
	public void borrarProveedor(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from proveedores where nif ='"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");
			
			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Empleado borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el proveedor", "Error", JOptionPane.WARNING_MESSAGE);
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

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	public void borrarProducto(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("delete from productos where nombre='"+tabla.getValueAt(tabla.getSelectedRow(),  0).toString()+"'" );

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public DefaultComboBoxModel cargaProveedores() {
		Connection conexion;
		DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
		listaModelo.addElement("Proveedores");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("Select NIF from proveedores");
			while(registro.next()) {
				listaModelo.addElement(registro.getString("NIF"));
			}
			conexion.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
		}
		return listaModelo;
	}
	
	public void comprobarProductos(Productos p) {
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select nombre from productos where nombre='"+p.getNombre()+"'");

			// si existe lo que estamos buscando
			if (registro.next()) {
				JOptionPane.showMessageDialog(null, "No introduzcas dos nombres iguales", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				insertProductos(p);
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		
	}
	

}
