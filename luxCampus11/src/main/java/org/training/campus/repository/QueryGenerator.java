package org.training.campus.repository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.training.campus.repository.annotation.Column;
import org.training.campus.repository.annotation.Id;
import org.training.campus.repository.annotation.Table;
import org.training.campus.repository.entity.Person;
import org.training.campus.repository.entity.Person.Color;
import org.training.campus.repository.entity.Person.Sex;

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

	private static <T> List<Object> getEntityPropertyValues(Class<T> cl, Object entity) {
		List<Object> properties = new LinkedList<>();
		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			Column anno = field.getAnnotation(Column.class);
			if (anno != null) {
				String propertyName = field.getName();
				try {
					field.setAccessible(true);
					properties.add(field.get(entity));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
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
		if (primaryKey != null) {
			join.add(primaryKey);
		}
		properties.forEach(join::add);
		return String.format("select %s from %s;", join.toString(), tableName);
	}

	public <T> String insert(Class<T> cl, T entity) {
		String tableName = getEntityTable(cl);
		var properties = getEntityPropertyNames(cl);
		var join = new StringJoiner(",");
		properties.forEach(join::add);
		var propertyValues = getEntityPropertyValues(cl, entity);
		var valueJoin = new StringJoiner(",");
		propertyValues.forEach(value -> valueJoin.add(convertToSQLLiteral(value)));
		return String.format("insert into %s (%s) values (%s);", tableName, join.toString(), valueJoin.toString());
	}

	public <T> String update(Class<T> cl, T entity) {
		return null;
	}

	public <T> String delete(Class<T> cl, Object id) {
		String tableName = getEntityTable(cl);
		String primaryKey = getEntityPrimaryKeyFieldName(cl);
		Object primKeyValue = convertToSQLLiteral(id);
		if (primKeyValue == null)
			throw new IllegalArgumentException("primary key value parameter shouldn't be null");
		return String.format("delete from %s where %s=%s;", tableName, primaryKey, primKeyValue);
	}

	public <T> String getById(Class<T> cl, Object id) {
		String tableName = getEntityTable(cl);
		String primaryKey = getEntityPrimaryKeyFieldName(cl);
		var properties = getEntityPropertyNames(cl);
		var join = new StringJoiner(",");
		if (primaryKey != null) {
			join.add(primaryKey);
		}
		properties.forEach(join::add);
		Object primKeyValue = convertToSQLLiteral(id);
		if (primKeyValue == null)
			throw new IllegalArgumentException("primary key value parameter shouldn't be null");
		return String.format("select %s from %s where %s=%s;", join.toString(), tableName, primaryKey, primKeyValue);
	}

	private static String convertToSQLLiteral(Object value) {
		if (value == null)
			return null;
		Class<?> type = value.getClass();
		if (type == long.class || type == Long.class) {
			return Long.toString((long) value);
		} else if (type == int.class || type == Integer.class) {
			return Integer.toString((int) value);
		} else if (type == short.class || type == Short.class) {
			return Short.toString((short) value);
		} else if (type == byte.class || type == Byte.class) {
			return Byte.toString((byte) value);
		} else if (type == float.class || type == Float.class) {
			return Float.toString((float) value);
		} else if (type == double.class || type == Double.class) {
			return Double.toString((double) value);
		} else if (type == char.class || type == Character.class) {
			return Character.toString((char) value);
		} else if (type == boolean.class || type == Boolean.class) {
			return Boolean.toString((boolean) value);
		} else if (type.isEnum()) {
			return "'" + Enum.class.cast(value).name() + "'";
		} else if (type == String.class) {
			return "'" + value + "'";
		} else if (type == LocalDate.class) {
			LocalDate date = (LocalDate) value;
			return "'" + date.format(DateTimeFormatter.ISO_DATE) + "'";
		}
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(getTableForEntity(Person.class));
		// System.out.println(getEntityPrimaryKeyFieldName(Person.class));
		// System.out.println(getEntityPropertyNames(Person.class));
		// System.out.println(QueryGenerator.getInstance().getAll(Person.class));
		// System.out.println(QueryGenerator.getInstance().getById(Person.class, 1L));
		// System.out.println(QueryGenerator.getInstance().delete(Person.class, 1L));
		System.out.println(QueryGenerator.getInstance().insert(Person.class,
				new Person("0123456789", "John", "Smith", LocalDate.of(2000, 01, 01), Sex.MALE, 180, 80, Color.BLUE)));
	}

}
