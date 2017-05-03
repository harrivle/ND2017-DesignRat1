package parser;

import java.io.Serializable;
import java.util.HashMap;

public class DesignDecisions extends XMLParsableArtifactContainer implements Serializable {

	public HashMap<String, DesignDecision> map = new HashMap<String, DesignDecision>();

	public DesignDecisions() {

		artifactName = "DesignDecision";
		artifactPlural = "DesignDecisions";
		fileExtentionName = "DesignDecision.xml";

	}

	public void addDecision(String id, String desc) {
		map.put(id, new DesignDecision(id, desc));
	}

	@Override
	void organizeContents() {
		for (XMLParsableArtifact artifact : artifactList) {
			map.put(artifact.getId(), new DesignDecision(artifact));
		}
	}

}
