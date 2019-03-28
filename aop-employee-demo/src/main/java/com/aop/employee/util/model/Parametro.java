package com.aop.employee.util.model;

@SuppressWarnings("rawtypes")
public class Parametro {

	private String nombre;
	private String tipo;
	private Object valor;
	private Class clase;
	private Integer posicion;
	
	public Parametro() {
		
	}
	
	/**
	 * @param nombre
	 * @param tipo
	 * @param valor
	 * @param posicion
	 */
	public Parametro(String nombre, String tipo, Object valor, Integer posicion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		this.posicion = posicion;
	}
	
	/**
	 * @param nombre
	 * @param tipo
	 * @param valor
	 * @param clase
	 * @param posicion
	 */
	public Parametro(String nombre, String tipo, Object valor, Class clase, Integer posicion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		this.clase = clase;
		this.posicion = posicion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the valor
	 */
	public Object getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Object valor) {
		this.valor = valor;
	}

	/**
	 * @return the clase
	 */
	public Class getClase() {
		return clase;
	}

	/**
	 * @param clase the clase to set
	 */
	public void setClase(Class clase) {
		this.clase = clase;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return the posicion
	 */
	public Integer getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Parametro [" 
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (tipo != null ? "tipo=" + tipo + ", " : "") 
				+ (valor != null ? "valor=" + valor + ", " : "")
				+ (clase != null ? "clase=" + clase + ", " : "") 
				+ (posicion != null ? "posicion=" + posicion : "") + "]";
	}

	public String obtenerDescripcionLog() {
		
		String descripcion = 
				"\t\t\t\t     Parametro #" + posicion 
					+ " [" 
						+ (nombre != null ? "nombre=" + nombre + ", " : "")
						+ (tipo != null ? "tipo=" + tipo + ", " : "") 
						+ (valor != null ? "valor=" + valor : "")
					+ "] \n";
		
		return descripcion;
		
	}
	
}
