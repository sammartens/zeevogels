package be.inbo.zeevogels.service;

import java.util.List;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;
import be.inbo.zeevogels.domain.SurveyListItem;

public interface SurveyService {

	Survey findById(Long id);

	Long countFiltered(List<FilterCriterium> filters);

	List<SurveyListItem> getFilteredSurveys(int start, int limit, List<FilterCriterium> filters,
			SortCriterium sortCriterium);

}
