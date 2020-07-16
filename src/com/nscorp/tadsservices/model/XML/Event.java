package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder= {"reason","block","classValue","value"})
public class Event {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	@XmlTransient
	public String reason;
	@XmlTransient
	public String block;
	@XmlTransient
	public String classValue;
	@XmlTransient
	public String value;
	// }}
	
	public Event() {}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getReason() {
		return reason;
	}

	@XmlAttribute(name="REASON")
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBlock() {
		return block;
	}

	@XmlAttribute(name="BLOCK")
	public void setBlock(String block) {
		this.block = block;
	}

	public String getClassValue() {
		return classValue;
	}

	@XmlAttribute(name="CLASS")
	public void setClassValue(String classValue) {
		this.classValue = classValue;
	}

	public String getValue() {
		return value;
	}

	@XmlValue()
	public void setValue(String value) {
		this.value = value;
	}
	// }}

	@Override
	public String toString() {
		return "Event [reason=" + reason + ", block=" + block + ", classValue=" + classValue + ", value=" + value + "]";
	}
}
