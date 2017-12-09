package org.iiitb.bunching.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.iiitb.bunching.modal.GPSData;
import org.iiitb.bunching.service.GPSDataService;

@Path("/GPSData")
@Produces(value = { MediaType.APPLICATION_JSON })
public class GPSDataResource {

	private GPSDataService gpsService = new GPSDataService();

	@GET
	@Path("/{busStopNumber}")
	public Response getGPSdata(@Context UriInfo uriInfo, @PathParam("busStopNumber") int busStopNumber) {

		MultivaluedMap<String, String> queryParam = uriInfo.getQueryParameters();

		String RouteNo = queryParam.get("routeNo").get(0);
		String Direction = queryParam.get("direction").get(0);
		String date = queryParam.get("date").get(0);
		String latitude = queryParam.get("lat").get(0);
		String longitude = queryParam.get("lon").get(0);
		String busStopName = queryParam.get("name").get(0);
		
		
		String week = queryParam.get("week")!=null ?  queryParam.get("week").get(0):null;

		System.out.println(RouteNo + "  " + Direction + " " + date + "  " + latitude + "  " + longitude + "  "
				+ busStopName + "  " + busStopNumber+"  "+week);
		List<GPSData> list = null;
		
		if(week==null){
		list = gpsService.getGPSDataForBusStop(RouteNo, Direction, date, latitude, longitude, busStopName,
				busStopNumber);
		}else{
			list = gpsService.getOneWeekGPSDataForBusStop(RouteNo, Direction, date, latitude, longitude, busStopName, busStopNumber);
		}

		if (list.isEmpty()) {
			return Response.noContent().build();
		} else {
			return Response.ok().entity(new GenericEntity<List<GPSData>>(list) {
			}).build();

		}
	}

	@GET
	public Response getGPSdata(@QueryParam("routeNo") String routeNumber, @QueryParam("date") String date,
			@QueryParam("direction") String direction, @QueryParam("start") String startTime,
			@QueryParam("end") String endTime) {

		System.out.println(routeNumber + "  " + date + " " + direction + " " + startTime + " " + endTime);

		ArrayList<GPSData> list = null;

		if (startTime == null || endTime == null) {
			list = gpsService.getGPSDataForWholeDay(routeNumber, date, direction);
		} else {
			list = gpsService.getOneWeekGPSData(routeNumber, date, direction, startTime, endTime);
		}

		if (list.isEmpty()) {
			return Response.noContent().build();
		} else {
			return Response.ok().entity(new GenericEntity<List<GPSData>>(list) {
			}).build();

		}
	}

}
