package be.inbo.zeevogels.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.inbo.zeevogels.domain.FilterCriterium;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Survey;
import be.inbo.zeevogels.exception.DeletedException;
import be.inbo.zeevogels.service.SurveyService;

@Component
@Path("survey")
public class SurveyResource extends ResourceBase {

	@Autowired
	private SurveyService surveyService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{surveyId}")
	public Response getById(@PathParam("surveyId") Long surveyId) {
		try {
			Survey survey = surveyService.findById(surveyId);
			return buildSuccessfulResponse(survey);
		} catch (DeletedException e) {
			return buildFailureResponse(e.getMessage());
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getSurveys(@QueryParam("page") @DefaultValue("1") Integer page,
			@QueryParam("start") @DefaultValue("0") Integer start,
			@QueryParam("limit") @DefaultValue(DEFAULT_PAGE_SIZE) Integer limit, @QueryParam("filter") String filter,
			@QueryParam("sort") String sort) throws JsonParseException, JsonMappingException, IOException {
		Long total;
		SortCriterium sortCriterium = new SortCriterium();
		sortCriterium.setProperty("name");
		sortCriterium.setDirection("DESC");
		if (sort != null) {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<?> typeReference = new TypeReference<List<SortCriterium>>() {
			};
			List<SortCriterium> sorters = mapper.readValue(sort, typeReference);
			sortCriterium = sorters.get(0);
		}
		List<FilterCriterium> filters = new ArrayList<FilterCriterium>();
		if (filter != null) {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<?> typeReference = new TypeReference<List<FilterCriterium>>() {
			};
			filters = mapper.readValue(filter, typeReference);
		}
		total = surveyService.countFiltered(filters);
		return buildSuccessfulResponse(surveyService.getFilteredSurveys(start, limit, filters, sortCriterium), total);
	}

}
