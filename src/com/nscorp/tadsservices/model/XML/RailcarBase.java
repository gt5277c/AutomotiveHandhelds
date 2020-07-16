package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;

public class RailcarBase {
	private String init;
	private String num;
	
	public RailcarBase() {}

	public String getInit() {
		return init;
	}

	@XmlAttribute(name="INIT")
	public void setInit(String init) {
		this.init = init;
	}

	public String getNum() {
		return num;
	}

	@XmlAttribute(name="NUM")
	public void setNum(String num) {
		this.num = num;
	}
}
