package org.iiitb.bunching.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iiitb.bunching.modal.BusStop;
import org.iiitb.bunching.service.BusStopServices;

@Path("/routeNo")
@Produces(value = { MediaType.APPLICATION_JSON })
public class BusStopDataResource {

	@GET
	@Path("/{routeNo}")
	public Response getBusStops(@PathParam("routeNo") String routeNumber) {

		BusStopServices services = new BusStopServices();

		List<BusStop> list = services.getBusStop(routeNumber);
		// System.out.println(list.size());

		if (list.isEmpty()) {
			return Response.noContent().build();
		} else {
			return Response.ok().entity(new GenericEntity<List<BusStop>>(list) {
					}).build();

		}
	}

}
