package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"type","zone","track","position"})
public class Location {
	private String type;
	private String zone;
	private String track;
	private String position;
	
	public Location() {}

	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getZone() {
		return zone;
	}

	@XmlAttribute(name="ZONE")
	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getTrack() {
		return track;
	}

	@XmlAttribute(name="TRACK")
	public void setTrack(String track) {
		this.track = track;
	}

	public String getPosition() {
		return position;
	}

	@XmlAttribute(name="POS")
	public void setPosition(String position) {
		this.position = position;
	}
}
