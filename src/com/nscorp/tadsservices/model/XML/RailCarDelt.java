package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"le","rcLocation","wbsn","waybill"})
public class RailCarDelt extends RailcarBase {
	private String le;
	private Location rcLocation;
	private String wbsn;
	private String waybill;
	
	public RailCarDelt() {
		rcLocation = new Location();
	}
	
	public String getLe() {
		return le;
	}

	@XmlElement(name="LE")
	public void setLe(String le) {
		this.le = le;
	}

	public Location getRcLocation() {
		return rcLocation;
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

	@Override
	public String toString() {
		return "RailCarDelt [le=" + le + ", rcLocation=" + rcLocation + ", wbsn=" + wbsn + ", waybill=" + waybill + "]";
	}
	
}
