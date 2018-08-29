package br.mil.fab.consigext.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "samba")
@Configuration("sambaProperties")
public class SambaProperties {
	
	String address;
	String user;
	String pass;
	String root;
	
	
	public String getAddress() {
		return address;
	}
	

	public String getUser() {
		return user;
	}
	
	
	public String getPass() {
		return pass;
	}
	

	public String getRoot() {
		return root;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public void setRoot(String root) {
		this.root = root;
	}
	

}
