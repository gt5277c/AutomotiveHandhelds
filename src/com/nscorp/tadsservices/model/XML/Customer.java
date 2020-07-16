package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Customer {
	private String type;
	private String value;
	
	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	@XmlValue()
	public void setValue(String value) {
		this.value = value;
	}
}
