package org.training.campus.repository.entity;

public interface Entity<T> {
	
	void setId(T id);
	T getId();
	
}
