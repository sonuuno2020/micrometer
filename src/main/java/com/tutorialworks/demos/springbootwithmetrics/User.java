package com.tutorialworks.demos.springbootwithmetrics;

public class User {
	private String name;
	private long id;
	
	public User(long id,String name) {
		this.name=name;
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
}
