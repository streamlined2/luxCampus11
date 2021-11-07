package org.training.campus.repository.entity;

import java.time.LocalDate;
import java.util.Objects;

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

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Color getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(Color eyeColor) {
		this.eyeColor = eyeColor;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Person p) {
			return Objects.equals(id, p.id);			
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
