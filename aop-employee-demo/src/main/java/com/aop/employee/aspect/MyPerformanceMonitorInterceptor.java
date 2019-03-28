package com.aop.employee.aspect;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.aop.employee.model.Parametro;
import com.aop.employee.model.Parametros;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.List;

public class MyPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8331119296024956321L;

	public MyPerformanceMonitorInterceptor() {
    }

    public MyPerformanceMonitorInterceptor(boolean useDynamicLogger) {
            setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log) throws Throwable {
        
        String nombreMetodo = invocation.getMethod().getName(); //createInvocationTraceName(invocation);
        String nombreClase = invocation.getMethod().getDeclaringClass().getCanonicalName();
        
        long inicio = System.currentTimeMillis();
        Date fechaInicio = new Date();

        try {
        	
            return invocation.proceed();
            
        } finally {
        	
            long fin = System.currentTimeMillis();
            Date fechaFin = new Date();
            
            long time = fin - inicio;
            
            StringBuffer mensaje = new StringBuffer();
            mensaje.append("Se ejecutó el método '" + nombreMetodo + "' con el siguiente detalle: ");
            
    		mensaje.append("\n\t\t Clase: 	'").append(nombreClase).append("'");
    		mensaje.append("\n\t\t Método: 	'").append(nombreMetodo).append("'");
    		mensaje.append("\n\t\t Inicio: 	'").append(fechaInicio).append("'");
    		mensaje.append("\n\t\t Fin: 		'").append(fechaFin).append("'");
    		mensaje.append("\n\t\t Duración: 	'").append(time).append(" ms'");
    		
            Parametros parametros = obtenerParametros(invocation);
    		mensaje.append(parametros.obtenerDescripcionLog());
    		
    		log.info(mensaje.toString());
    		
        }
        
    }
    
    
    public Parametros obtenerParametros(MethodInvocation invocacion) {
    	
    	Parametro parametro = null;
    	Parametros parametros = null;
    	
    	String [] tiposParametros = null;
    	String [] nombresParametros = null;
    	Object [] valoresParametros = null;
    	
    	int cantidadParametros = invocacion.getArguments().length;
    	
    	if (cantidadParametros > 0) {
    		
    		parametros = new Parametros();
    		
        	nombresParametros = obtenerNombresDeParametros(invocacion, cantidadParametros);
        	tiposParametros = obtenerTiposDeParametros(invocacion, cantidadParametros);
        	valoresParametros = obtenerValoresDeParametros(invocacion, cantidadParametros);
        	
    		for (int i = 0; i < cantidadParametros; i++) {
    			
    			parametro = new Parametro(nombresParametros[i], tiposParametros[i], valoresParametros[i], i);
    			parametros.addParametro(parametro);
    			
    		}
    		
    	}
    
    	return parametros;
    
    }
    
    public String [] obtenerTiposDeParametros(MethodInvocation invocacion, int cantidadParametros) {
    	
		Method metodo = invocacion.getMethod();
		Parameter[] parametros = metodo.getParameters();
		String [] tiposParametros = new String[cantidadParametros];
		String tipoParametro = null;
		
		int i = 0;
		for(Parameter parametro : parametros) {
			
			tipoParametro = parametro.getType().getCanonicalName();
			tiposParametros[i] = tipoParametro;
			i++;
		}
		
		return tiposParametros;

    }
    
    public String [] obtenerNombresDeParametros(MethodInvocation invocacion, int cantidadParametros) {
    	
		Method metodo = invocacion.getMethod();
		Parameter[] parametros = metodo.getParameters();
		String [] nombresParametros = new String[cantidadParametros];
		String nombreParametro = null;
		
		int i = 0;
		for(Parameter parametro : parametros) {
			
			nombreParametro = parametro.getName();
			nombresParametros[i++] = nombreParametro;
		}
		
		return nombresParametros;

    }
    
    
    public Object[] obtenerValoresDeParametros(MethodInvocation invocacion, int cantidadParametros) {
    	
		int i = 0;
		
		Object[] valoresParametros = null;
		Object[] valores = invocacion.getArguments();

		if (valores != null && valores.length > 0) {
			
			valoresParametros = new Object[cantidadParametros];
			for(Object valorParametro : valores) {
					
				valoresParametros[i] = valorParametro;
				i++;
				
			}
			
		}
		
		return valoresParametros;
    	
    }
    
}
