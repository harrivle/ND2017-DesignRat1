package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArtifactLibrary extends Observable {

	// contains variable map<String, DesignDecision> of id --> DesignDecision
	DesignDecisions designDecisions; // DesignDecision has id, description, (and
										// eventually more)

	// contains variable map<String, String> of id --> descriptions
	Requirements requirements;

	// contains map<String,set<String> > of design decision id --> set of
	// requirement ids
	DesignRequirementLink designReqLink;
	
	HashMap<String, List<String>> tagInfo;

	File designDecFile;
	File reqFile;
	File designReqFile;
	File tagInfoFile;

	private ArtifactContainerFactory factory;

	private static ArtifactLibrary instance = null;

	public ArtifactLibrary() {
		factory = new ArtifactContainerFactory();
		designDecFile = new File("src/artifacts/designDecisions.ser");
		reqFile = new File("src/artifacts/Requirements.ser");
		designReqFile = new File("src/artifacts/DesignRequirementLinks.ser");
		tagInfoFile = new File("src/artifacts/TagInfo.ser");
		// Exists only to defeat instantiation.
		if (designDecFile.exists() && designDecFile.isFile()) {
			System.out.println(designDecFile.exists());
			FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
            	fis = new FileInputStream("src/artifects/designDecisions.ser");
            	in = new ObjectInputStream(fis);
            	designDecisions = (DesignDecisions) in.readObject();
                    in.close();
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
		}
		else {
			designDecisions = (DesignDecisions) getArtifacts("designDecisions");
		}
		if (reqFile.exists() && reqFile.isFile()) {
			FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
            	fis = new FileInputStream("src/artifacts/Requirements.ser");
            	in = new ObjectInputStream(fis);
            	requirements = (Requirements) in.readObject();
                    in.close();
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
		}
		else {
			requirements = (Requirements) getArtifacts("requirements");
		}
		if (designReqFile.exists() && designReqFile.isFile()) {
			System.out.println(designReqFile.exists());
			FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
            	fis = new FileInputStream("src/artifacts/DesignRequirementLinks.ser");
            	in = new ObjectInputStream(fis);
            	designReqLink = (DesignRequirementLink) in.readObject();
            	in.close();
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
		}
		else {
			designReqLink = (DesignRequirementLink) getArtifacts("designRequirementLink");
		}
		if (tagInfoFile.exists() && tagInfoFile.isFile()) {
			FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
            	fis = new FileInputStream("src/artifacts/TagInfo.ser");
            	in = new ObjectInputStream(fis);
            	tagInfo = (HashMap<String, List<String>>) in.readObject();
            	in.close();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
		}
		else {
			tagInfo = buildTagInfo();
		}
		testWithDisplay();
	}

	public static ArtifactLibrary getInstance() {
		if (instance == null) {
			instance = new ArtifactLibrary();
		}
		return instance;
	}

	public XMLParsableArtifactContainer getArtifacts(String type) {
		XMLParsableArtifactContainer container;
		container = factory.getXMLArtifacts(type);
		container.performParse();
		container.organizeContents();
		return container;
	}

	public DesignDecisions getDesignDecisions() {
		return designDecisions;
	}

	public void addDesignDecision(String id, String desc) {
		designDecisions.addDecision(id, desc);
		designReqLink.map.put(id, new HashSet<String>());
		System.out.println(designReqLink.map.toString());
		getReqIdList(id);
	}

	public Requirements getRequirements() {
		return requirements;
	}

	public DesignRequirementLink getDesignReqLink() {
		return designReqLink;
	}

	public void testWithDisplay() {
		for (String key : designDecisions.map.keySet())
			System.out.println(key + ": " + designDecisions.map.get(key).description);
		for (String key : requirements.map.keySet())
			System.out.println(key + ": " + requirements.map.get(key));
	}

	// get requirement set from design id
	public HashSet<String> getReqIdList(String designDecisionId) {
		System.out.println(designReqLink.map.get(designDecisionId).toString());
		return designReqLink.map.get(designDecisionId);
	}

	// set requirement list given design id and new list
	public void setReqIdList(String designDecisionId, HashSet<String> reqSet) {
		designReqLink.map.put(designDecisionId, reqSet);
		setChanged();
		notifyObservers();
		
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
                fos = new FileOutputStream("src/artifacts/DesignRequirementLinks.ser");
                out = new ObjectOutputStream(fos);
                out.writeObject(designReqLink);

                out.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
	}

	// get design design decision description given id
	public String getDesignDesc(String designDecisionId) {
		return designDecisions.map.get(designDecisionId).description;
	}

	// set design design decision description given id and description
	public void setDesignDesc(String designDecisionId, String description) {
		designDecisions.map.get(designDecisionId).description = description;
		setChanged();
		notifyObservers();
		
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
                fos = new FileOutputStream("src/artifacts/designDecisions.ser");
                out = new ObjectOutputStream(fos);
                out.writeObject(designDecisions);

                out.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
	}
	
	public HashMap<String, List<String>> buildTagInfo() {
		HashMap<String, List<String>> map = new HashMap();
		for (String id : getDesignDecisions().map.keySet()) {
			map.put(id, new ArrayList<String>(Arrays.asList("File", "Method", "Project", "Comments")));
		}
		
		return map;
	}
	
	public HashMap<String, List<String>> getTagInfo() {
		return tagInfo;
	}
	
	public void setTagInfo(String id, List<String> list) {
		tagInfo.put(id, list);
		setChanged();
		notifyObservers();
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
                fos = new FileOutputStream("src/artifacts/TagInfo.ser");
                out = new ObjectOutputStream(fos);
                out.writeObject(tagInfo);

                out.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
	}
}
