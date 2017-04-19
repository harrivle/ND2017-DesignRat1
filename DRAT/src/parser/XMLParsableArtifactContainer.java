package parser;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;


public class XMLParsableArtifactContainer {

	//private T t;// = T.getInstnace();
	
	public String artifactName;
	public String artifactPlural;
	
	public String filePathName = "";
	
	public XMLParser xmlParser;
	
	List<XMLParsableArtifact> artifactList = null;
	
	public XMLParsableArtifactContainer(String cls) {	
		
		prepareFilePathName();
		
		if (cls == "DesignDecision") {
			//Artifacts = new HashMap<String, DesignDecision>();
			artifactName = "DesignDecision";
			artifactPlural = "DesignDecisions";
			filePathName = filePathName + "DesignDecision.xml";
		}		
		else if (cls == "Requirement") {
			artifactName = "Requirement";
			artifactPlural = "Requirements";
			filePathName = filePathName + "Requirements.xml";			
		} 
		else if (cls == "TM_Reqs_Design") {
			artifactName = "TMDesign";
			artifactPlural = "TMDesigns";
			filePathName = filePathName + "TM_Requirements_Design.xml";			
		} 
		else if (cls == "TM_Code_Reqs") {
			artifactName = "TMReqCode";
			artifactPlural = "TMReqCodes";
			filePathName = filePathName + "TM_Code_Requirements.xml";		
		} 
		else {
			System.out.println("\n\nType not identified\n\n");
		}
		
		try {
			XMLParser xmlParser = new XMLParser(filePathName, artifactName, artifactPlural);
			//xmlParser.traverseAndPrint();
			artifactList =  xmlParser.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//printResult();
	}
	
	public void prepareFilePathName() {
		String absolutePath = "/home/harrison/EclipseProjects/DRAT/src/artifacts/";
		//String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		//String relativePath = workspacePath.replaceAll("runtime-EclipseApplication", "/git/ND2017-DRAT1/DRAT1/src/drat1/artifacts/");
		//System.out.println(relativePath);		
		//This gets us to the relative path of DRAT1 for all of us
		//But, we are going to late have to change it so that it is working for the user's plugin
		//The files will be in the launched eclipses's workspace so thats tricky
		filePathName = absolutePath;
	}
	
	public void printResult() {
		for(XMLParsableArtifact artifact : artifactList) {
			System.out.println(artifact.id + "  " + artifact.description);
			//Artifacts.put(artifact.id, (T)artifact);
		}		
	}
	
	
}