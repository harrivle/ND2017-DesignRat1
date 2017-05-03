package parser;

import java.io.Serializable;
import java.util.HashMap;

public class Requirements extends XMLParsableArtifactContainer implements Serializable {

	public HashMap<String, String> map = new HashMap<String, String>();

	public Requirements() {

		artifactName = "Requirement";
		artifactPlural = "Requirements";
		fileExtentionName = "Requirements.xml";
	}

	@Override
	void organizeContents() {
		for (XMLParsableArtifact artifact : artifactList) {
			map.put(artifact.getId(), artifact.getDescription());
		}
	}

}
