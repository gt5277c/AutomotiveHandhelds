package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"code","color","manufacturerName"})
public class Color {
	private String code;
	private String manufacturerName;
	private String color;
	
	public String getCode() {
		return code;
	}

	@XmlAttribute(name="CODE")
	public void setCode(String code) {
		this.code = code;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	@XmlAttribute(name="MFRNAME")
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getColor() {
		return color;
	}

	@XmlAttribute(name="COLOR")
	public void setColor(String color) {
		this.color = color;
	}
}
