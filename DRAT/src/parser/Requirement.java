package parser;

public class Requirement {

	String id;
	String description;
	
	Requirement() {
		
	}

	public Requirement(XMLParsableArtifact artifact) {
		id = artifact.id;
		description = artifact.description;
	}
}