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
import be.inbo.zeevogels.domain.SortCriterium;
import be.inbo.zeevogels.domain.Trip;
import be.inbo.zeevogels.service.TripService;

@Component
@Path("trip")
public class TripResource extends ResourceBase {

	@Autowired
	private TripService tripService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrips(@QueryParam("page") @DefaultValue("1") Integer page,
			@QueryParam("start") @DefaultValue("0") Integer start,
			@QueryParam("limit") @DefaultValue(DEFAULT_PAGE_SIZE) Integer limit, @QueryParam("filter") String filter,
			@QueryParam("sort") String sort) throws JsonParseException, JsonMappingException, IOException {
		Long total;
		SortCriterium sortCriterium = new SortCriterium();
		sortCriterium.setProperty("survey");
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
		total = tripService.countFiltered(filters);
		return buildSuccessfulResponse(tripService.getFilteredTrips(start, limit, filters, sortCriterium), total);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{tripId}")
	public Response update(Trip tripInput) {
		cleanupTrip(tripInput);
		Trip updatedTrip = tripService.update(tripInput);
		return buildSuccessfulResponse(updatedTrip);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Trip tripInput) {
		cleanupTrip(tripInput);
		Trip updatedTrip = tripService.create(tripInput);
		return buildSuccessfulResponse(updatedTrip);
	}

	private void cleanupTrip(Trip trip) {
		if (trip.getShip().getId() == null) {
			trip.setShip(null);
		}
	}
}
