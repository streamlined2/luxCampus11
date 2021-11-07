package org.training.campus.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.training.campus.repository.entity.Person;
import org.training.campus.repository.entity.Person.Color;
import org.training.campus.repository.entity.Person.Sex;

class QueryGeneratorTest {

	@Test
	void testGetAll() {
		final String expected = "select id,ssn,firstname,lastname,birthDate,sex,height,weight,eyeColor from person;";
		final String actual = QueryGenerator.getInstance().getAll(Person.class);
		assertEquals(expected, actual);
	}

	@Test
	void testInsert() {
		Person person = new Person("0123456789", "John", "Smith", LocalDate.of(2000, 01, 01), Sex.MALE, 180, 80,
				Color.BLUE);
		final String expected = """
				insert into person (ssn,firstname,lastname,birthDate,sex,height,weight,eyeColor) values ('0123456789','John','Smith','2000-01-01','MALE',180,80,'BLUE');""";
		final String actual = QueryGenerator.getInstance().insert(Person.class, person);
		assertEquals(expected, actual);
	}

	@Test
	void testUpdate() {
		Person person = new Person("0123456789", "John", "Smith", LocalDate.of(2000, 01, 01), Sex.MALE, 180, 80,
				Color.BLUE);
		person.setId(1);
		final String expected = """
				update person set
				ssn='0123456789',firstname='John',lastname='Smith',birthDate='2000-01-01',sex='MALE',height=180,weight=80,eyeColor='BLUE'
				where id=1;
				""";
		final String actual = QueryGenerator.getInstance().update(Person.class, person);
		assertEquals(expected, actual);
	}

	@Test
	void testGetById() {
		final String expected = "select id,ssn,firstname,lastname,birthDate,sex,height,weight,eyeColor from person where id=1;";
		final String actual = QueryGenerator.getInstance().getById(Person.class, 1L);
		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		final String expected = """
				delete from person where id=1;""";
		final String actual = QueryGenerator.getInstance().delete(Person.class, 1L);
		assertEquals(expected, actual);
	}

}
