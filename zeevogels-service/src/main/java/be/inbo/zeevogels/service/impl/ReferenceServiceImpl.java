package be.inbo.zeevogels.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.inbo.zeevogels.dao.AgeDAO;
import be.inbo.zeevogels.dao.AssociationBehaviourDAO;
import be.inbo.zeevogels.dao.FlywayBehaviourDAO;
import be.inbo.zeevogels.dao.PlumageDAO;
import be.inbo.zeevogels.dao.ShipDAO;
import be.inbo.zeevogels.dao.StratumDAO;
import be.inbo.zeevogels.dao.TaxonDAO;
import be.inbo.zeevogels.dao.TurbineHeightDAO;
import be.inbo.zeevogels.domain.Age;
import be.inbo.zeevogels.domain.AssociationBehaviour;
import be.inbo.zeevogels.domain.FlywayBehaviour;
import be.inbo.zeevogels.domain.Plumage;
import be.inbo.zeevogels.domain.Ship;
import be.inbo.zeevogels.domain.Stratum;
import be.inbo.zeevogels.domain.Taxon;
import be.inbo.zeevogels.domain.TurbineHeight;
import be.inbo.zeevogels.service.ReferenceService;

@Service
@Transactional
public class ReferenceServiceImpl implements ReferenceService {

	@Autowired
	private ShipDAO shipDAO;
	@Autowired
	private AgeDAO ageDAO;
	@Autowired
	private PlumageDAO plumageDAO;
	@Autowired
	private StratumDAO stratumDAO;
	@Autowired
	private FlywayBehaviourDAO flywayBehaviourDAO;
	@Autowired
	private AssociationBehaviourDAO associationBehaviourDAO;
	@Autowired
	private TurbineHeightDAO turbineHeightDAO;
	@Autowired
	private TaxonDAO taxonDAO;

	@Override
	public List<Ship> getAllShips() {
		return shipDAO.findAll();
	}

	@Override
	public List<Age> getAllAges() {
		return ageDAO.findAll();
	}

	@Override
	public List<Plumage> getAllPlumages() {
		return plumageDAO.findAll();
	}

	@Override
	public List<Stratum> getAllStratums() {
		return stratumDAO.findAll();
	}

	@Override
	public List<FlywayBehaviour> getAllFlywayBehaviours() {
		return flywayBehaviourDAO.findAll();
	}

	@Override
	public List<AssociationBehaviour> getAllAssociationBehaviours() {
		return associationBehaviourDAO.findAll();
	}

	@Override
	public List<TurbineHeight> getAllTurbineHeights() {
		return turbineHeightDAO.findAll();
	}

	@Override
	public List<Taxon> getAllTaxons() {
		return taxonDAO.findAll();
	}
}
