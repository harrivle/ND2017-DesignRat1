package net.mv.tutorial.annotation;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ArtifactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3276974823304918907L;
	private String uri;
	private String type;
	private Map<String, String> attributes;
	private final long timeCreated;

	public long getTimeCreated() {
		return timeCreated;
	}

	public ArtifactInfo(String uri, String type) {
		this.uri = uri;
		this.type = type;
		attributes = new TreeMap<>();
		timeCreated = System.currentTimeMillis();
	}

	public String getArtifactType() {
		return type;
	}

	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public static ArtifactInfo create(String type) {
		return new ArtifactInfo("", type);
	}

	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> e : attributes.entrySet()) {
			sb.append(e.getKey());
			sb.append("=");
			sb.append(e.getValue());
			sb.append(" | ");
		}
		return sb.toString();
	}

}
