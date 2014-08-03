package be.inbo.zeevogels.service;

import java.util.List;

import be.inbo.zeevogels.domain.Age;
import be.inbo.zeevogels.domain.AssociationBehaviour;
import be.inbo.zeevogels.domain.FlywayBehaviour;
import be.inbo.zeevogels.domain.Plumage;
import be.inbo.zeevogels.domain.Ship;
import be.inbo.zeevogels.domain.Stratum;
import be.inbo.zeevogels.domain.Taxon;
import be.inbo.zeevogels.domain.TurbineHeight;

public interface ReferenceService {

	List<Ship> getAllShips();

	List<Age> getAllAges();

	List<Plumage> getAllPlumages();

	List<Stratum> getAllStratums();

	List<FlywayBehaviour> getAllFlywayBehaviours();

	List<AssociationBehaviour> getAllAssociationBehaviours();

	List<TurbineHeight> getAllTurbineHeights();

	List<Taxon> getAllTaxons();

}