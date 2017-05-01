package parser;

import java.util.HashMap;

public class DesignDecisions extends XMLParsableArtifactContainer {
	
	HashMap<String, DesignDecision> map = new HashMap<String, DesignDecision>();
	
	public DesignDecisions() {
		
		artifactName = "DesignDecision";
		artifactPlural = "DesignDecisions";
		fileExtentionName = "DesignDecision.xml";

	}

	@Override
	void organizeContents() {
		for(XMLParsableArtifact artifact : artifactList) {
			map.put(artifact.getId(), new DesignDecision(artifact));
		}
	}

}
