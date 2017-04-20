package parser;

//import java.io.File;
import java.util.List;
//import org.eclipse.core.resources.ResourcesPlugin;


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
		//String absolutePathH = "/home/harrison/EclipseProjects/DRAT/src/artifacts/";
		//String absolutePathT = "/Users/troyprince/git/ND2017-DRAT1/DRAT/src/artifacts/";
		String relativePath = "src/artifacts/";
		//how you would find plugin path:
			//String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
			//String relativePath = workspacePath.replaceAll("runtime-EclipseApplication", "/git/ND2017-DRAT1/DRAT/src/drat1/artifacts/");
		//This gets us to the relative path of DRAT for all of us
		//But, we are going to later have to change it so that it is working for the user's plugin
		//The files will be in the launched eclipses's workspace so thats tricky
		filePathName = relativePath;
		//filePathName = absolutePathT;
	}
	
	public void printResult() {
		for(XMLParsableArtifact artifact : artifactList) {
			System.out.println(artifact.id + "  " + artifact.description);
			//Artifacts.put(artifact.id, (T)artifact);
		}		
	}
	
	
}