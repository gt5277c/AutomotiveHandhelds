package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder= {"type","splc","fsac"})
public class Site {
	private String type;
	private String splc;
	private String fsac;
	private String value;
	
	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getSplc() {
		return splc;
	}

	@XmlAttribute(name="SPLC")
	public void setSplc(String splc) {
		this.splc = splc;
	}

	public String getFsac() {
		return fsac;
	}

	@XmlAttribute(name="FSAC")
	public void setFsac(String fsac) {
		this.fsac = fsac;
	}

	public String getValue() {
		return value;
	}

	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}
}
