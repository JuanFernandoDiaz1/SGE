package vista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modelo.BuscarProductoM;

public class OrdenarProductos implements Comparator<BuscarProductoM>{

	public void getOrdenarArrrayList(ArrayList<BuscarProductoM> productos) {
		Collections.sort(productos, new Comparator<BuscarProductoM>() {

			@Override
			public int compare(BuscarProductoM o1, BuscarProductoM o2) {
				return new String(o1.getFecha().concat(o1.getTipo())).compareTo(o2.getFecha().concat(o2.getTipo()));
				
			}
			
		});
	}
	
	
	@Override
	public int compare(BuscarProductoM o1, BuscarProductoM o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
