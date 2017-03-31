package drat1;

import java.util.Map;

public class ArtifactLibrary {
	private static ArtifactLibrary instance = null;
	protected ArtifactLibrary() {
		// Exists only to defeat instantiation.
	}
	public static ArtifactLibrary getInstance() {
		if(instance == null) {
			instance = new ArtifactLibrary();
	    }
		return instance;
	}
	
	public Map<String, DesignDecision> designDecisions;
	public Map<String, Requirements> requirements;
	
}
