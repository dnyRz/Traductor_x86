package Final;

import java.util.ArrayList;

public class PalabraReservada {
	private static ArrayList<String> palabras = new ArrayList<>();
	
	public static void cargarPalabras() {
		palabras.clear();
		palabras.add("ORG");
		palabras.add("EQU");
		palabras.add("END");
	}
	
	public static boolean exitePalabra(String palabra) {
		cargarPalabras();
		return palabras.contains(palabra);
	}
}
