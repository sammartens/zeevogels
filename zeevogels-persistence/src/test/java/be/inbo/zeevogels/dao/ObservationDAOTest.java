package be.inbo.zeevogels.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;

@ContextConfiguration(locations = { "classpath:persistence-context-test.xml" })
public class ObservationDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ObservationDAO observationDAO;

	@Test
	public void testFindFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("groupCode", "groep1"));
		List<Observation> result = observationDAO.findFiltered(0, 10, filters, new SortCriterium("groupCode", "ASC"));
		assertEquals(1, result.size());
		assertEquals("dit is groep1", result.get(0).getGroupCode());
		assertEquals("Gavia stellata", result.get(0).getTaxon().getValue());
		assertEquals("Adult", result.get(0).getAge().getValue());
		assertEquals("Eerste kalenderjaar", result.get(0).getPlumage().getValue());
		assertEquals("Nultelling", result.get(0).getStratum().getValue());
		assertEquals("Vliegt naar noorden", result.get(0).getFlywayBehaviour().getValue());
		assertEquals("Ander schip", result.get(0).getAssociationBehaviour().getValue());
		assertEquals("Vogel vliegt ter hoogte van de rotor", result.get(0).getTurbineHeight().getValue());
	}

	@Test
	public void testCountFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("groupCode", "groep1"));
		Long result = observationDAO.countFiltered(filters);
		assertEquals(1, result.intValue());
	}
}
