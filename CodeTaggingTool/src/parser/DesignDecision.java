package parser;

import java.io.Serializable;

public class DesignDecision {

	String id;
	String description;

	DesignDecision() {

	}

	public DesignDecision(XMLParsableArtifact artifact) {
		id = artifact.id;
		description = artifact.description;
	}

	public DesignDecision(String idinput, String desc) {
		id = idinput;
		description = desc;

	}

}
