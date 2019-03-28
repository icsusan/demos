package com.aop.employee.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.aop.employee.util.model.Parametro;

@RunWith(JUnit4.class)
public class ParametroTest {

	@Test
	public void test() {


		Parametro parametro = new Parametro("nombres", "java.lang.String", "Valores....", 0);
		System.out.println(parametro.obtenerDescripcionLog());
		
	}

}
