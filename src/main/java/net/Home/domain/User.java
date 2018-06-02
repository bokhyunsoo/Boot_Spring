package net.Home.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long Id;
	
	@Column(nullable=false, length=20, unique=true)
	private String userId;
	private String password;
	private String name;
	private String email;
	
	
	public User() {
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

	public void update(User updatedUser) {
		this.password = updatedUser.password;
		this.name = updatedUser.name;
		this.email = updatedUser.email;
	}

}
