package com.nscorp.tadsservices.model;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="ManfVINCode")
@Entity
public class ManfVINCode extends TADSLocationObject {
	private String manufacturer;
	private String vincode;
	private String vincheck;
	
	public ManfVINCode() {}

	@XmlElement (name="manufacturer")
	public String getManufacturer() { return manufacturer; }
	public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

	@XmlElement (name="vincode")
	public String getVincode() { return vincode; }
	public void setVincode(String vincode) { this.vincode = vincode; }
	
	@XmlElement (name="vincheck")
	public String getVincheck() { return vincheck; }
	public void setVincheck(String vincheck) { this.vincheck = vincheck; }

	public static final HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("tblManf_Manufacturer", "manufacturer");
			put("tblManfCodes_VINCode", "vincode");
			put("tblManf_VINCheck", "vincheck");
		}
	};
}
