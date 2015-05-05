package lights;

public class LineaTrafico {
	
	Integer numeroCarros;
	String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumeroCarros() {
		return numeroCarros;
	}

	public void setNumeroCarros(Integer numeroCarros) {
		this.numeroCarros = numeroCarros;
	}

	public LineaTrafico(Integer numeroCarros, String nombre) {
		super();
		this.numeroCarros = numeroCarros;
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "LineaTrafico [numeroCarros=" + numeroCarros + ", nombre="
				+ nombre + "]";
	}
	
	
	

}
