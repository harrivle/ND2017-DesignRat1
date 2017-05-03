package parser;

public class XMLParsableArtifact {

	String id;
	String description;

	XMLParsableArtifact() {

	}

	public XMLParsableArtifact(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
