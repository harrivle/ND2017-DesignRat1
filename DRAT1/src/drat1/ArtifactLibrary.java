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
	
	public static void main() {
		System.out.println("\n\nTesting if singleton library is initialized\n\n");
		MessageBox msb = new MessageBox(null);
		msb.setMessage("Testing if singleton library is initialized");
		msb.open();
		
		try {
			XMLParser xmlParser = new XMLParser();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
