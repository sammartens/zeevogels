package be.inbo.zeevogels.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.inbo.zeevogels.dao.SurveyDAO;
import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;
import be.inbo.zeevogels.domain.SurveyListItem;
import be.inbo.zeevogels.service.SurveyService;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyDAO surveyDAO;

	@Override
	public Survey findById(Long id) {
		return surveyDAO.findById(id);
	}

	@Override
	public Long countFiltered(List<FilterCriterium> filters) {
		return surveyDAO.countFiltered(filters);
	}

	@Override
	public List<SurveyListItem> getFilteredSurveys(int start, int limit, List<FilterCriterium> filters,
			SortCriterium sortCriterium) {
		List<SurveyListItem> result = new ArrayList<SurveyListItem>();
		List<Survey> identificaties = surveyDAO.findFiltered(start, limit, filters, sortCriterium);
		for (Survey identificatie : identificaties) {
			SurveyListItem listItem = mapSurveyToListItem(identificatie);
			result.add(listItem);
		}
		return result;

	}

	private SurveyListItem mapSurveyToListItem(Survey survey) {
		SurveyListItem listItem = new SurveyListItem();
		listItem.setId(survey.getId());
		listItem.setName(survey.getName());
		listItem.setAdminName(survey.getAdminName());
		listItem.setDescription(survey.getDescription());
		listItem.setStatus(survey.getStatus().getName());
		listItem.setStartDate(survey.getStartDate());
		listItem.setEndDate(survey.getEndDate());
		return listItem;
	}
}
