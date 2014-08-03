package be.inbo.zeevogels.dao;

import java.io.Serializable;
import java.util.List;

/**
 * A read only DAO to be used for DAOs where modification of the entities is not allowed. Can be used for any Persistent Class with any Serializable
 * identifier.
 *
 * @param <T>  The persistent class that must be set in order to use the defined methods.
 * @param <ID> The serializable identifier of the given persistent class.
 * @author: dimitri_vansaelen
 * @since: 1.0
 */
public interface ReadOnlyDAO<T, ID extends Serializable> {
	
	/**
	 * Gets the attached domain object according to the given identifier.
	 *
	 * @param id The identifier of the domain object to fetch.
	 * @return The attached domain object fetched.
	 */
	T findById(ID id);
	
	/**
	 * Gets all attached domain objects of the persistent class in the database.
	 *
	 * @return A list of all attached domain objects of the persistent class from database.
	 */
	List<T> findAll();
	
	/**
	 * Gets all attached domain objects of the persistent class in the database.
	 *
	 * @return A list of all attached domain objects of the persistent class from database.
	 */
	List<T> findAll(int start, int limit);
	
	/**
	 * Gets all attached domain objects of the persistent class that matches the given example.
	 *
	 * @param exampleInstance The example that every domain objects must match.
	 * @return A list of all attached domain objects of the persistent class that matches.
	 */
	List<T> findByExample(T exampleInstance);
	
	/**
	 * Count the total number of elements of this type.
	 *
	 * @return Total number of elements.
	 */
	Long count();
	
}
