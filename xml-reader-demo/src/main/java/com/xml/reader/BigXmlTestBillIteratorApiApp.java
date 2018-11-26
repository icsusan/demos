package com.xml.reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.codehaus.stax2.XMLInputFactory2;

public class BigXmlTestBillIteratorApiApp {

	private static final String DOCUMENT_NS = "http://www.amdocs.com/amdd/stp/IMPL\\\\\" "
			+ "xmlns=\\\\\"http://www.amdocs.com/amdd/stp/GBF\\\\\" "
			+ "xmlns:xsi=\\\\\"http://www.w3.org/2001/XMLSchema-instance\\\\\" "
			+ "xsi:schemaLocation=\\\\\"http://www.amdocs.com/amdd/stp/GBF ./Root.xsd http://www.amdocs.com/amdd/stp/IMPL ./impl/IMPL_ROOT.xsd";
	private static final String BIGXMLTEST_PREFIX_ROOT_ELEMENT = "cust";
	private static final String BIGXMLTEST_ROOT_ELEMENT = "ROOT";
	private static final String CONTENT_ELEMENT = "BILLS";
	private static final String DATA_ELEMENT = "BILL";
	
	public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
	public static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	public static void main(String args[]) throws Exception {
		
		System.out.println("INICIO " + format.format(new Date()));
		
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		
		String filename = args[0]; //"add.PER.20171000070000737090000000009_R.xml";
		String filepath = args[1]; //"D:\\workspace-sancor\\";
		String outputPath = args[2];; //"D:\\workspace-sancor\\";
		String outputFinPath = args[3];; //"D:\\workspace-sancor\\";
		Long numeroArchivosDivididos = new Long(args[4]);
		
		BigXmlTestBillIteratorApiApp bigXmlTest = new BigXmlTestBillIteratorApiApp();
		
		Long countPerson = bigXmlTest.countBills(filepath, filename);
		Long limit = countPerson / numeroArchivosDivididos;
		Long resto = countPerson % numeroArchivosDivididos;
		if (resto.compareTo(0l) > 0) {
			limit = limit + 1;
		}
		
		bigXmlTest.startSplitting(filepath, filename, outputPath, outputFinPath, limit);
		System.out.println("FIN " + format.format(new Date()));
		
	}

	public Long countBills(String filepath, String filename) throws Exception {
		
		Long countPerson = 0l;

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
       	InputStream in = new FileInputStream(filepath + filename);
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        streamReader.nextTag(); // Advance to "ROOT" element
        streamReader.nextTag(); // Advance to "BILLS" element

        while (streamReader.hasNext()) {
        	
            if (streamReader.isStartElement()) {
            	if (streamReader.getLocalName().equals(DATA_ELEMENT)) {
            		countPerson++;
            	}
            }
            
            streamReader.next();
        }
		
//		XMLEventReader xmlEventReader = ((XMLInputFactory2) XMLInputFactory.newInstance())
//		.createXMLEventReader(new File(filepath + filename).toURI().toURL());

//		XMLEvent event = null; 									
		
//		try {
//			
//			while (xmlEventReader.hasNext()) {
//				
//				event = xmlEventReader.nextEvent();
//
//				if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(CONTENT_ELEMENT)) {
//					
//					event = xmlEventReader.nextEvent();
//
//					while (!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(CONTENT_ELEMENT))) {
//
//						if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(DATA_ELEMENT)) {
//							countPerson++;
//						}
//												
//					}
//					
//				}
//				
//			}
//			
//		} finally {
//			
//			xmlEventReader.close();
//			
//		}
		
        System.out.println("countPerson = " + countPerson);
		return countPerson;
		
	}
	
	public void startSplitting(String filepath, String filename, String outputPath, String outputFinPath, Long limit) throws Exception {
		
		XMLEventReader xmlEventReader = ((XMLInputFactory2) XMLInputFactory.newInstance())
				.createXMLEventReader(new File(filepath + filename).toURI().toURL());

		// No validation. Woodstox does not support validation using the iterator API.
		// A quick workaround would be to parse the file first using the cursor API, just for validation,
		// and then parse it a second time using the iterator API.

		long countPerson = 0;
		int fileNumber = 0;
		int dataRepetitions = 0;
		
		Map<String, Object> result = openOutputFileAndWriteHeader(++fileNumber, filename, outputPath, outputFinPath); // Prepare first file
		XMLEventWriter xmlEventWriter = (XMLEventWriter) result.get("writer");
		String outputFinFilename = (String) result.get("outputFinFilename");
		XMLEvent event = null; 									
		
		try {
			
			while (xmlEventReader.hasNext()) {
				
				event = xmlEventReader.nextEvent();

				if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(CONTENT_ELEMENT)) {
					
					event = xmlEventReader.nextEvent();

					while (!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(CONTENT_ELEMENT))) {

						if (dataRepetitions != 0 && event.isStartElement()
								&& event.asStartElement().getName().getLocalPart().equals(DATA_ELEMENT)
								&& countPerson % limit == 0){
								
							xmlEventWriter.close(); // Also closes any open Element(s) and the document
							
							System.out.println("outputFinFilename = " + outputFinFilename);
							BufferedWriter writer = new BufferedWriter(new FileWriter(outputFinFilename));
							writer.write(" ");
							writer.close();
							
							result = openOutputFileAndWriteHeader(++fileNumber, filename, outputPath, outputFinPath); // Continue with next file
							xmlEventWriter = (XMLEventWriter) result.get("writer");
							outputFinFilename = (String) result.get("outputFinFilename");
							dataRepetitions = 0;
							
						}
						
						// Write the current event to output
						xmlEventWriter.add(event);
						event = xmlEventReader.nextEvent();

						if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(DATA_ELEMENT)) {
							dataRepetitions++;
							countPerson++;
						}
												
					}
					
				}
				
			}
			
		} finally {
			
			System.out.println("outputFinFilename = " + outputFinFilename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFinFilename));
			writer.write(" ");
			writer.close();
			
			xmlEventReader.close();
			xmlEventWriter.close();
			
		}
		
	}

	private Map<String, Object> openOutputFileAndWriteHeader(int fileNumber, String filename, String outputPath,
			String outputFinPath) throws Exception {
		
		XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		xmlOutputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		
		String path = outputPath;
		
		//add.PER.20161000170000247790000000001_R.xml
		String index = String.format("%03d", fileNumber);
		String outputFile = filename;
		outputFile = outputFile.replace(".xml", "");
		outputFile = outputFile.concat("_").concat("" + index).concat(".xml");
		 
		OutputStream outputStream = new FileOutputStream(new File(path, outputFile));
		
		XMLEventWriter writer = xmlOutputFactory.createXMLEventWriter(outputStream, null);
		writer.add(xmlEventFactory.createStartDocument());

		QName qname = new QName(DOCUMENT_NS, BIGXMLTEST_ROOT_ELEMENT, BIGXMLTEST_PREFIX_ROOT_ELEMENT);
		StartElement startElement = xmlEventFactory.createStartElement(qname, null, null);
		writer.add(startElement);
		
		qname = new QName("", CONTENT_ELEMENT, "");
		startElement = xmlEventFactory.createStartElement(qname, null, null);
		writer.add(startElement);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("writer", writer);
		result.put("outputFinFilename", outputFinPath.concat(outputFile.replace(".xml", ".fin")));
		
		return result;
		
	}
	
}