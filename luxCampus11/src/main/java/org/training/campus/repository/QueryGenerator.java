package org.training.campus.repository;

public class QueryGenerator {

	private QueryGenerator() {
	}

	private static final QueryGenerator instance = new QueryGenerator();

	public static QueryGenerator getInstance() {
		return instance;
	}

	public <T> String getAll(Class<T> cl) {
		return null;
	}

	public <T> String insert(Class<T> cl, T value) {
		return null;
	}

	public <T> String update(Class<T> cl, T value) {
		return null;
	}

	public <T> String delete(Class<T> cl, Object id) {
		return null;
	}

	public <T> String getById(Class<T> cl, Object id) {
		return null;
	}

}
