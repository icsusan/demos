package com.aop.employee.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import com.aop.employee.util.model.Parametro;
import com.aop.employee.util.model.Parametros;

public class MensajeLogUtil {

	
	public static void armarDatosMensajeLog(String nombreMetodo, String nombreClase, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, Integer duracion, StringBuffer mensaje) {
		
		String formatoFecha = "EEEE dd-MM-yyyy HH:mm:ss.SSS";
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatoFecha);
		
		mensaje.append("\n\t\t Clase: 	'").append(nombreClase).append("'");
		mensaje.append("\n\t\t Método: 	'").append(nombreMetodo).append("'");
		
		if (fechaInicio != null) {
			mensaje.append("\n\t\t Inicio: 	'").append(fechaInicio.format(formateador)).append("'");
		}
		
		if (fechaFin != null) {
			mensaje.append("\n\t\t Fin: 		'").append(fechaFin.format(formateador)).append("'");
		}
		
		if (duracion != null) {
			mensaje.append("\n\t\t Duración: 	'").append(duracion).append(" ms'");
		}
		
	}
	
    public static Parametros obtenerParametrosParaMensajesLog(MethodInvocation invocacion) {
    	
    	Method metodo = invocacion.getMethod();
		Parameter[] parametrosMetodo = metodo.getParameters();
		Object[] valoresMetodo = invocacion.getArguments();
		
		int cantidadParametros = invocacion.getArguments().length;
		
		Parametros parametros = armarParametros(parametrosMetodo, valoresMetodo, cantidadParametros);
		return parametros;
    
    }
    
    public static Parametros armarParametros(Parameter[] parametrosMetodo, Object[] valoresMetodo, int cantidadParametros) {
    	
		String tipoParametro = null;
		String nombreParametro = null;
		Object valorParametro = null;
		
		Parameter parametroMetodo = null;
		Parametro parametro = null;
    	Parametros parametros = new Parametros();

		int i = 0;
		while(i < cantidadParametros) {
			
			valorParametro = valoresMetodo[i];

			parametroMetodo = parametrosMetodo[i];
			tipoParametro = parametroMetodo.getType().getCanonicalName();
			nombreParametro = parametroMetodo.getName();

			parametro = new Parametro(nombreParametro, tipoParametro, valorParametro, i);
			parametros.addParametro(parametro);
			
			i++;
		}
		
    	return parametros;
    
    }
    
    @SuppressWarnings("rawtypes")
	public static Parametros obtenerParametrosParaExcepcionesLog(JoinPoint joinPoint) {

    	CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    	
		String [] nombresParametrosMetodo = codeSignature.getParameterNames();
		Class [] tipoParametrosMetodo = codeSignature.getParameterTypes();
		
		Object [] valoresMetodo = joinPoint.getArgs();

		String tipoParametro = null;
		String nombreParametro = null;
		Object valorParametro = null;
		
		Parametro parametro = null;
    	Parametros parametros = new Parametros();

		int cantidadParametros = valoresMetodo.length;
		int i = 0;
		while(i < cantidadParametros) {
			
			valorParametro = valoresMetodo[i];
			tipoParametro = tipoParametrosMetodo[i].getCanonicalName();
			nombreParametro = nombresParametrosMetodo[i];

			parametro = new Parametro(nombreParametro, tipoParametro, valorParametro, i);
			parametros.addParametro(parametro);
			
			i++;
		}
		
		return parametros;
    
    }
    
}
