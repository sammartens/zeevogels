package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.TurbineHeightDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.TurbineHeight;

@Repository
public class TurbineHeightDAOImpl extends GenericHibernateDAO<TurbineHeight, Long> implements TurbineHeightDAO {

}
