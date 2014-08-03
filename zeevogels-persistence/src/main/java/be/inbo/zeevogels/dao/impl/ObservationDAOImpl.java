package be.inbo.zeevogels.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import be.inbo.zeevogels.dao.ObservationDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;

@Repository
public class ObservationDAOImpl extends GenericHibernateDAO<Observation, Long> implements ObservationDAO {

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		Criteria crit = buildFilterCriteria(filters, new SortCriterium());
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult();
	}

	@Override
	public List<Observation> findFiltered(int start, int limit, List<FilterCriterium> filters,
			SortCriterium sortCriterium) {
		List<Observation> result = new ArrayList<Observation>();
		Criteria idsOnlyCriteria = buildFilterCriteria(filters, sortCriterium);
		idsOnlyCriteria.setProjection(Projections.id());
		idsOnlyCriteria.setFirstResult(start).setMaxResults(limit);
		List<Long> ids = idsOnlyCriteria.list();
		if (ids.size() > 0) {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.in("id", ids));
			buildSortOrder(criteria, sortCriterium);

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			result = criteria.list();
		}
		return result;

	}

	private Criteria buildFilterCriteria(List<FilterCriterium> filters, SortCriterium sortCriterium) {
		Criteria crit = getSession().createCriteria(Observation.class);
		boolean sortAdded = false;
		for (FilterCriterium filter : filters) {
			if (!StringUtils.isEmpty(filter.getValue())) {
				switch (filter.getProperty()) {
				case "tripId":
					crit.add(Restrictions.eq("trip.id", Long.valueOf(filter.getValue())));
					break;
				case "taxon":
					Criteria taxonCriteria = crit.createCriteria("taxon");
					taxonCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "taxon".equals(sortCriterium.getProperty())) {
						taxonCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order
								.desc("value"));
						sortAdded = true;
					}
					break;
				case "age":
					Criteria ageCriteria = crit.createCriteria("age");
					ageCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "age".equals(sortCriterium.getProperty())) {
						ageCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order
								.desc("value"));
						sortAdded = true;
					}
					break;
				case "plumage":
					Criteria plumageCriteria = crit.createCriteria("plumage");
					plumageCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "plumage".equals(sortCriterium.getProperty())) {
						plumageCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("value")
								: Order.desc("value"));
						sortAdded = true;
					}
					break;
				case "stratum":
					Criteria stratumCriteria = crit.createCriteria("stratum");
					stratumCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "stratum".equals(sortCriterium.getProperty())) {
						stratumCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("value")
								: Order.desc("value"));
						sortAdded = true;
					}
					break;
				case "flywayBehaviour":
					Criteria flywayBehaviourCriteria = crit.createCriteria("flywayBehaviour");
					flywayBehaviourCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "flywayBehaviour".equals(sortCriterium.getProperty())) {
						flywayBehaviourCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order
								.asc("value") : Order.desc("value"));
						sortAdded = true;
					}
					break;
				case "associationBehaviour":
					Criteria associationBehaviourCriteria = crit.createCriteria("associationBehaviour");
					associationBehaviourCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "associationBehaviour".equals(sortCriterium.getProperty())) {
						associationBehaviourCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order
								.asc("value") : Order.desc("value"));
						sortAdded = true;
					}
					break;
				case "turbineHeight":
					Criteria turbineHeightCriteria = crit.createCriteria("turbineHeight");
					turbineHeightCriteria.add(Restrictions.ilike("value", "%" + filter.getValue() + "%"));
					if (!sortAdded && "turbineHeight".equals(sortCriterium.getProperty())) {
						turbineHeightCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("value")
								: Order.desc("value"));
						sortAdded = true;
					}
					break;
				default:
					crit.add(Restrictions.ilike(filter.getProperty(), "%" + filter.getValue() + "%"));
					if (!sortAdded && sortCriterium.getProperty() != null) {
						crit.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc(sortCriterium
								.getProperty()) : Order.desc(sortCriterium.getProperty()));
						sortAdded = true;
						break;
					}
				}
			}
		}
		if (sortCriterium.getProperty() != null && !sortAdded) {
			buildSortOrder(crit, sortCriterium);
		}
		return crit;
	}

	private void buildSortOrder(Criteria criteria, SortCriterium sortCriterium) {
		Order order;

		switch (sortCriterium.getProperty()) {
		case "taxon":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("taxon").addOrder(order);
			break;
		case "age":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("age").addOrder(order);
			break;
		case "plumage":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("plumage").addOrder(order);
			break;
		case "stratum":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("stratum").addOrder(order);
			break;
		case "flywayBehaviour":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("flywayBehaviour").addOrder(order);
			break;
		case "associationBehaviour":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("associationBehaviour").addOrder(order);
			break;
		case "turbineHeight":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("value") : Order.desc("value");
			criteria.createCriteria("turbineHeight").addOrder(order);
			break;
		default:
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc(sortCriterium.getProperty()) : Order
					.desc(sortCriterium.getProperty());
			criteria.addOrder(order);
			break;
		}
	}

}
