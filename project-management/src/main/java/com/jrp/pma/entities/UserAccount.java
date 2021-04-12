package com.jrp.pma.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_accounts") //because the tablename is not same as class name
public class UserAccount {
	
	@Id
	@SequenceGenerator(name = "user_accounts_seq", sequenceName = "user_accounts_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_accounts_seq") //mentioning the generator
	@Column(name="user_id") //making sure that the java field userId is mapped to the column user_id
	private long userId;
	
	@Column(name="username")
	private String userName;
	
	private String email;
	
	private String password;
	
	private boolean enabled= true; //by default we want the user to be enabled.

	public UserAccount() {

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
	
	

}
