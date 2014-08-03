package be.inbo.zeevogels.service;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;

public interface TripService {

	Long countFiltered(List<FilterCriterium> filters);

	List<Trip> getFilteredTrips(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium);

	Trip update(Trip trip);

	Trip create(Trip trip);

}
