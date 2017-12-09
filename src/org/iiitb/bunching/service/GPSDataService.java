package org.iiitb.bunching.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.iiitb.bunching.database.DataAccessClass;
import org.iiitb.bunching.modal.BusStop;
import org.iiitb.bunching.modal.GPSData;

public class GPSDataService {

	public ArrayList<GPSData> getGPSDataForBusStop(String RouteNo, String direction, String date,
			String busStoplatitude, String busStoplongitude, String busStopName, int busStopNumber) {

		Connection connection = DataAccessClass.getInstance().Connect();

		ArrayList<GPSData> listOfGPSdata = new ArrayList<>();

		PreparedStatement preparestatement;

		String sql = "SELECT DeviceId,latitude,longitude,time,direction,"
				+ "(7912 * ASIN(SQRT( POWER(SIN((? - abs(latitude)) * pi()/180 / 2), 2) + COS(? * pi()/180 ) * COS(abs(latitude) * pi()/180) * POWER(SIN((?-LONGITUDE) * pi()/180 / 2), 2) )))"
				+ "  as distance FROM " + RouteNo + " WHERE  direction = ? and date = ? having  distance < 0.0534";

		try {

			preparestatement = connection.prepareStatement(sql);

			preparestatement.setObject(1, Double.parseDouble(busStoplatitude));

			preparestatement.setObject(2, Double.parseDouble(busStoplatitude));

			preparestatement.setObject(3, Double.parseDouble(busStoplongitude));

			preparestatement.setObject(4, direction);

			preparestatement.setObject(5, date);

			ResultSet rs = preparestatement.executeQuery();

			GPSData oldObject = null;

			while (rs.next()) {

				GPSData newObj = new GPSData(rs.getInt("deviceId"), rs.getString("latitude"), rs.getString("longitude"),
						rs.getString("direction"), rs.getDouble("distance"), rs.getString("time"), date, busStopName,
						busStopNumber);

				if (!newObj.equals(oldObject)) {
					listOfGPSdata.add(newObj);
				}

				oldObject = newObj;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		BunchingService service = new BunchingService();

		return service.findbunchingOfGivenData(listOfGPSdata);
	}

	
	public ArrayList<GPSData> getOneWeekGPSDataForBusStop(String RouteNo, String direction, String date,
			String busStoplatitude, String busStoplongitude, String busStopName, int busStopNumber) {
			
		ArrayList<GPSData> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		
		Calendar cal = Calendar.getInstance(); 

		for (int i = 1; i <= 7; i++) {

			list.addAll(getGPSDataForBusStop(RouteNo, direction, date, busStoplatitude, busStoplongitude, busStopName, i));
			
			
			try {
				Date d = sdf.parse(date);
				cal.setTime(d);
				cal.add(Calendar.DATE, 1);
				d = cal.getTime();
				date = sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("list Size :"+list.size());
			System.out.println("next Date :" + date);

		}

		return list;	
		
	}
	
	// get GPS Data for specific time frame
	public ArrayList<GPSData> getGPSDataForBusStop(String RouteNo, String direction, String date, String latitude,
			String longitude, String startTime, String endTime, String busStopName, int busStopNumber) {

		ArrayList<GPSData> listOfGPSdata = new ArrayList<>();

		Connection connection = DataAccessClass.getInstance().Connect();

		PreparedStatement preparestatement;

		// introduce date also

		String sql = "SELECT DeviceId,latitude,longitude,time,direction,"
				+ "(7912 * ASIN(SQRT( POWER(SIN((? - abs(latitude)) * pi()/180 / 2), 2) + COS(? * pi()/180 ) * COS(abs(latitude) * pi()/180) * POWER(SIN((?-LONGITUDE) * pi()/180 / 2), 2) )))"
				+ "  as distance FROM " + RouteNo
				+ " WHERE  direction = ? and date = ? and time between ? and ? having  distance < 0.066";
		/**/

		try {

			preparestatement = connection.prepareStatement(sql);

			preparestatement.setObject(1, Double.parseDouble(latitude));

			preparestatement.setObject(2, Double.parseDouble(latitude));

			preparestatement.setObject(3, Double.parseDouble(longitude));

			preparestatement.setObject(4, direction);

			preparestatement.setObject(5, date);

			preparestatement.setObject(6, startTime);

			preparestatement.setObject(7, endTime);

			// System.out.println(latitude+" "+longitude);

			ResultSet rs = preparestatement.executeQuery();

			GPSData oldObject = null;

			while (rs.next()) {

				GPSData newObj = new GPSData(rs.getInt("deviceId"), rs.getString("latitude"), rs.getString("longitude"),
						rs.getString("direction"), rs.getDouble("distance"), rs.getString("time"), date, busStopName,
						busStopNumber);

				if (!newObj.equals(oldObject)) {
					listOfGPSdata.add(newObj);
				}

				oldObject = newObj;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		BunchingService service = new BunchingService();

		listOfGPSdata = service.findbunchingOfGivenData(listOfGPSdata);

		return listOfGPSdata;

	}

	public ArrayList<GPSData> getAllGPSData(String routeNumber, String direction, String date, String startTime,
			String endTime) {

		BusStopServices services = new BusStopServices();

		List<BusStop> listOfBusStop = services.getBusStop(routeNumber);

		ArrayList<GPSData> list = new ArrayList<>();

		int i = 1;
		for (BusStop busStop : listOfBusStop) {

			System.out.println("bus stop " + i);

			list.addAll(getGPSDataForBusStop("K_4", direction, date, busStop.getLatitude(), busStop.getLongitude(),
					startTime, endTime, busStop.getBusStopName(), i));

			i++;

		}

		return list;
	}

	// find all GPS data for one week
	public ArrayList<GPSData> getOneWeekGPSData(String routeNumber, String date,String direction, String startTime,
			String endTime) {

		ArrayList<GPSData> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		
		
		Calendar cal = Calendar.getInstance(); 

		for (int i = 1; i <= 7; i++) {

			list.addAll(getAllGPSData(routeNumber, direction, date, startTime, endTime));
			
			System.out.println(list.size());
			
			try {
				Date d = sdf.parse(date);
				cal.setTime(d);
				cal.add(Calendar.DATE, 1);
				d = cal.getTime();
				date = sdf.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			System.out.println("next Date :" + date);

		}

		return list;

	}

	/*
	 * replace public ArrayList<GPSData> entireDayData(String RouteNo, String
	 * direction, String date, String latitude, String longitude){return null;}
	 */

	public ArrayList<GPSData> getGPSDataForWholeDay(String RouteNo, String date, String direction) {
		BusStopServices services = new BusStopServices();

		// Retrieving the list of bus stops along with direction , it contains
		// the name of both up and down direction
		List<BusStop> listOfBusStop = services.getBusStop(RouteNo);

		ArrayList<GPSData> list = new ArrayList<>();

		int i = 1; // for bus Stop Name assigment manually

		for (BusStop busStop : listOfBusStop) {
			
			System.out.println("name :"+busStop.getBusStopName());
			
			list.addAll(getGPSDataForBusStop("K_4", direction, date, busStop.getLatitude(), busStop.getLongitude(),
					busStop.getBusStopName(), i));
			
			i++;
		}
		return list;
	}

	public static void main(String args[]) throws ParseException {

		
		GPSDataService s = new GPSDataService();
		
		/*ArrayList<GPSData> al = new ArrayList<>();

		String direction = "nagarabhavi bda complex nagarabhavi 2nd stage beside bda complex nagarabhavi to cv raman nagara";

		// ------------------bunching at a particular bus stop (entire
		// day)---------------------------------------//

		System.out.println("1/06/2016");


		String date = "13/06/2016";
		// TreeMap<Integer,TreeMap<DateTime,ArrayList<GPSData>>> data =
		 al=s.getOneWeekGPSData("K-4",date,direction,"18:15:00","20:00:00");
		 for(GPSData obj:al){
			 System.out.println(obj.getDeviceId()+"  "+obj.getTime()+"  "+obj.getBusStopID()+"  "+obj.getDate());
		 }
	*/

	}

}
