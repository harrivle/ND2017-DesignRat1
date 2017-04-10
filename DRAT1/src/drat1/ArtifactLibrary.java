package drat1;

import java.util.HashMap;

import org.eclipse.swt.widgets.MessageBox;
import org.xml.sax.SAXException;

public class ArtifactLibrary {
	HashMap<String, DesignDecision> designDecisions = new HashMap<String, DesignDecision>();
	HashMap<String, Requirement> requirments = new HashMap<String, Requirement>();
	
	private static ArtifactLibrary instance = null;
	protected ArtifactLibrary() {
		// Exists only to defeat instantiation.
		
		initializeDesignDecisions();
		initializeRequirements();
		
		testWithDisplay();
	}
	public static ArtifactLibrary getInstance() {
		if(instance == null) {
			instance = new ArtifactLibrary();
	    }
		return instance;
	}
	
	public void initializeDesignDecisions() {
		XMLParsableArtifactContainer rawDesList = new XMLParsableArtifactContainer("DesignDecision");
		for(XMLParsableArtifact artifact : rawDesList.artifactList) {
			designDecisions.put(artifact.getId(), new DesignDecision(artifact));
		}
	}
	
	public void initializeRequirements() {
		XMLParsableArtifactContainer rawReqList = new XMLParsableArtifactContainer("Requirement");
		for(XMLParsableArtifact artifact : rawReqList.artifactList) {
			requirments.put(artifact.getId(), new Requirement(artifact));
		}		
	}
	
	public void testWithDisplay() {
		for(String key : designDecisions.keySet()) System.out.println(key + ": " + designDecisions.get(key).description);
		for(String key : requirments.keySet()) System.out.println(key + ": " + requirments.get(key).description);
	}
	
}
