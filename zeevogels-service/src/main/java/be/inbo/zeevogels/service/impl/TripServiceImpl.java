package be.inbo.zeevogels.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.inbo.zeevogels.dao.TripDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;
import be.inbo.zeevogels.service.TripService;

@Service
@Transactional
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDAO tripDAO;

	// @Override
	// public Survey findById(Long id) {
	// return surveyDAO.findById(id);
	// }

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		return tripDAO.countFiltered(filters);
	}

	@Override
	public List<Trip> getFilteredTrips(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium) {
		List<Trip> trips = tripDAO.findFiltered(start, limit, filters, sortCriterium);
		return trips;
	}

	@Override
	public Trip update(Trip tripInput) {
		Trip trip = tripDAO.update(tripInput);
		return trip;
	}

	@Override
	public Trip create(Trip tripInput) {
		Trip trip = tripDAO.save(tripInput);
		return trip;
	}
}
