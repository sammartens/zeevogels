package be.inbo.zeevogels.service;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;

public interface ObservationService {

	Long countFiltered(List<FilterCriterium> filters);

	List<Observation> getFilteredObservations(int start, int limit, List<FilterCriterium> filters,
			SortCriterium sortCriterium);

	Observation update(Observation observation);

	Observation create(Observation observation);

}
