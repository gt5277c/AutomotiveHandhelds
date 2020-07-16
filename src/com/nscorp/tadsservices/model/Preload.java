package com.nscorp.tadsservices.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.PreloadDAO;

@XmlRootElement (name="Preload")
@Entity
public class Preload extends TADSLocationObject{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private String ActionCode;
	private String Submit;
	private String Expiration;
	private String Extended;
	private String DeleteOnCancel;
	private String ChngWho;
	private Date Chngdate;
	
	private PreloadDAO preDAO;
	// }}
	
	//Constructor
	public Preload() {}

	public Preload(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getCode() { return Code; }
	public void setCode(String code) { Code = code; }

	public String getActionCode() { return ActionCode; }
	public void setActionCode(String actionCode) { ActionCode = actionCode; }

	public String getSubmit() { return Submit; }
	public void setSubmit(String submit) { Submit = submit; }

	public String getExpiration() { return Expiration; 	}
	public void setExpiration(String expiration) { Expiration = expiration; }

	public String getExtended() { return Extended; }
	public void setExtended(String extended) { Extended = extended; }

	public String getDeleteOnCancel() { return DeleteOnCancel; }
	public void setDeleteOnCancel(String deleteOnCancel) { DeleteOnCancel = deleteOnCancel; }

	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	public Date getChngdate() { return Chngdate; }
	public void setChngdate(Date chngdate) { Chngdate = chngdate; }
	// }}

	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Preload GetPreload(String code) {
		return preDAO.get(code);
	}
	
	public List<Preload> GetAllPreloads(){
		return preDAO.getAllPreloads();
	}
	
	public boolean IsExpired(String code) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		Preload preload = GetPreload(code);
		if(preload != null) {
			//Get expiration and compare to todays date.
			if(preload.getExpiration() != null) {
				try {
					Date expDate = sdf.parse(preload.getExpiration());
					if(expDate.before(now)) return false;
				} catch (ParseException e) {
					e.printStackTrace();
					return true;
				}
			}
		}
		
		return true;
	}
	
	public void Save() {
		preDAO.set(this);
	}
	
	public void Delete() {
		preDAO.delete(this);
	}

}
