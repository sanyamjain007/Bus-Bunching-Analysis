package org.iiitb.bunching.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.iiitb.bunching.modal.GPSData;
import org.joda.time.DateTime;

public class BunchingService {

	public ArrayList<GPSData> findbunchingOfGivenData(ArrayList<GPSData> data) {

		HashSet<Integer> deviceId = new HashSet<>();
		ArrayList<GPSData> locationData = new ArrayList<>();

		DateTime prevTime = null;

		Iterator<GPSData> actualList = data.iterator();
		// System.out.println("HI");
		GPSData prevRecord = null;
		while (actualList.hasNext()) {
			GPSData busRecord = actualList.next();

			String busTime = busRecord.getTime();

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

			Date date = null;
			try {
				date = dateFormat.parse(busTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			DateTime startTime = new DateTime(date);

			DateTime endTime = null;

			if (prevTime != null)
				endTime = prevTime.plusMinutes(7);

			if (startTime.isAfter(prevTime) && startTime.isBefore(endTime)) {

				if (!deviceId.contains(prevRecord.getDeviceId())) {
					locationData.add(prevRecord);
					// System.out.println(prevRecord.getDeviceId()+"
					// "+prevRecord.time);
					deviceId.add(prevRecord.getDeviceId());
				}
				if (!deviceId.contains(busRecord.getDeviceId())) {
					locationData.add(busRecord);
					// System.out.println(busRecord.getDeviceId()+"
					// "+busRecord.time);
					deviceId.add(busRecord.getDeviceId());
				}

			} else {
				deviceId.clear();
				// locationData.add(null);
			}

			prevTime = startTime;
			prevRecord = busRecord;

		}
		return locationData;
	}	
}
