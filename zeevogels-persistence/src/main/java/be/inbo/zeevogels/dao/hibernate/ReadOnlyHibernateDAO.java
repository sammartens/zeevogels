package be.inbo.zeevogels.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import be.inbo.zeevogels.dao.ReadOnlyDAO;

/**
 * A Hibernate implementation of the read only DAO to be used for DAOs where modification of the entities is not
 * allowed. Can be used for any Persistent Class with any Serializable identifier.
 * 
 * @param <T> The persistent class that must be set in order to use the defined methods.
 * @param <ID> The serializable identifier of the given persistent class.
 * 
 * @author: dimitri_vansaelen
 * @since: 1.0
 */
public abstract class ReadOnlyHibernateDAO<T, ID extends Serializable> implements ReadOnlyDAO<T, ID> {

	private Class<? extends T> persistentClass;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * Empty Constructor. Initializes the persistentClass property according to the super class.
	 */
	@SuppressWarnings("unchecked")
	public ReadOnlyHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#findById(java.io.Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		T entity = (T) getSession().get(getPersistentClass(), id);
		if (entity == null) {
			throw new ObjectNotFoundException(id, getPersistentClass().getSimpleName());
		}
		return entity;
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#findAll(int start, int limit)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int start, int limit) {
		List<T> result = new ArrayList<T>();
		Criteria idsOnlyCriteria = getSession().createCriteria(getPersistentClass());
		idsOnlyCriteria.setProjection(Projections.distinct(Projections.id()));
		idsOnlyCriteria.setFirstResult(start).setMaxResults(limit);
		List<Long> ids = idsOnlyCriteria.list();
		if (ids.size() > 0) {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.in("id", ids));
			result = criteria.list();
		}
		return result;
	}

	/**
	 * Creates a query according to the given example instance and excluding the given properties.
	 * 
	 * @param exampleInstance The example that the result domain objects must match.
	 * @param excludeProperty An array of properties that the query must ignore when trying to match.
	 * @return The list of domain objects that match.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return (List<T>) crit.list();
	}

	/**
	 * @see be.inbo.autopsies.data.dao.GenericDAO#findByExample(java.lang.Object)
	 */
	@Override
	public List<T> findByExample(T exampleInstance) {
		return findByExample(exampleInstance, new String[0]);
	}

	@Override
	public Long count() {
		return (Long) createCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected Criteria createCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit;
	}

	/**
	 * Gets the current session factory attached to this DAO.
	 * 
	 * @return The current session factory attached to this DAO.
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	/**
	 * Sets the session factory to be attached to this DAO.
	 * 
	 * @param s The session factory implementation to be attached to this DAO.
	 */
	public void setSessionFactory(SessionFactory s) {
		this.sessionFactory = s;
	}

	/**
	 * Gets the current session attached to this DAO.
	 * 
	 * @return The current session attahed to this DAO.
	 */
	protected Session getSession() {
		if (sessionFactory == null) {
			throw new IllegalStateException("Session has not been set on DAO before usage");
		}
		return sessionFactory.getCurrentSession();
	}

	/**
	 * The persistent class attached to this DAO.
	 * 
	 * @return The persistent class value attached to this DAO.
	 */
	public Class<? extends T> getPersistentClass() {
		return persistentClass;
	}
}