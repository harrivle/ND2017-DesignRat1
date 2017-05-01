package parser;

import java.util.HashMap;
import java.util.HashSet;

public class DesignRequirementLink extends XMLParsableArtifactContainer {

	private HashSet<String> designIds = new HashSet<String>();
	public HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();

	public DesignRequirementLink() {

		artifactName = "TMDesign";
		artifactPlural = "TMDesigns";
		fileExtentionName = "TM_Requirements_Design.xml";

	}

	@Override
	void organizeContents() {
		// TOTO: do this the right way
		for (XMLParsableArtifact artifact : artifactList) {
			designIds.add(artifact.description);
		}
		for (String id : designIds) {
			map.put(id, new HashSet<String>());
		}
		for (XMLParsableArtifact artifact : artifactList) {
			map.get(artifact.description).add(artifact.id);
		}
	}

}
