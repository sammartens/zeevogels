package be.inbo.zeevogels.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import be.inbo.zeevogels.dao.TripDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;

@Repository
public class TripDAOImpl extends GenericHibernateDAO<Trip, Long> implements TripDAO {

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		Criteria crit = buildFilterCriteria(filters, new SortCriterium());
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult();
	}

	@Override
	public List<Trip> findFiltered(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium) {
		List<Trip> result = new ArrayList<Trip>();
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
		Criteria crit = getSession().createCriteria(Trip.class);
		boolean sortAdded = false;
		for (FilterCriterium filter : filters) {
			if (!StringUtils.isEmpty(filter.getValue())) {
				switch (filter.getProperty()) {
				case "ship":
					Criteria shipCriteria = crit.createCriteria("ship");
					shipCriteria.add(Restrictions.ilike("name", "%" + filter.getValue() + "%"));
					if (!sortAdded && "ship".equals(sortCriterium.getProperty())) {
						shipCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("name") : Order
								.desc("name"));
						sortAdded = true;
					}
					break;
				case "id":
					crit.add(Restrictions.eq("id", Long.valueOf(filter.getValue())));
					if (!sortAdded && "id".equals(sortCriterium.getProperty())) {
						crit.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("id") : Order.desc("id"));
						sortAdded = true;
					}
					break;
				case "date":
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
						Date monthDate = dateFormat.parse(filter.getValue());
						Calendar monthCal = Calendar.getInstance();
						monthCal.setTime(monthDate);
						Calendar startCal = Calendar.getInstance();
						startCal.set(monthCal.get(Calendar.YEAR), monthCal.get(Calendar.MONTH), 1, 0, 0, 0);
						Calendar endCal = Calendar.getInstance();
						endCal.set(monthCal.get(Calendar.YEAR), monthCal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
						endCal.add(Calendar.SECOND, -1);
						crit.add(Restrictions.between("date", startCal.getTime(), endCal.getTime()));
						if (!sortAdded && sortCriterium.getProperty() != null) {
							crit.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc(sortCriterium
									.getProperty()) : Order.desc(sortCriterium.getProperty()));
							sortAdded = true;
							break;
						}
					} catch (ParseException e) {
						throw new RuntimeException(e.getMessage());
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
		case "ship":
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc("name") : Order.desc("name");
			criteria.createCriteria("ship").addOrder(order);
			break;
		default:
			order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc(sortCriterium.getProperty()) : Order
					.desc(sortCriterium.getProperty());
			criteria.addOrder(order);
			break;
		}
	}

}
