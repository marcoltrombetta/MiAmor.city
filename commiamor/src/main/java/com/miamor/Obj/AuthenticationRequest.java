package com.miamor.Obj;

public class AuthenticationRequest {
	private String toke;
	private String custId;
	private String firstname;
	
	public AuthenticationRequest() {
		super();
	}
	
	public AuthenticationRequest(String toke, String custId, String firstname) {
		super();
		this.toke = toke;
		this.custId = custId;
		this.firstname = firstname;
	}

	public String getToke() {
		return toke;
	}

	public void setToke(String toke) {
		this.toke = toke;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public boolean isNull(){
		if(this.toke==null || this.custId==null){
			return true;
		}
		return false;
	}
}
