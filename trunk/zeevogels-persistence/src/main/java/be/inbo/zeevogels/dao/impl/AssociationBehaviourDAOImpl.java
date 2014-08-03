package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.AssociationBehaviourDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.AssociationBehaviour;

@Repository
public class AssociationBehaviourDAOImpl extends GenericHibernateDAO<AssociationBehaviour, Long> implements
		AssociationBehaviourDAO {

}
