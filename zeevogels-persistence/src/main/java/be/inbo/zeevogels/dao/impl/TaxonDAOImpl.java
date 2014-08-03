package be.inbo.zeevogels.dao.impl;

import org.springframework.stereotype.Repository;

import be.inbo.zeevogels.dao.TaxonDAO;
import be.inbo.zeevogels.dao.hibernate.GenericHibernateDAO;
import be.inbo.zeevogels.domain.Taxon;

@Repository
public class TaxonDAOImpl extends GenericHibernateDAO<Taxon, Long> implements TaxonDAO {

}
