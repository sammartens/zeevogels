package be.inbo.zeevogels.dao;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;

public interface TripDAO extends GenericDAO<Trip, Long> {

	Long countFiltered(List<FilterCriterium> filters);

	List<Trip> findFiltered(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium);

}
