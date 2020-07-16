package com.nscorp.tadsservices.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@XmlRootElement (name="AuthResponse")
@Entity
public class AuthResponse {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	@SerializedName("success")
	@Expose
	private Boolean success;
	
	@SerializedName("result")
	@Expose
	private Result result;
	
	@SerializedName("ConnectionInformation")
	@Expose
	private ConnectionInformation connectioninformation;
	
	@XmlElement (name="success")
	public Boolean getSuccess() { return success; }
	public void setSuccess(Boolean success) { this.success = success; }

	@XmlElement (name="result")
	public Result getResult() { return result; }
	public void setResult(Result result) { this.result = result; } 
	
	@XmlElement (name="ConnectionInformation")
	public ConnectionInformation getConnectionInformation() { return connectioninformation; }
	public void setConnectionInformation(ConnectionInformation connectioninformation) { this.connectioninformation = connectioninformation; }
	
	// }}
	
	@Override
	public String toString() {
		String output = null;
		Result result = this.getResult();
		
		output = "Success:" + this.success + "\r\n";
		if(result != null) {
			output += "Result UIMessage:" + result.getUIMessage() + "\r\n";
			output += "     description:" + result.getDescription() + "\r\n";
			output += "       principal:" + result.getPrincipal() + "\r\n";
			output += "      statusCode:" + result.getStatusCode() + "\r\n";
			output += "       assertion:" + result.getAssertion() + "\r\n";
		}
				
		return output;
	}
}
