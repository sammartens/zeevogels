package be.inbo.zeevogels.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import be.inbo.zeevogels.dao.SurveyDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;

@ContextConfiguration(locations = { "classpath:persistence-context-test.xml" })
public class SurveyDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SurveyDAO surveyDAO;

	@Test
	public void testFindAll() {
		List<Survey> result = surveyDAO.findAll(0, 50);
		assertEquals(3, result.size());
	}

	@Test
	public void testFindById() {
		Survey result = surveyDAO.findById(Long.valueOf(1));
		assertNotNull(result);
		assertEquals("Mijn eerste survey", result.getName());
		assertEquals(1, result.getSurveyEvents().size());
	}

	@Test
	public void testFindFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("name", "eerste"));
		List<Survey> result = surveyDAO.findFiltered(0, 10, filters, new SortCriterium("name", "ASC"));
		assertEquals(1, result.size());
		assertEquals("Mijn eerste survey", result.get(0).getName());
	}

	@Test
	public void testCountFiltered() {
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		filters.add(new FilterCriterium("name", "eerste"));
		Long result = surveyDAO.countFiltered(filters);
		assertEquals(1, result.intValue());
	}
}
