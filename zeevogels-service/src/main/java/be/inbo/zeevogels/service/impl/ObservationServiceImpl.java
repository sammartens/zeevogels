package be.inbo.zeevogels.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.inbo.zeevogels.dao.ObservationDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.service.ObservationService;

@Service
@Transactional
public class ObservationServiceImpl implements ObservationService {
	@Autowired
	private ObservationDAO observationDAO;

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		return observationDAO.countFiltered(filters);
	}

	@Override
	public List<Observation> getFilteredObservations(int start, int limit, List<FilterCriterium> filters,
			SortCriterium sortCriterium) {
		List<Observation> observations = observationDAO.findFiltered(start, limit, filters, sortCriterium);
		return observations;
	}

	@Override
	public Observation update(Observation observationInput) {
		Observation observation = observationDAO.update(observationInput);
		return observation;
	}

	@Override
	public Observation create(Observation observationInput) {
		Observation observation = observationDAO.save(observationInput);
		return observation;
	}
}
