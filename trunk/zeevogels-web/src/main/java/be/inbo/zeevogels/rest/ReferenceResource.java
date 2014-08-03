package be.inbo.zeevogels.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.inbo.zeevogels.service.ReferenceService;

@Component
@Path("references")
public class ReferenceResource extends ResourceBase {

	@Autowired
	private ReferenceService referenceService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ships")
	public Response getShips() {
		return buildSuccessfulResponse(referenceService.getAllShips());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ages")
	public Response getAges() {
		return buildSuccessfulResponse(referenceService.getAllAges());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/plumages")
	public Response getPlumages() {
		return buildSuccessfulResponse(referenceService.getAllPlumages());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stratums")
	public Response getStratums() {
		return buildSuccessfulResponse(referenceService.getAllStratums());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/flywayBehaviours")
	public Response getFlywayBehaviours() {
		return buildSuccessfulResponse(referenceService.getAllFlywayBehaviours());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/associationBehaviours")
	public Response getAssociationBehaviours() {
		return buildSuccessfulResponse(referenceService.getAllAssociationBehaviours());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/turbineHeights")
	public Response getTurbineHeights() {
		return buildSuccessfulResponse(referenceService.getAllTurbineHeights());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/taxons")
	public Response getTaxons() {
		return buildSuccessfulResponse(referenceService.getAllTaxons());
	}
}