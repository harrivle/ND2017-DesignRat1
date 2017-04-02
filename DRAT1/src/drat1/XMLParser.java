package drat1;

import java.io.IOException;

import javax.xml.parsers.*;
import org.xml.sax.*;
//import org.xml.sax.helpers.*;
//SAXParser;
//import javax.xml.parsers.SAXParserFactory;

//import java.io.*;
//import java.util.*;


public class XMLParser {
	
	//reference link: https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
	
	SAXParserFactory spf;
	SAXParser saxParser;
	XMLReader xmlReader;
	
	XMLParser() throws SAXException {
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			xmlReader = saxParser.getXMLReader();
		}
		catch(ParserConfigurationException e) {
			System.out.println(e.toString());
		}
		
	}
	
	public void parse(String filename) throws IOException, SAXException {
		xmlReader.parse(filename);
		
	}

}
