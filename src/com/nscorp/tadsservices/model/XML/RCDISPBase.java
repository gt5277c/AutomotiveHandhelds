package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.TADSJournalMessage;
import com.nscorp.tadsservices.model.XML.Confirmation;
import com.nscorp.tadsservices.model.XML.Header;
import com.nscorp.tadsservices.model.XML.Site;
import com.nscorp.tadsservices.model.XML.SystemID;

@XmlType(propOrder= {"prodStat","header","site","groupSiteOrigin","event","eventTime"})
public class RCDISPBase extends TADSJournalMessage{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String prodStat;
	private Header header;
	private Site site;
	private String groupSiteOrigin;
	private Event event;
	private String eventTime;
	
	// }}
	
	public RCDISPBase() {
		header = new Header();
		site = new Site();
		event = new Event();
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getProdStat() {
		return prodStat;
	}

	@XmlAttribute(name="PRODSTAT")
	public void setProdStat(String prodStat) {
		this.prodStat = prodStat;
	}

	public Header getHeader() {
		return header;
	}

	@XmlElement(name="HEADER")
	public void setHeader(Header header) {
		this.header = header;
	}

	public Site getSite() {
		return site;
	}

	@XmlElement(name="SITE")
	public void setSite(Site site) {
		this.site = site;
	}

	public Event getEvent() {
		return event;
	}

	public String getGroupSiteOrigin() {
		return groupSiteOrigin;
	}

	@XmlElement(name="GS-ORIG")
	public void setGroupSiteOrigin(String groupSiteOrigin) {
		this.groupSiteOrigin = groupSiteOrigin;
	}

	@XmlElement(name="EVENT")
	public void setEvent(Event event) {
		this.event = event;
	}

	public String getEventTime() {
		return eventTime;
	}

	@XmlElement(name="EVENTTIME")
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String getXML() {
		return null;
	}

	// }}

	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////

}
