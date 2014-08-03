package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.StratumDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.Stratum;

@Repository
public class StratumDAOImpl extends GenericHibernateDAO<Stratum, Long> implements StratumDAO {

}
