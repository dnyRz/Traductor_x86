package Final;

public class Convertidor {
	public static int aDecimal(String sistema, String numero) {
		int nuevoNumero=0;
		
		if(numero=="") {
			return 0;
		}
		
		if(sistema=="") {//ya es decimal
			nuevoNumero = Integer.parseInt(numero);
		}
		else if(sistema.equals("$")) {//es hexadecimal
			nuevoNumero = Integer.parseInt(numero, 16);
		}
		else if(sistema.equals("@")) {//es octal
			nuevoNumero = Integer.parseInt(numero, 8);
		}
		else if(sistema.equals("%")) {//es binario
			nuevoNumero = Integer.parseInt(numero, 2);
		}
		
		return nuevoNumero;
	}
	
	public static String aHexadecimal(int numero) {//pasarle numero en decimal
		return Integer.toHexString(numero);
	}
	
}
