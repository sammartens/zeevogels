package be.inbo.zeevogels.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import be.inbo.zeevogels.domain.Observation;
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.service.ObservationService;

@Component
@Path("observation")
public class ObservationResource extends ResourceBase {

	@Autowired
	private ObservationService observationService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getObservations(@QueryParam("page") @DefaultValue("1") Integer page,
			@QueryParam("start") @DefaultValue("0") Integer start,
			@QueryParam("limit") @DefaultValue(DEFAULT_PAGE_SIZE) Integer limit, @QueryParam("filter") String filter,
			@QueryParam("sort") String sort) throws JsonParseException, JsonMappingException, IOException {
		Long total;
		SortCriterium sortCriterium = new SortCriterium();
		sortCriterium.setProperty("taxon");
		sortCriterium.setDirection("ASC");
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
		total = observationService.countFiltered(filters);
		return buildSuccessfulResponse(
				observationService.getFilteredObservations(start, limit, filters, sortCriterium), total);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{tripId}")
	public Response update(Observation observationInput) {
		cleanupObservation(observationInput);
		Observation updatedObservation = observationService.update(observationInput);
		return buildSuccessfulResponse(updatedObservation);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Observation observationInput) {
		cleanupObservation(observationInput);
		Observation updatedObservation = observationService.create(observationInput);
		return buildSuccessfulResponse(updatedObservation);
	}

	private void cleanupObservation(Observation observation) {
		if (observation.getWp() == null) {
			observation.setWp(Boolean.FALSE);
		}
		if (observation.getAge().getId() == null) {
			observation.setAge(null);
		}
		if (observation.getPlumage().getId() == null) {
			observation.setPlumage(null);
		}
		if (observation.getStratum().getId() == null) {
			observation.setStratum(null);
		}
		if (observation.getTaxon().getId() == null) {
			observation.setTaxon(null);
		}
		if (observation.getFlywayBehaviour().getId() == null) {
			observation.setFlywayBehaviour(null);
		}
		if (observation.getAssociationBehaviour().getId() == null) {
			observation.setAssociationBehaviour(null);
		}
		if (observation.getTurbineHeight().getId() == null) {
			observation.setTurbineHeight(null);
		}
	}
}
