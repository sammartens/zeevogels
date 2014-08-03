package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.AgeDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.Age;

@Repository
public class AgeDAOImpl extends GenericHibernateDAO<Age, Long> implements AgeDAO {

}
