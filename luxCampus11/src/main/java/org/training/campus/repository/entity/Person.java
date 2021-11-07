package org.training.campus.repository.entity;

import java.time.LocalDate;

public class Person {
	public enum Sex {
		FEMALE, MALE
	}

	public enum Color {
		RED, GREEN, BLUE, YELLOW, BROWN, DARK
	}

	private long id;
	private String ssn;
	private String firstname;
	private String lastname;
	private LocalDate birthDate;
	private Sex sex;
	private int height;
	private int weight;
	private Color eyeColor;

	public Person(String ssn, String firstname, String lastname, LocalDate birthDate, Sex sex, int height, int weight,
			Color eyeColor) {
		super();
		this.ssn = ssn;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
		this.eyeColor = eyeColor;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

}
