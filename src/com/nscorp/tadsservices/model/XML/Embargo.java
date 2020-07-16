package com.nscorp.tadsservices.model.XML;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Embargo {
	private String permit;
	private String value;
	
	public Embargo() {
		
	}

	public String getPermit() {
		return permit;
	}

	@XmlAttribute(name="PERMIT")
	public void setPermit(String permit) {
		this.permit = permit;
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
		return "Embargo [permit=" + permit + ", value=" + value + "]";
	}
	
}
