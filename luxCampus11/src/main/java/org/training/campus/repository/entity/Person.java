package org.training.campus.repository.entity;

import java.time.LocalDate;

import org.training.campus.repository.annotation.Column;
import org.training.campus.repository.annotation.Id;
import org.training.campus.repository.annotation.Table;

@Table(name = "person")
public class Person {
	public enum Sex {
		FEMALE, MALE
	}

	public enum Color {
		RED, GREEN, BLUE, YELLOW, BROWN, DARK
	}

	@Id
	private long id;
	@Column
	private String ssn;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private LocalDate birthDate;
	@Column
	private Sex sex;
	@Column
	private int height;
	@Column
	private int weight;
	@Column
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
