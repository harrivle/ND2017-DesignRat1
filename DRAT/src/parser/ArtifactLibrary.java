package parser;

import java.util.HashMap;
import java.util.HashSet;

public class ArtifactLibrary {
	HashMap<String, DesignDecision> designDecisions = new HashMap<String, DesignDecision>();
	HashMap<String, Requirement> requirements = new HashMap<String, Requirement>();
	
	//map of design decision ID to set of requirement IDs
	HashMap<String, HashSet<String> > designReqLink = new HashMap<String, HashSet<String> >();
	
	private static ArtifactLibrary instance = null;
	public ArtifactLibrary() {
		// Exists only to defeat instantiation.
		
		initializeDesignDecisions();
		initializeRequirements();
		initializeTraceMatrix();
		
		testWithDisplay();
	}

	public static ArtifactLibrary getInstance() {
		if(instance == null) {
			instance = new ArtifactLibrary();
	    }
		return instance;
	}
	
	public HashMap<String, DesignDecision> getDesignDecisions() {
		return designDecisions;
	}
	
	public HashMap<String, Requirement> getRequirements() {
		return requirements;
	}
	
	public HashMap<String, HashSet<String> > getDesignReqLink(){
		return designReqLink;
	}
	
	public void initializeDesignDecisions() {
		XMLParsableArtifactContainer rawDesList = new XMLParsableArtifactContainer("DesignDecision");
		for(XMLParsableArtifact artifact : rawDesList.artifactList) {
			designDecisions.put(artifact.getId(), new DesignDecision(artifact));
			designReqLink.put(artifact.getId(), new HashSet<String>() );
		}
	}
	
	public void initializeRequirements() {
		XMLParsableArtifactContainer rawReqList = new XMLParsableArtifactContainer("Requirement");
		for(XMLParsableArtifact artifact : rawReqList.artifactList) {
			requirements.put(artifact.getId(), new Requirement(artifact));
		}		
	}
	
	public void initializeTraceMatrix() {
		XMLParsableArtifactContainer rawReqDesignLink = new XMLParsableArtifactContainer("TM_Reqs_Design");
		for(XMLParsableArtifact artifact : rawReqDesignLink.artifactList) {
			designReqLink.get(artifact.description).add(artifact.id);
		}
	}	
	
	public void testWithDisplay() {
		for(String key : designDecisions.keySet()) System.out.println(key + ": " + designDecisions.get(key).description);
		for(String key : requirements.keySet()) System.out.println(key + ": " + requirements.get(key).description);
	}
	
}
