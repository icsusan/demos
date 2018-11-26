package com.xml.reader;

public class CountApp {

	public static void main(String[] args) {

		Long numeroArchivosDivididos = 1000l;
		Long countPerson = 546486000l;
		Long limit = countPerson / numeroArchivosDivididos;
		Long resto = countPerson % numeroArchivosDivididos;
		
		System.out.println("limit 1 = " + limit);
		System.out.println("resto = " + resto);
		
		if (resto.compareTo(0l) > 0) {
			limit = limit + 1;
		}
		
		System.out.println("limit 2 = " + limit);
		
	}

}
