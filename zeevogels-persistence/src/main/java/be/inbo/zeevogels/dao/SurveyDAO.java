package be.inbo.zeevogels.dao;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;

public interface SurveyDAO extends GenericDAO<Survey, Long> {

	Long countFiltered(List<FilterCriterium> filters);

	List<Survey> findFiltered(int start, int limit, List<FilterCriterium> filters, SortCriterium sortCriterium);

}
