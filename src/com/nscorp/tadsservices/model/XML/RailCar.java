package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class RailCar extends RailcarBase {
	private String aarType;
	private String type;
	private String lights;
	private Embargo embargo;
	private String le;
	private Location rcLocation;
	private String wbsn;
	private String waybill;
	private String bol;
	private String scac;
	
	public RailCar() {
		embargo = new Embargo();
		rcLocation = new Location();
	}

	public String getAarType() {
		return aarType;
	}

	@XmlAttribute(name="AARTYPE")
	public void setAarType(String aarType) {
		this.aarType = aarType;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getLights() {
		return lights;
	}

	@XmlElement(name="LIGHTS")
	public void setLights(String lights) {
		this.lights = lights;
	}

	public Embargo getEmbargo() {
		return embargo;
	}

	@XmlElement(name="EMBARGO")
	public void setEmbargo(Embargo embargo) {
		this.embargo = embargo;
	}

	public Location getRcLocation() {
		return rcLocation;
	}

	public String getLe() {
		return le;
	}

	@XmlElement(name="LE")
	public void setLe(String le) {
		this.le = le;
	}

	@XmlElement(name="RCLOC")
	public void setRcLocation(Location rcLocation) {
		this.rcLocation = rcLocation;
	}

	public String getWbsn() {
		return wbsn;
	}

	@XmlElement(name="WBSN")
	public void setWbsn(String wsbn) {
		this.wbsn = wsbn;
	}

	public String getWaybill() {
		return waybill;
	}

	@XmlElement(name="WAYBILL")
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public String getBol() {
		return bol;
	}

	@XmlElement(name="BOL")
	public void setBol(String bol) {
		this.bol = bol;
	}

	public String getScac() {
		return scac;
	}

	@XmlElement(name="SCAC")
	public void setScac(String scac) {
		this.scac = scac;
	}

	@Override
	public String toString() {
		return "RailCar [aarType=" + aarType + ", type=" + type + ", lights=" + lights + ", embargo=" + embargo
				+ ", le=" + le + ", rcLocation=" + rcLocation + ", wbsn=" + wbsn + ", waybill=" + waybill + ", bol="
				+ bol + ", scac=" + scac + ", toString()=" + super.toString() + "]";
	}
	
}
