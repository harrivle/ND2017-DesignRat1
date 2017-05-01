package parser;

public class ArtifactContainerFactory {

	public XMLParsableArtifactContainer getXMLArtifacts(String type) {
		XMLParsableArtifactContainer container = null;

		if (type.equals("requirements")) {
			container = new Requirements();
		}
		else if (type.equals("designDecisions")) {
			container = new DesignDecisions();
		}
		else if (type.equals("designRequirementLink")) {
			container = new DesignRequirementLink();
		}
		else {
			System.out.println("Type not recognized.");
		}

		return container;
	}

}
