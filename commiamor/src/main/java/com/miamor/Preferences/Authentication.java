package com.miamor.Preferences;

import com.miamor.Obj.AuthenticationRequest;

import android.content.Context;
import android.content.SharedPreferences;

public class Authentication {
	private Context ctx;
	private static String file_name="AUTHENTICATION_FILE_NAME";
	
	public Authentication(Context context) {
		ctx=context;
	}
	
	public boolean isLogged(){
		AuthenticationRequest auth=this.read();
		if(auth.getCustId().isEmpty() && auth.getToke().isEmpty()
				|| auth.getCustId().contains("null") && auth.getToke().contains("null")){
			return false;
		}
		
		return true;
	}
	
	public void save(AuthenticationRequest auth){
		SharedPreferences preferences = ctx.getSharedPreferences(file_name, ctx.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("Authentication_CustId",auth.getCustId());
		editor.putString("Authentication_Token",auth.getToke());
		editor.putString("Authentication_FirstName",auth.getFirstname());
		editor.apply();
	}
	
	public void edit(AuthenticationRequest auth){
		 SharedPreferences.Editor editor = ctx.getSharedPreferences(file_name,ctx.MODE_PRIVATE).edit();
		 editor.putString("Authentication_CustId",auth.getCustId());
		 editor.putString("Authentication_Token",auth.getToke());
		 editor.putString("Authentication_FirstName",auth.getFirstname());
		 editor.apply();
	}
	
	public AuthenticationRequest read(){
		AuthenticationRequest auth=new AuthenticationRequest();
		SharedPreferences prfs = ctx.getSharedPreferences(file_name, ctx.MODE_PRIVATE);
		auth.setCustId(prfs.getString("Authentication_CustId", ""));
		auth.setToke(prfs.getString("Authentication_Token", ""));
		auth.setFirstname(prfs.getString("Authentication_FirstName", ""));
		
		return auth;
	}

	public void clear(){
		SharedPreferences prfs = ctx.getSharedPreferences(file_name, ctx.MODE_PRIVATE);
		prfs.edit()
				.remove("Authentication_CustId")
				.remove("Authentication_Token")
				.remove("Authentication_FirstName")
				.commit();
	}
}
