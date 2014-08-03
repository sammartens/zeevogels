package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.PlumageDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.Plumage;

@Repository
public class PlumageDAOImpl extends GenericHibernateDAO<Plumage, Long> implements PlumageDAO {

}
