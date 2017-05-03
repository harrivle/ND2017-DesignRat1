package parser;

//import java.io.File;
import java.util.List;
//import org.eclipse.core.resources.ResourcesPlugin;

public abstract class XMLParsableArtifactContainer {

	public String artifactName;
	public String artifactPlural;
	public String fileExtentionName;
	public String filePathName;

	public XMLParser xmlParser;

	List<XMLParsableArtifact> artifactList = null;

	public XMLParsableArtifactContainer() {
	}

	public void performParse(String artifactPath) {
		prepareFilePathName(artifactPath);

		try {
			XMLParser xmlParser = new XMLParser(filePathName, artifactName, artifactPlural);
			// xmlParser.traverseAndPrint();
			artifactList = xmlParser.getList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareFilePathName(String artifactPath) {
		// String absolutePathH ="/home/harrison/EclipseProjects/DRAT/src/artifacts/";
		//String absolutePathT = "/Users/troyprince/git/ND2017-DRAT1/DRAT/src/artifacts/";
		//String DRATrelativePath = "src/artifacts/" + fileExtentionName;
		// how you would find plugin path:
		// String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		filePathName = artifactPath + fileExtentionName;
		// filePathName = absolutePathT;
	}

	public void printResult() {
		for (XMLParsableArtifact artifact : artifactList) {
			System.out.println(artifact.id + "  " + artifact.description);
			// Artifacts.put(artifact.id, (T)artifact);
		}
	}

	abstract void organizeContents();

}