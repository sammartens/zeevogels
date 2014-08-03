package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.FlywayBehaviourDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.FlywayBehaviour;

@Repository
public class FlywayBehaviourDAOImpl extends GenericHibernateDAO<FlywayBehaviour, Long> implements FlywayBehaviourDAO {

}
