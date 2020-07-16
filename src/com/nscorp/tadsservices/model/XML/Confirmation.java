package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"value","code"})
public class Confirmation {
	private String value;
	private String code;
	
	public Confirmation() {
		
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute(name="VALUE")
	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	@XmlAttribute(name="CODE")
	public void setCode(String code) {
		this.code = code;
	}
}
