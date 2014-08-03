package be.inbo.zeevogels.dao.hibernate;

import java.io.Serializable;

import be.inbo.zeevogels.dao.GenericDAO;

/**
 * A Hibernate implementation of the generic DAO to be used for every DAO. Can be used for any Persistent Class with any
 * Serializable identifier.
 * 
 * @param <T> The persistent class that must be set in order to use the defined methods.
 * @param <ID> The serializable identifier of the given persistent class.
 * 
 * @author: dimitri_vansaelen
 * @since: 1.0
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable> extends ReadOnlyHibernateDAO<T, ID> implements
		GenericDAO<T, ID> {

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) {
		getSession().save(entity);
		return entity;
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#update(Object)
	 */
	@Override
	public T update(T entity) {
		getSession().merge(entity);
		return entity;
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#remove(java.lang.Object)
	 */
	@Override
	public void remove(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	/**
	 * Flush the current database session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * Clear the current database session.
	 */
	public void clear() {
		getSession().clear();
	}

}