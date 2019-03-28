package com.aop.employee.aspect;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.aop.employee.util.MensajeLogUtil;
import com.aop.employee.util.model.Parametros;

import java.time.LocalDateTime;

public class MonitorPerformanceInterceptor extends AbstractMonitoringInterceptor {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8331119296024956321L;

	public MonitorPerformanceInterceptor() {
    }

    public MonitorPerformanceInterceptor(boolean useDynamicLogger) {
            setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log) throws Throwable {
        
        String nombreMetodo = invocation.getMethod().getName(); //createInvocationTraceName(invocation);
        String nombreClase = invocation.getMethod().getDeclaringClass().getCanonicalName();
        
        long inicio = System.currentTimeMillis();
        LocalDateTime fechaInicio = LocalDateTime.now();

        try {
        	
            return invocation.proceed();
            
        } finally {
        	
            long fin = System.currentTimeMillis();
            LocalDateTime fechaFin = LocalDateTime.now();
            
            long duracion = fin - inicio;

            StringBuffer mensaje = new StringBuffer();
            mensaje.append("Se ejecutó el método '" + nombreMetodo + "' con el siguiente detalle: ");
            
            MensajeLogUtil.armarDatosMensajeLog(nombreMetodo, nombreClase, fechaInicio, fechaFin, new Integer("" + duracion), mensaje);
    		
            Parametros parametros = MensajeLogUtil.obtenerParametrosParaMensajesLog(invocation);
    		mensaje.append(parametros.obtenerDescripcionLog());
    		
    		log.info(mensaje.toString());
    		
        }
        
    }

}
