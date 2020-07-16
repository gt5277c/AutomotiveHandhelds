package com.nscorp.tadsservices.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
	@SerializedName("UIMessage")
	@Expose
	private String uIMessage;
	
	@SerializedName("description")
	@Expose
	private String description;
	
	@SerializedName("principal")
	@Expose
	private String principal;
	
	@SerializedName("statusCode")
	@Expose
	private String statusCode;
	
	@SerializedName("assertion")
	@Expose
	private String assertion;

	public String getUIMessage() {
		return uIMessage;
	}

	public void setUIMessage(String uIMessage) {
		this.uIMessage = uIMessage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getAssertion() {
		return assertion;
	}

	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
}
