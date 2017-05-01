package parser;

public class DesignDecision {

	String id;
	String description;

	DesignDecision() {

	}

	public DesignDecision(XMLParsableArtifact artifact) {
		id = artifact.id;
		description = artifact.description;
	}

}
