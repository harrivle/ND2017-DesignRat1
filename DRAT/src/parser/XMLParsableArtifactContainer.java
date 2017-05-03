package parser;

//import java.io.File;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.resources.ResourcesPlugin;


public abstract class XMLParsableArtifactContainer {
	
	public String artifactName;
	public String artifactPlural;
	public String fileExtentionName;
	public String filePathName;
	
	public XMLParser xmlParser;
	
	List<XMLParsableArtifact> artifactList = null;
	
	public XMLParsableArtifactContainer() {	
	}
	
	public void performParse() {
		prepareFilePathName();
		//System.out.println(filePathName);
		try {
			System.out.println("Getting artifacts from " + filePathName);
			XMLParser xmlParser = new XMLParser(filePathName, artifactName, artifactPlural);
			//xmlParser.traverseAndPrint();
			artifactList =  xmlParser.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepareFilePathName() {
		//String absolutePathH = "/home/harrison/EclipseProjects/DRAT/src/artifacts/";
		String absolutePathT = "/Users/troyprince/git/ND2017-DRAT1/DRAT/src/artifacts/";
		//String relativePath = "src/artifacts/" + fileExtentionName;
		//how you would find plugin path:
		//String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
			//String relativePath = workspacePath.replaceAll("runtime-EclipseApplication", "/git/ND2017-DRAT1/DRAT/src/drat1/artifacts/");
		//This gets us to the relative path of DRAT for all of us
		//But, we are going to later have to change it so that it is working for the user's plugin
		//The files will be in the launched eclipses's workspace so thats tricky
		filePathName = absolutePathT + fileExtentionName;
		//filePathName = absolutePathT;
	}
	
	public void printResult() {
		for(XMLParsableArtifact artifact : artifactList) {
			System.out.println(artifact.id + "  " + artifact.description);
			//Artifacts.put(artifact.id, (T)artifact);
		}		
	}

	abstract void organizeContents();

}