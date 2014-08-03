package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.ShipDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.Ship;

@Repository
public class ShipDAOImpl extends GenericHibernateDAO<Ship, Long> implements ShipDAO {

}
