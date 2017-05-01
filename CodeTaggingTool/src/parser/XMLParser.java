package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class XMLParser {

	XMLInputFactory factory;
	XMLStreamReader reader;
	File xmlFile;    //file to read from
	String singular; //name of artifact to be parsed
	String plural;   //plural version of artifact to be parsed
	
	XMLParser(String filename, String Singular, String Plural) throws FileNotFoundException, XMLStreamException {
		
		xmlFile = new File(filename);
		InputStream is = new FileInputStream(xmlFile);
		factory = XMLInputFactory.newInstance();
		reader = factory.createXMLStreamReader(is);
		singular = Singular; 
		plural = Plural; 
	}
	
	//used to see what is in the xml file while debugging
	public void traverseAndPrint() throws XMLStreamException {
		while(reader.hasNext()) {
			
		    if(reader.hasText()) {
		    	String content = reader.getText();
		    	System.out.println(content);
		    	//System.out.println("next element");
		    }
		    reader.next();
		}
		
	}

	//function built with assistance from:
	//http://www.topjavatutorial.com/java/convert-xml-document-java-object-using-stax-cursor-api/
	public List<XMLParsableArtifact> getList() throws XMLStreamException {
        List<XMLParsableArtifact> artifactList=null;
        XMLParsableArtifact art = null;
        
        boolean isId = false;
        boolean isDesc = false;
        String text = "";
        
        while (reader.hasNext()) {
        	int eventType = reader.getEventType();
			if (eventType == XMLStreamConstants.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (plural.equals(elementName)) {
					artifactList = new ArrayList<>();
				} else if (singular.equals(elementName)) {
					art = new XMLParsableArtifact();
				} else if ("ID".equals(elementName)) {
					isId = true;
				} else if ("Description".equals(elementName)) {
					isDesc = true;
				} else {
				}
			} else if (eventType == XMLStreamConstants.CHARACTERS) {
				if(isId){
        			art.setId(reader.getText());
        			isId = false;
        		}
        		else if(isDesc){
        			art.setDescription(reader.getText());
        			isDesc = false;
                }
				if(reader.hasText())
        			text = reader.getText();
			} else if (eventType == XMLStreamConstants.END_ELEMENT) {
				String elementName = reader.getLocalName();
				if(elementName.equals(singular))
        			artifactList.add(art);
			} else if (eventType == XMLStreamConstants.START_DOCUMENT) {
				artifactList = new ArrayList<>();
			}
 
        	reader.next();
        }
        reader.close();

        return artifactList;
    }

}
