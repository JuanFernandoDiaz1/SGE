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

import modelo.BuscarProductoM;
import modelo.Cliente;
import modelo.Compras;
import modelo.Escandallo;
import modelo.EscandalloMaterial;
import modelo.Factura;
import modelo.Material;
import modelo.OrdenesFavM;
import modelo.Personal;
import modelo.Productos;
import modelo.Proveedor;
import modelo.Telefono;
import modelo.Venta;

public class GestionBBDD {

	public ArrayList<Cliente> consulta() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select distinct clientes.id_cliente, dni, nombre, direccion, email, numero "
					+ "from clientes left join telefonos on telefonos.id_cliente = clientes.id_cliente group by dni order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Cliente c1 = new Cliente();
				// guardamos los campos en el objeto modelo
				c1.setId(registro.getInt("clientes.id_cliente"));
				c1.setDni(registro.getString("DNI"));
				c1.setNombre(registro.getString("Nombre"));
				c1.setDireccion(registro.getString("Direccion"));
				c1.setEmail(registro.getString("Email"));
				c1.setTelefono(registro.getInt("numero"));
				// a�adimos modelos al arrayList
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
			ResultSet registro = consulta.executeQuery(
					"select distinct dni, nombre, direccion, email, numero " + "from personal left join telefonos on "
							+ "telefonos.id_personal = personal.id_personal group by dni");

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
			ResultSet registro = consulta.executeQuery("select distinct proveedores.id_proveedor, nombre, nif, direccion, email, numero "
					+ "from proveedores left join telefonos on "
					+ "telefonos.id_proveedor = proveedores.id_proveedor group by nif order by 1");

