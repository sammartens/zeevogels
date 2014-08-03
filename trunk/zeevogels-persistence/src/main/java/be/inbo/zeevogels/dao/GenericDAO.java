package be.inbo.zeevogels.dao;

import java.io.Serializable;

/**
 * A generic DAO to be used for every DAO. Can be used for any Persistent Class with any Serializable
 * identifier.
 *
 * @param <T>  The persistent class that must be set in order to use the defined methods.
 * @param <ID> The serializable identifier of the given persistent class.
 * @author: dimitri_vansaelen
 * @since: 1.0
 */
public interface GenericDAO<T, ID extends Serializable> extends ReadOnlyDAO<T, ID> {

	/**
	 * Persist the given object in database.
	 *
	 * @param entity The object to be persisted.
	 * @return The attached version of the persisted object.
	 */
	T save(T entity);

	/**
	 * Updates the given object in database.
	 *
	 * @param entity The object to be updated.
	 * @return The attached version of the persisted object.
	 */
	T update(T entity);

	/**
	 * Remove the given object from database.
	 *
	 * @param entity The given object to be removed.
	 */
	void remove(T entity);

	/**
	 * Re-read the state of the given entity from the database.
	 *
	 * @param entity The entity from which the state has to be read again
	 */
	void refresh(T entity);

}