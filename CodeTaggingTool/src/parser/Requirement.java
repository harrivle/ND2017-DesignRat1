package parser;

public class Requirement {

	public String id;
	public String description;
	
	Requirement() {
		
	}

	public Requirement(XMLParsableArtifact artifact) {
		id = artifact.id;
		description = artifact.description;
	}
}