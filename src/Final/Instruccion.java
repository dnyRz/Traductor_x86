package Final;

public class Instruccion {
	private String etiqueta;
	private String operacion;
	private String operando;
	
	public Instruccion(String etiqueta, String operacion, String operando) {
		if(!fijaEtiqueta(etiqueta)) {
			this.etiqueta="";
		}
		if(!fijaOperacion(operacion)) {
			this.operacion = "";
		}
		if(!fijaOperando(operando)) {
			this.operando = "";
		}
	}
	
	public boolean fijaEtiqueta(String e){
		if(e!="") {
			etiqueta = e;
			return true;
		}
		return false;
	}
	
	public boolean fijaOperacion(String o) {
		if(o!="") {
			operacion = o;
			return true;
		}
		return false;
	}
	
	public boolean fijaOperando(String o) {
		if(o!="") {
			operando = o;
			return true;
		}
		return false;
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
