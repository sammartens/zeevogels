package be.inbo.zeevogels.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;

@ContextConfiguration(locations = { "classpath:persistence-context-test.xml" })
public class TripDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TripDAO tripDAO;

	@Test
	public void testFindFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("survey", "eerste"));
		List<Trip> result = tripDAO.findFiltered(0, 10, filters, new SortCriterium("survey", "ASC"));
		assertEquals(1, result.size());
		assertEquals("Mijn eerste survey", result.get(0).getSurvey());
		assertEquals("De marie-louise", result.get(0).getShip().getName());
	}

	@Test
	public void testCountFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("survey", "eerste"));
		Long result = tripDAO.countFiltered(filters);
		assertEquals(1, result.intValue());
	}
}
