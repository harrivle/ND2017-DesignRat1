package drat1;

import java.util.Map;

import org.eclipse.swt.widgets.MessageBox;
import org.xml.sax.SAXException;

public class ArtifactLibrary {
	private static ArtifactLibrary instance = null;
	protected ArtifactLibrary() {
		// Exists only to defeat instantiation.
	}
	public static ArtifactLibrary getInstance() {
		if(instance == null) {
			instance = new ArtifactLibrary();
	    }
		return instance;
	}
	
	public Map<String, DesignDecision> designDecisions;
	public Map<String, Requirements> requirements;
	//XMLParser xmlParser;
	
	public void test() {
		//System.out.println("\n\nTesting if singleton library is initialized\n\n");
		
		try {
			XMLParser xmlParser = new XMLParser();
			xmlParser.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void simpleTest() {
		System.out.println("\n\nTesting if singleton library is initialized\n\n");
	}
	
}
