package com.nscorp.tadsservices.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public abstract class TADSJournalMessage {

	public static <T> T convertXmlToObject(String xml, Class<T> clazz) {
		T unmarshalledObject = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			InputStream stream = new ByteArrayInputStream(xml.getBytes());
			unmarshalledObject = (T) unmarshaller.unmarshal(stream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return unmarshalledObject;
	}
	
	public static <T> String convertObjectToXML(Object objectToMarshal) {
		String xml = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(objectToMarshal.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			//Add line feeds to make it human readable.
			//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//Removes xmlHeader -> <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			
			StringWriter stringwriter = new StringWriter();		
			marshaller.marshal(objectToMarshal, stringwriter);
			xml = stringwriter.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	public abstract String getXML();
}
