package Final;

import java.util.ArrayList;

public class AdminArchivo {
	private DAOArchivo dao;
	private ArrayList<String> archivoLista; //cada linea del archivo es un elemento de la lista
	
	public AdminArchivo(String d, String n) {
		dao = new DAOArchivo(d, n);
		archivoLista = new ArrayList<String>();
	}
	
	public void cargarArchivo() {
		dao.cargarArchivo(archivoLista);
	}
	
	public void guardarArchivo(String cadena) {
		dao.guardarArchivo(cadena);
	}
	
	public String dameLinea(int numLinea) {
		if(numLinea>=0 && numLinea<archivoLista.size()) {
			return archivoLista.get(numLinea);
		}
		return "";
	}
	
	public ArrayList<String> dameLineas() {
		return archivoLista;
	}
	
	public int dameTotalLineas() {
		return archivoLista.size();
	}
	
	public void imprimirInfo() {
		int i, totalDatos;
		for(i=0,totalDatos=archivoLista.size(); i<totalDatos; i++) {
			System.out.println(dameLinea(i));
		}
	}
}
