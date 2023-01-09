package Final;

import java.util.ArrayList;

public class AdminLineaCodigo {
	ArrayList<String> lineas;
	private final String HEXADECIMAL = "$";
	private final String DECIMAL = "";
	private final String OCTAL = "@";
	private final String BINARIO = "%";
	
	private final String SIMBOLO_IMM = "#";
	private final String SIMBOLO_DIR = "DIR";
	private final String SIMBOLO_INH = "INH";
	private final String SIMBOLO_EXT = "EXT";
	
	private String operacion;
	private String operando;
	private String etiqueta;
	
	public AdminLineaCodigo(ArrayList<String> lineas){
		this.lineas = lineas;
		operando = operacion = etiqueta = "";
		//analizarLineas();
	}
	
	public void analizarLineas() {
		int i, tam;
		tam = lineas.size();
		for(i=0; i<tam; i++) {
			//System.out.println(lineas.get(i));
			separarLinea(lineas.get(i));
		}
	}
	
	public void separarLinea(String linea) {
//		System.out.println("Analizando linea: " + linea);
		//int i, tam;
		String a[]; //para guardar etiqueta y resto (operacion, operando, ...) de la linea
		String b[]; //para guardar operacion y operando ya separados
		String etiqueta, resto;
		String operacion, operando;
		
		this.etiqueta = this.operacion = this.operando = "";
		etiqueta = resto = operacion = operando = "";
		a = linea.split("	");
		
		if(a.length==2) {
			etiqueta = a[0];
			resto = a[1];
			
			this.etiqueta = etiqueta;
			
			b = resto.split(" ");
			if(b.length==2) {
				operacion = b[0];
				operando = b[1];
				
				this.operacion = operacion;
				this.operando = operando;
			}
			else {//hay instrucciones que no tienen operando
				operacion = b[0];
				
				this.operacion = operacion;
			}
		}
		else {
			this.etiqueta = linea;
		}
	}
	
	public String dameNumero(String operando) {//te da el numero solo sin # ni sistema de numeracion
		operando = operando.replace("#", ""); //para quitar el modo immediato
		operando = operando.replace(HEXADECIMAL, "");
		operando = operando.replace(OCTAL, "");
		operando = operando.replace(BINARIO, "");
		return operando;
	}
	
	public String dameSistemaNumeracion(String operando) {
		if(operando.equals("")) {
			return "No tiene";
		}
		else if(operando.contains(HEXADECIMAL)) {
			return HEXADECIMAL;
		}
		else if(operando.contains(OCTAL)) {
			return OCTAL;
		}
		else if(operando.contains(BINARIO)) {
			return BINARIO;
		}
		return DECIMAL;
	}
	
	public String dameModoDireccionamiento(String operacion, String operando) {
		String modo, numero, sistema;
		int numeroDecimal;
		
		sistema = dameSistemaNumeracion(operando);
		numero = dameNumero(operando);
		numeroDecimal = Convertidor.aDecimal(sistema, numero);
		
		//Extras mejorar esto
		if(operacion.equals("INCA")) {
			return "INH";
		}
		
		
		
		
		modo = "";
		if(operacion.equals("BNE") || operacion.equals("LBNE")) { //de momento solo tengo BNE y LBNE como relativo y no tiene ningun otro modo(los REL solo tienen REL)
			modo = "REL";
		}
		else if(operacion.contains(",")) {//de momento solo tengo IDX para los de la forma
			modo = "IDX"; //faltan los rangos de este
		}
		else if(operando.equals("")) {
			modo = SIMBOLO_INH;
		}
		else if((operando.contains(SIMBOLO_IMM))) {
			modo = "IMM";//SIMBOLO_IMM;
		}
		else if(numeroDecimal>=0 && numeroDecimal<=255) {
			modo = SIMBOLO_DIR;
		}
		else if(numeroDecimal>=-32768 && numeroDecimal<=65535) {
			modo = SIMBOLO_EXT;
		}
		
		return modo;
	}
	
	public ArrayList<String> dameLineas(){
		return lineas;
	}
	
	public String dameEtiqueta() {
		return etiqueta;
	}
	
	public String dameOperacion() {
		return operacion;
	}
	
	public String dameOperando() {
		return operando;
	}
}
