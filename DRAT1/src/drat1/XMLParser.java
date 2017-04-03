package drat1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.*;

public class XMLParser {

	XMLInputFactory factory;
	XMLStreamReader reader;
	
	XMLParser() throws FileNotFoundException, XMLStreamException {
		
		//use recourse plugin to get workspace path
		//append file path to recourse path
		//make sure that the files are in the new plugin's eclipse
		
		String filename =  "/Users/troyprince/git/ND2017-DRAT1/DRAT1/src/drat1/artifacts/DesignDecision.xml";
		File xmlFile = new File(filename);
		InputStream is = new FileInputStream(xmlFile);
		factory = XMLInputFactory.newInstance();
		reader = factory.createXMLStreamReader(is);
	}
	
	public void parse() throws XMLStreamException {
		while(reader.hasNext()) {
		    if(reader.hasText()) {
		    	System.out.println(reader.getText());
		    }
		    reader.next();
		}
		
	}
	/*
	public void parse(String filename) throws IOException, SAXException {
		reader.parse(filename);
		while(reader.hasNext() ) {
			
		}
	}*/

}