			while (registro.next()) {
				Proveedor proveedor = new Proveedor();
				proveedor.setId(registro.getInt("proveedores.id_proveedor"));
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
			e.printStackTrace();
		}
		return proveedores;
	}

	public ArrayList<Telefono> consultaTelefono(String tipo, String documento, String entidad) {
		ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta
					.executeQuery("select numero, nombre, " + documento + " from telefonos inner join " + entidad
							+ " on " + "telefonos.id_" + tipo + " = " + entidad + ".id_" + tipo);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Telefono tel = new Telefono();
				// guardamos los campos en el objeto modelo
				tel.setDni(registro.getString(documento));
				tel.setTitular(registro.getString("Nombre"));
				tel.setNumero(registro.getInt("numero"));
				// a�adimos modelos al arrayList
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
			ResultSet registro = consulta.executeQuery(
					"select distinct productos.nombre, productos.descripcion, productos.precio, productos.precioVenta,"
							+ " productos.stock, proveedores.Nif, productos.tipo  from productos inner join proveedores on productos.ID_Proveedor= proveedores.ID_Proveedor");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Productos producto = new Productos();
				// guardamos los campos en el objeto modelo
				producto.setNombre(registro.getString("Nombre"));
				producto.setDescripcion(registro.getString("Descripcion"));
				producto.setPrecio(registro.getInt("Precio"));
				producto.setPrecioVenta(registro.getInt("PrecioVenta"));
				producto.setStock(registro.getInt("Stock"));
				producto.setProveedor(registro.getString("NIF"));
				producto.setTipo(registro.getString("tipo"));
				// a�adimos modelos al arrayList
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

	public ArrayList<Venta> consultaVenta() {
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select factura, fecha, clientes.nombre, clientes.dni,"
					+ " personal.nombre, personal.dni, precioVenta from venta inner join clientes on clientes.ID_Cliente = venta.ID_Cliente"
					+ " inner join personal on venta.ID_Personal = personal.ID_Personal");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Venta venta = new Venta();
				// guardamos los campos en el objeto modelo
				venta.setFactura(registro.getInt("Factura"));
				venta.setFechaTotal(registro.getString("Fecha"));
				venta.setCliente(registro.getString("Clientes.Nombre"));
				venta.setDniCliente(registro.getString("clientes.DNI"));
				venta.setPersonal(registro.getString("personal.nombre"));
				venta.setDniPersonal(registro.getString("personal.DNI"));
				venta.setPrecioTotal(registro.getInt("precioVenta"));
				// a�adimos modelos al arrayList
				ventas.add(venta);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return ventas;
	}
	

	public ArrayList<Compras> consultaCompras() {
		ArrayList<Compras> compras = new ArrayList<Compras>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select factura, fecha, proveedores.nombre, proveedores.nif,"
					+ " personal.nombre, personal.dni, precioCompra from compra inner join proveedores on proveedores.ID_proveedor = compra.ID_proveedor"
					+ " inner join personal on compra.ID_Personal = personal.ID_Personal");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Compras compra = new Compras();
				// guardamos los campos en el objeto modelo
				compra.setFactura(registro.getInt("Factura"));
				compra.setFechaTotal(registro.getString("Fecha"));
				compra.setProveedor(registro.getString("proveedores.Nombre"));
				compra.setNifProveedor(registro.getString("proveedores.NIF"));
				compra.setPersonal(registro.getString("personal.nombre"));
				compra.setDniPersonal(registro.getString("personal.DNI"));
				compra.setPrecioTotal(registro.getInt("precioCompra"));
				// a�adimos modelos al arrayList
				compras.add(compra);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return compras;
	}
	public ArrayList<Factura> consultaFacturaCompra(int factura) {
		ArrayList<Factura> facturas= new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("Select productos_compra.Unidades, productos.Descripcion,"
					+ " productos.Precio, (productos_compra.Unidades*productos.Precio) as importe from productos_compra"
					+ " INNER JOIN productos on productos_compra.id_producto= productos.ID_Producto where id_compra ="+factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Factura f = new Factura();
				f.setCantidad(registro.getInt("productos_compra.Unidades"));
				f.setDescripcion(registro.getString("productos.Descripcion"));
				f.setImporte(registro.getInt("productos.Precio"));
				f.setImporteTotal(registro.getInt("importe"));
				facturas.add(f);
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		return facturas;
	}
	public ArrayList<Factura> consultaFacturaVenta(int factura) {
		ArrayList<Factura> facturas= new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("Select productos_ventas.Unidades, productos.Descripcion,"
					+ " productos.PrecioVenta, (productos_ventas.Unidades*productos.PrecioVenta) as importe from"
					+ " productos_ventas INNER JOIN productos on productos_ventas.id_producto= productos.ID_Producto"
					+ " where id_venta = "+factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Factura f = new Factura();
				f.setCantidad(registro.getInt("productos_ventas.Unidades"));
				f.setDescripcion(registro.getString("productos.Descripcion"));
				f.setImporte(registro.getInt("productos.PrecioVenta"));
				f.setImporteTotal(registro.getInt("importe"));
				facturas.add(f);
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		return facturas;
	}

	public void insertCliente(String nombre, String dni, String direccion, String email) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into clientes (nombre, dni, direccion, email) values ('" + nombre + "', '"
					+ dni + "', '" + direccion + "', '" + email + "')");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
			consulta.executeUpdate("insert into proveedores (nombre, nif, direccion, email) values ('" + nombre + "', '"
					+ nif + "', '" + direccion + "', '" + email + "')");
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
					"insert into telefonos (numero, id_" + tipo + ") values (" + numero + ", " + id + ")");
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
					"insert into productos (nombre, descripcion, precio,precioVenta, stock, id_proveedor, tipo) "
							+ "values ('" + p.getNombre() + "', '" + p.getDescripcion() + "', " + p.getPrecio() + ", "
							+ p.getPrecioVenta() + ", " + p.getStock()
							+ " , (Select id_proveedor from proveedores where NIF ='" + p.getProveedor() + "'), '"+p.getTipo() +"')");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int obtenerIdGeneral(String tipo, String entidad, String documento, String numeroDoc) {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery(
					"select id_" + tipo + " from " + entidad + " where " + documento + "='" + numeroDoc + "'");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt("id_" + tipo);
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
					+ persona.getDni() + "', direccion='" + persona.getDireccion() + "', email= '" + persona.getEmail()
					+ "'" + " where dni = '" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");

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

			int valor = consulta.executeUpdate(
					"update proveedores set nombre = '" + proveedor.getNombre() + "', nif='" + proveedor.getNif()
							+ "', direccion='" + proveedor.getDireccion() + "', email= '" + proveedor.getEmail() + "'"
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
		System.out.println(numero + "************");
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update telefonos set numero = " + tel.getNumero() + " where id_" + tipo
					+ " = '" + id + "' and numero=" + numero);

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
		System.out.println(numero + "************");
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate("update telefonos set numero = " + telefono + " where id_" + tipo
					+ " = (select id_" + tipo + " from " + entidad + " where " + documento + "= '"
					+ tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "' and numero= " + numero + ")");

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

			int valor = consulta.executeUpdate(
					"update productos set nombre = '" + p.getNombre() + "', descripcion = '" + p.getDescripcion()
							+ "' , precio = " + p.getPrecio() + " , precioVenta = " + p.getPrecioVenta() + ", Stock = "
							+ p.getStock() + ", id_proveedor =  (Select id_proveedor from proveedores " + "where NIF ='"
							+ p.getProveedor() + "'), tipo= '"+ p.getTipo() +"' where nombre='" + tabla.getValueAt(tabla.getSelectedRow(), 0)
							+ "' and descripcion='" + tabla.getValueAt(tabla.getSelectedRow(), 1) + "'");

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

			int valor = consulta.executeUpdate(
					"delete from clientes where dni ='" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Cliente borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el cliente", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	public void borrarPersonal(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"delete from personal where dni ='" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "'");

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

			int valor = consulta.executeUpdate(
					"delete from telefonos where id_" + tipo + " = (select id_" + tipo + " from " + entidad + " where "
							+ documento + "= '" + tabla.getValueAt(tabla.getSelectedRow(), 1).toString() + "')");

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

			int valor = consulta.executeUpdate("delete from productos where nombre='"
					+ tabla.getValueAt(tabla.getSelectedRow(), 0).toString() + "'");

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
			while (registro.next()) {
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
			ResultSet registro = consulta
					.executeQuery("select nombre from productos where nombre='" + p.getNombre() + "'");

			// si existe lo que estamos buscando
			if (registro.next()) {
				JOptionPane.showMessageDialog(null, "No introduzcas dos nombres iguales", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				insertProductos(p);
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	public int contarVentas(String operacion, String tipo, String tabla, String documento, JTable tableClientes) {
		int id = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select count(factura) from " + operacion + " where id_" + tipo
					+ "=(select id_" + tipo + " from " + tabla + " where " + documento + "= '"
					+ tableClientes.getValueAt(tableClientes.getSelectedRow(), 1) + "')");

			// si existe lo que estamos buscando
			if (registro.next()) {
				id = registro.getInt(1);
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

	public ArrayList<Venta> listaCompra(int factura) {
		ArrayList<Venta> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery(
					"SELECT Nombre, Unidades FROM productos_ventas inner join productos on productos_ventas.ID_Producto = productos.ID_Producto WHERE ID_Venta="
							+ factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Venta producto = new Venta();

				producto.setProducto(registro.getString("Nombre"));
				producto.setUnidades(registro.getInt("Unidades"));
				// a�adimos modelos al arrayList
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return productos;

	}

	public ArrayList<Compras> listaCompra2(int factura) {
		ArrayList<Compras> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery(
					"SELECT Nombre, Unidades,Precio FROM productos_compra inner join productos on productos_compra.ID_Producto = productos.ID_Producto WHERE ID_compra="
							+ factura);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Compras compra = new Compras();

				compra.setProducto(registro.getString("Nombre"));
				compra.setUnidades(registro.getInt("Unidades"));
				// a�adimos modelos al arrayList
				productos.add(compra);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return productos;

	}

	public int consultaPrecioCompra(String nombre) {
		int precio = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("select precio from productos where nombre='" + nombre + "'");

			if(registro.next()) {
				precio = registro.getInt("precio");
			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD precio", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return precio;
	}
	
	public int consultaPrecioVenta(String nombre) {
		int precio = 0;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			ResultSet registro = consulta.executeQuery("select precioVenta from productos where nombre='" + nombre + "'");

			if(registro.next()) {
				precio = registro.getInt("precioVenta");
			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD precio", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return precio;
	}
	public ArrayList<Escandallo> consultaEscandallo() {
		ArrayList<Escandallo> escandallos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("SELECT id_escandallo, nombre FROM escandallo inner join productos on productos.ID_Producto = escandallo.ID_Producto");

			// si existe lo que estamos buscando
			while (registro.next()) {
				Escandallo escandallo = new Escandallo();

				escandallo.setIdEscandallo(registro.getInt("id_escandallo"));
				escandallo.setProducto(registro.getString("nombre"));
				// a�adimos modelos al arrayList
				escandallos.add(escandallo);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return escandallos;
	}
	public ArrayList<BuscarProductoM> consultaBuscarProducto() {
		ArrayList<BuscarProductoM> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("SELECT compra.fecha, proveedores.Nombre, productos_compra.Unidades, productos.Precio, productos.Stock "
					+ "from compra inner join proveedores on proveedores.ID_Proveedor = compra.id_proveedor inner join productos_compra on compra.Factura = "
					+ "productos_compra.Id_compra INNER JOIN productos on productos_compra.id_producto =  productos.ID_Producto where productos.Nombre='raton' order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("compra.fecha"));
				producto.setPersonal(registro.getString("proveedores.Nombre"));
				producto.setUnidades(registro.getInt("productos_compra.Unidades"));
				producto.setPrecio(registro.getInt("productos.Precio"));
				producto.setStock(registro.getInt("productos.Stock"));
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return productos;
	}
	public void consultaBuscarProductoC(ArrayList<BuscarProductoM> productos, String productoT) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("SELECT compra.fecha, proveedores.Nombre, productos_compra.Unidades, (productos.Precio*productos_compra.Unidades) as precio, productos.Stock "
					+ "from compra inner join proveedores on proveedores.ID_Proveedor = compra.id_proveedor inner join productos_compra on compra.Factura = "
					+ "productos_compra.Id_compra INNER JOIN productos on productos_compra.id_producto =  productos.ID_Producto where productos.Nombre='"+productoT+"' order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("compra.fecha"));
				producto.setPersonal(registro.getString("proveedores.Nombre"));
				producto.setUnidades(registro.getInt("productos_compra.Unidades"));
				producto.setPrecio(registro.getInt("precio"));
				producto.setStock(registro.getInt("productos.Stock"));
				producto.setTipo("Compra");
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	public ArrayList<BuscarProductoM> consultaBuscarProductoV(String productoT) {
		ArrayList<BuscarProductoM> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select venta.fecha, clientes.Nombre, productos_ventas.Unidades, (productos.PrecioVenta* productos_ventas.Unidades) as precio, productos.Stock "
					+ "from venta inner join clientes on clientes.ID_Cliente = venta.Id_cliente inner join productos_ventas on venta.Factura = productos_ventas.ID_Venta"
					+ " INNER JOIN productos on productos_ventas.id_producto =  productos.ID_Producto where productos.Nombre='"+productoT+"' order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("venta.fecha"));
				producto.setPersonal(registro.getString("clientes.Nombre"));
				producto.setUnidades(registro.getInt("productos_ventas.Unidades"));
				producto.setPrecio(registro.getInt("precio"));
				producto.setStock(registro.getInt("productos.Stock"));
				producto.setTipo("Venta");
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}
	
	public void borrarEscandallo(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"delete from escandallo where id_escandallo ='" + tabla.getValueAt(tabla.getSelectedRow(), 0).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Escandallo borrado correctamente");
			} 

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void borrarOrdenes(JTable tabla) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"delete from ordenesfabrica where id_orden ='" + tabla.getValueAt(tabla.getSelectedRow(), 0).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "orden borrado correctamente");
			} 

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	public void borrarEscandalloMaterial(JTable tableEscandallos) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"delete from escandallos_materiales where id_escandallo ='" + tableEscandallos.getValueAt(tableEscandallos.getSelectedRow(), 0).toString() + "'");

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}		
	}
	public ArrayList<Material> consultaMateriales() {
		ArrayList<Material> materiales = new ArrayList<>();
		Material materia;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			ResultSet registro = consulta.executeQuery("SELECT nombre, stock FROM materiales");


			while (registro.next()) {
				materia = new Material();
				
				materia.setNombre(registro.getString("nombre"));
				materia.setStock(registro.getInt("stock"));
				
				materiales.add(materia);

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return materiales;
	}

	public void insertarMaterial(Material mat) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			int valor = consulta.executeUpdate("insert into materiales (nombre, stock) values ('" + mat.getNombre() + "', "
					+ mat.getStock() + ")");
			if(valor == 1) {
				JOptionPane.showMessageDialog(null, "Material insertado correctamente");
			}else {
				JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la insercion", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void borrarMaterial(JTable tableMateriales) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"delete from materiales where nombre ='" + tableMateriales.getValueAt(tableMateriales.getSelectedRow(), 0).toString() + "'");

			if (valor == 1) {
				JOptionPane.showMessageDialog(null, "Material borrado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "No existe el material", "Error", JOptionPane.WARNING_MESSAGE);
			}

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	public ArrayList<OrdenesFavM> consultaOrdenesFav() {
		
			ArrayList<OrdenesFavM> ordenes = new ArrayList<>();
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
				Statement consulta = conexion.createStatement();
				// guarda los regsitros de la tabla que vamos a consultar
				ResultSet registro = consulta.executeQuery("SELECT id_orden, Id_Escandallo, unidades ,personal.nombre, fechaInicio, fechaFin, Estado "
						+ "FROM ordenesfabrica inner join personal on ordenesfabrica.ID_Personal = personal.ID_Personal");

				// si existe lo que estamos buscando
				while (registro.next()) {
					OrdenesFavM orden = new OrdenesFavM();
					orden.setIdOrden(registro.getInt("id_orden"));
					orden.setEscandallo(registro.getInt("id_escandallo"));
					orden.setUnidades(registro.getInt("unidades"));
					orden.setPersonal(registro.getString("personal.nombre"));
					orden.setFechaInicio(registro.getDate("fechaInicio").toString());
					orden.setFechaFin(registro.getDate("fechaFin").toString());
					orden.setEstado(registro.getString("estado"));
					// a�adimos modelos al arrayList
					ordenes.add(orden);

				}
				conexion.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
			return ordenes;
	}
	public ArrayList<Escandallo> consultaVerEscandallo(int id_escandallo) {
		ArrayList<Escandallo> escandallo = new ArrayList<Escandallo>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select productos.Nombre, unidadesMaterial From escandallos_materiales "
					+ "Inner JOIN productos on Escandallos_materiales.id_material = productos.id_producto where id_escandallo = "+id_escandallo);

			// si existe lo que estamos buscando
			while (registro.next()) {
				Escandallo esc = new Escandallo();
				// guardamos los campos en el objeto modelo
				esc.setProducto(registro.getString("productos.nombre"));
				esc.setUnidades(registro.getInt("unidadesMaterial"));
				// a�adimos modelos al arrayList
				escandallo.add(esc);

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return escandallo;
	}
	public void insertOrdenes(OrdenesFavM ordenes) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");

			Statement consulta = conexion.createStatement();
			consulta.executeUpdate("insert into ordenesfabrica (id_escandallo, unidades, id_personal, fechaInicio, fechaFin, Estado) values (" + ordenes.getEscandallo() + ", "
					+ ordenes.getUnidades() + ", (Select id_personal from personal where dni = '"+ordenes.getPersonal()+"'), '" + ordenes.getFechaInicio() + "', '"+ordenes.getFechaFin()+"', '"+ordenes.getEstado()+"')");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void acabarOrden(int recogerDatos) {
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			int valor = consulta.executeUpdate(
					"update ordenesfabrica set Estado = 'Acabado' where id_orden ='"+recogerDatos+"'");
			JOptionPane.showMessageDialog(null, "Estado modificado correctamente");

			conexion.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public Productos consultaProducto(String productoT) {
		Productos p = new Productos();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select tipo from productos where nombre='"+productoT+"'");

			if(registro.next()) {
				p.setNombre(productoT);
				p.setTipo(registro.getString("tipo"));
			}
			
			

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return p;
	}
	
	public ArrayList<BuscarProductoM> consultaBuscarProductoFabrica(String productoT) {
		ArrayList<BuscarProductoM> productos = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select fechafin, unidades, productos.Precio from ordenesfabrica inner JOIN "
					+ "escandallo on ordenesfabrica.ID_Escandallo = escandallo.ID_Escandallo inner join productos on escandallo.ID_Producto = "
					+ "productos.ID_Producto where productos.nombre ='"+productoT+"' order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("fechafin"));
				producto.setPersonal("DiWi Phones");
				producto.setUnidades(registro.getInt("unidades"));
				producto.setPrecio(registro.getInt("productos.precio")*producto.getUnidades());
				producto.setTipo("Fabrica");
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}
	
	public void consultaBuscarProductoVenta(ArrayList<BuscarProductoM> productos, String productoT) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select venta.fecha, clientes.Nombre, productos_ventas.Unidades, productos.PrecioVenta from venta "
					+ "inner join clientes on clientes.ID_Cliente = venta.Id_cliente INNER join productos_ventas on venta.factura = productos_ventas.ID_Venta "
					+ "INNER join productos on productos_ventas.ID_Producto = productos.ID_Producto where productos.nombre='"+productoT+"' order by 1");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("venta.fecha"));
				producto.setPersonal(registro.getString("clientes.Nombre"));
				producto.setUnidades(registro.getInt("productos_ventas.Unidades"));
				producto.setPrecio(registro.getInt("productos.PrecioVenta")*producto.getUnidades());
				producto.setTipo("Venta");
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	

	public void sumarStock(int stock, int id) {
		
			try {
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
				Statement consulta = conexion.createStatement();

				consulta.executeUpdate("update productos set stock = (select stock from productos where ID_Producto = (select ID_Producto from escandallo where id_escandallo="+id+"))+"+stock+" where ID_Producto=(select ID_Producto from escandallo where id_escandallo="+id+")");

			

				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void restarStock(String nombreProducto, int unidades) {
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();

			consulta.executeUpdate("update productos set stock = (select stock from "
					+ "productos where nombre = '"+nombreProducto+"')-"+unidades+" where nombre='"+nombreProducto+"'");

			

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
}
	
	public String nombreEscandallo(int id) {
		String nombre=null;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select nombre from productos inner join escandallo on productos.id_producto = escandallo.id_producto where"
					+ " id_escandallo= " + id);

			// si existe lo que estamos buscando
			while (registro.next()) {
				
				nombre=registro.getString("nombre");
				

			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la BBDD al realizar la consulta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		return nombre;
	}
	
	
	public void stockFabricaSimple(ArrayList<BuscarProductoM> productos, String productoT) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select ordenesfabrica.fechainicio, ordenesfabrica.unidades, escandallos_materiales.unidadesmaterial, productos.Precio "
					+ "from ordenesfabrica inner join escandallo on ordenesfabrica.ID_Escandallo = escandallo.ID_Escandallo inner join escandallos_materiales "
					+ "on escandallo.ID_Escandallo = escandallos_materiales.ID_Escandallo inner join productos on productos.ID_Producto = "
					+ "escandallos_materiales.ID_Material where productos.nombre='"+productoT+"'");

			// si existe lo que estamos buscando
			while (registro.next()) {
				BuscarProductoM producto = new BuscarProductoM();
				producto.setFecha(registro.getString("ordenesfabrica.fechainicio"));
				producto.setPersonal("DiWi Phones");
				producto.setUnidades(registro.getInt("escandallos_materiales.unidadesmaterial")*registro.getInt("ordenesfabrica.unidades"));
				producto.setPrecio(registro.getInt("productos.Precio"));
				producto.setTipo("Fabrica");
				productos.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	public ArrayList<EscandalloMaterial> restarStockMateriales(int idOrden) {
		ArrayList<EscandalloMaterial> materiales = new ArrayList<>();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd", "root", "");
			Statement consulta = conexion.createStatement();
			// guarda los regsitros de la tabla que vamos a consultar
			ResultSet registro = consulta.executeQuery("select productos.nombre, sum(escandallos_materiales.UnidadesMaterial * ordenesfabrica.Unidades) "
					+ "from productos inner join escandallos_materiales on productos.ID_Producto = escandallos_materiales.ID_Material inner join "
					+ "escandallo on escandallos_materiales.ID_Escandallo = escandallo.ID_Escandallo inner join "
					+ "ordenesfabrica on ordenesfabrica.ID_Escandallo = escandallo.ID_Escandallo where ordenesfabrica.ID_orden="+ idOrden+" "
					+ "GROUP by productos.Nombre ");

			// si existe lo que estamos buscando
			while (registro.next()) {
				EscandalloMaterial producto = new EscandalloMaterial();
				producto.setNombre(registro.getString(1));
				producto.setUnidades(registro.getInt(2));
				materiales.add(producto);

			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return materiales;
	}
	
}
