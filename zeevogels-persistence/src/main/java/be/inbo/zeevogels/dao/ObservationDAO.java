package be.inbo.zeevogels.dao;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;

public interface ObservationDAO extends GenericDAO<Observation, Long> {

	Long countFiltered(List<FilterCriterium> filters);

	List<Observation> findFiltered(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium);

}
