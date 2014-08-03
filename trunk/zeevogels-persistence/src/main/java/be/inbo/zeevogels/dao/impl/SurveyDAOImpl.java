package be.inbo.zeevogels.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import be.inbo.zeevogels.dao.SurveyDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;

@Repository
public class SurveyDAOImpl extends GenericHibernateDAO<Survey, Long> implements SurveyDAO {

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		Criteria crit = buildFilterCriteria(filters, new SortCriterium());
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult();
	}

	@Override
	public List<Survey> findFiltered(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium) {
		List<Survey> result = new ArrayList<Survey>();
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
		Criteria crit = getSession().createCriteria(Survey.class);
		boolean sortAdded = false;
		for (FilterCriterium filter : filters) {
			if (!StringUtils.isEmpty(filter.getValue())) {
				switch (filter.getProperty()) {
				case "status":
					Criteria statusCriteria = crit.createCriteria("status");
					statusCriteria.add(Restrictions.ilike("name", "%" + filter.getValue() + "%"));
					if ("status".equals(sortCriterium.getProperty())) {
						statusCriteria.addOrder("ASC".equals(sortCriterium.getDirection()) ? Order.asc("name") : Order
								.desc("name"));
						sortAdded = true;
					}
					break;
				default:
					crit.add(Restrictions.ilike(filter.getProperty(), "%" + filter.getValue() + "%"));
					if (sortCriterium.getProperty() != null) {
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
		Order order = "ASC".equals(sortCriterium.getDirection()) ? Order.asc(sortCriterium.getProperty()) : Order
				.desc(sortCriterium.getProperty());
		criteria.addOrder(order);
	}

}
