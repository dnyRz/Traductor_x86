package Final;

import java.util.ArrayList;

public class AdminMnemonico {
	ArrayList<Mnemonico> mnemonicos;
	DAOMnemonico dao;
	
	public AdminMnemonico() {
		mnemonicos = new ArrayList<Mnemonico>();
		dao = new DAOMnemonico();
	}
	
	public void cargarDatos() {
		dao.cargarDatos(mnemonicos);
	}
	
	public ArrayList<Integer> dameConjuntoInstrucciones(String instruccion){//todas las posibilidades para un demonico(diferentes modos de doreccinamiento)
		ArrayList<Integer> lista = new ArrayList<Integer>();
		String s;
		int i, totalDatos;
		for(i=0,totalDatos=mnemonicos.size(); i<totalDatos; i++) {
			s = mnemonicos.get(i).dameMnemonico();
			if(instruccion.equals(s)) {
				lista.add(i);
			}
		}
		return lista;
	}
	
	public ArrayList<String> dameModosDireccionamiento(String instruccion){//todas las posibilidades para un demonico(diferentes modos de doreccinamiento)
		ArrayList<String> lista = new ArrayList<>();
		String s;
		int i, totalDatos;
		for(i=0,totalDatos=mnemonicos.size(); i<totalDatos; i++) {
			s = mnemonicos.get(i).dameMnemonico();
			if(instruccion.equals(s)) {
				lista.add(mnemonicos.get(i).dameModoDireccionamiento());
			}
		}
		return lista;
	}
	
	public boolean existeMnemonico(String m) {
		return dameConjuntoInstrucciones(m).size()>0;
	}
	
	public int dameLongitudInstruccion(String mn, String modo) {
		ArrayList<Integer> lista = dameConjuntoInstrucciones(mn);
		int i, tamanio, indice;
		boolean existeMn, existeModo;
		
		tamanio = lista.size();
		if(tamanio>0) {
			for(i=0; i<tamanio; i++) {
				existeMn = existeModo = false;
				indice = lista.get(i);
				if(mnemonicos.get(indice).dameMnemonico().equals(mn)) {
					existeMn = true;
				}
				if(mnemonicos.get(indice).dameModoDireccionamiento().equals(modo)) {
					existeModo = true;
				}
				if(existeMn && existeModo) {
					return mnemonicos.get(indice).dameLongitudInstruccion();
				}
			}
		}
		return 0;
	}
	
	public String dameCodigoOperacion(String mn, String modo) {
		ArrayList<Integer> lista = dameConjuntoInstrucciones(mn);
		int i, tamanio, indice;
		boolean existeMn, existeModo;
		
		tamanio = lista.size();
		if(tamanio>0) {
			for(i=0; i<tamanio; i++) {
				existeMn = existeModo = false;
				indice = lista.get(i);
				if(mnemonicos.get(indice).dameMnemonico().equals(mn)) {
					existeMn = true;
				}
				if(mnemonicos.get(indice).dameModoDireccionamiento().equals(modo)) {
					existeModo = true;
				}
				if(existeMn && existeModo) {
					return mnemonicos.get(indice).dameCodigoOperacion();
				}
			}
		}
		
		return "";
	}
	
	public ArrayList<Mnemonico> dameListaMneminicos(){
		return mnemonicos;
	}
	
	public void imprimirInfo() {
		int i, totalDatos;
		for(i=0,totalDatos=mnemonicos.size(); i<totalDatos; i++) {
			System.out.println("Mnemonico: " + mnemonicos.get(i).dameMnemonico());
		}
	}
}
