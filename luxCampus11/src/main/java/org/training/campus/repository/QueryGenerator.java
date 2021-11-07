package org.training.campus.repository;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.training.campus.repository.annotation.Column;
import org.training.campus.repository.annotation.Id;
import org.training.campus.repository.annotation.Table;
import org.training.campus.repository.entity.Person;

public class QueryGenerator {

	private QueryGenerator() {
	}

	private static final QueryGenerator instance = new QueryGenerator();

	public static QueryGenerator getInstance() {
		return instance;
	}

	private static <T> String getEntityTable(Class<T> cl) {
		String tableName = cl.getSimpleName().toLowerCase();
		Table anno = cl.getAnnotation(Table.class);
		if (anno != null) {
			String tName = anno.name().trim();
			if (!tName.isEmpty()) {
				tableName = tName;
			}
		}
		return tableName;
	}

	private static <T> String getEntityPrimaryKeyFieldName(Class<T> cl) {
		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			Id anno = field.getAnnotation(Id.class);
			if (anno != null) {
				if (anno.name().isBlank()) {
					return field.getName();
				} else {
					return anno.name().trim();
				}
			}
		}
		if (cl.getSuperclass() != null) {
			return getEntityPrimaryKeyFieldName(cl.getSuperclass());
		}
		return null;
	}

	private static <T> List<String> getEntityPropertyNames(Class<T> cl) {
		List<String> properties = new LinkedList<>();
		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			Column anno = field.getAnnotation(Column.class);
			if (anno != null) {
				String columnName = field.getName();
				String nameParam = anno.name();
				if (!nameParam.isBlank()) {
					columnName = nameParam.trim();
				}
				properties.add(columnName);
			}
		}
		if (cl.getSuperclass() != null) {
			properties.addAll(getEntityPropertyNames(cl.getSuperclass()));
		}
		return properties;
	}

	public <T> String getAll(Class<T> cl) {
		String tableName = getEntityTable(cl);
		String primaryKey = getEntityPrimaryKeyFieldName(cl);
		var properties = getEntityPropertyNames(cl);
		var join = new StringJoiner(",");
		if(primaryKey!=null) {
			join.add(primaryKey);
		}
		properties.forEach(property -> join.add(property));
		return String.format("select %s from %s;", join.toString(), tableName);
	}

	public <T> String insert(Class<T> cl, T value) {
		return null;
	}

	public <T> String update(Class<T> cl, T valuze) {
		return null;
	}

	public <T> String delete(Class<T> cl, Object id) {
		return null;
	}

	public <T> String getById(Class<T> cl, Object id) {
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(getTableForEntity(Person.class));
		// System.out.println(getEntityPrimaryKeyFieldName(Person.class));
		// System.out.println(getEntityPropertyNames(Person.class));
		System.out.println(QueryGenerator.getInstance().getAll(Person.class));
	}

}
