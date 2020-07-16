package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"id","node"})
public class SystemID {
	private String id;
	private String node;
	
	public SystemID() {
		
	}

	public String getId() {
		return id;
	}
	
	@XmlAttribute(name="ID")
	public void setId(String id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}

	@XmlAttribute(name="NODE")
	public void setNode(String node) {
		this.node = node;
	}
}
