package com.aop.employee.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Parametros {

	private List<Parametro> parametros;
	
	public Parametros() {
		
		this.parametros = new ArrayList<Parametro>();
	}

	/**
	 * @return the parametros
	 */
	public List<Parametro> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}
	
	public void addParametro(Parametro parametro) {
		
		if (parametro == null) {
			return;
		}
		
		if(CollectionUtils.isEmpty(this.parametros)) {
			
			this.parametros = new ArrayList<Parametro>();
		}
		
		this.parametros.add(parametro);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Parametros [" + (parametros != null ? "parametros=" + parametros : "") + "]";
	}
	
	public String obtenerDescripcionLog() {

		StringBuffer descripcion = new StringBuffer();

		descripcion.append("\n\t\t Argumentos: \tLa invocación fue realizada con los siguientes argumentos: \n");

		for (Parametro parametro : this.parametros) {

			descripcion.append(parametro.obtenerDescripcionLog());

		}

		return descripcion.toString();

	}
	
}
