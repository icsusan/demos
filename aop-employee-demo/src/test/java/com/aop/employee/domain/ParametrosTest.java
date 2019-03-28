package com.aop.employee.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.aop.employee.model.Employee;
import com.aop.employee.util.model.Parametro;
import com.aop.employee.util.model.Parametros;

@RunWith(JUnit4.class)
public class ParametrosTest {

	@Test
	public void test() {


		Parametro parametro = null;
		Parametros parametros = new Parametros();
		int i = 0;
		for (; i < 5; i++) {
			
			parametro = new Parametro("nombre_" + i, "java.lang.String_" + i, "valores_" + i, i);
			parametros.addParametro(parametro);

		}
		
		Employee employee = new Employee(3, "Susan", "Inga");
		parametros.addParametro(new Parametro("employee", Employee.class.getCanonicalName(), employee.toString(), i));
		
		System.out.println(parametros.obtenerDescripcionLog());
		
	}

}
