package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class LocationType {
	private String type;
	private String sub;
	private String road;
	private String value;
	
	public LocationType() {
		
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getSub() {
		return sub;
	}

	@XmlAttribute(name="SUB")
	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getRoad() {
		return road;
	}

	@XmlAttribute(name="ROAD")
	public void setRoad(String road) {
		this.road = road;
	}

	public String getValue() {
		return value;
	}

	@XmlValue()
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LocationType [type=" + type + ", sub=" + sub + ", road=" + road + ", value=" + value + "]";
	}
	
}
