package org.iiitb.bunching.modal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GPSData {

	private int deviceId;
	private String latitude;
	private String longitude;
	private String direction;

	private double distance;

	private String time;

	private String date;
	private String busStopName;
	private Integer busStopID;

	public GPSData() {
	}

	public GPSData(int deviceId, String latitude, String longitude, String direction, double distance, String time,
			String date, String busStopName, Integer busStopID) {
		super();
		this.deviceId = deviceId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.direction = direction;
		this.distance = distance;
		this.time = time;
		this.date = date;
		this.busStopName = busStopName;
		this.busStopID = busStopID;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBusStopName() {
		return busStopName;
	}

	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deviceId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPSData other = (GPSData) obj;
		if (deviceId != other.deviceId)
			return false;

		return true;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getBusStopID() {
		return busStopID;
	}

	public void setBusStopID(Integer busStopID) {
		this.busStopID = busStopID;
	}

	@Override
	public String toString() {
		return "GPSData [deviceId=" + deviceId + ", latitude=" + latitude + ", longitude=" + longitude + ", direction="
				+ direction + ", distance=" + distance + ", time=" + time + ", date=" + date + ", busStopName="
				+ busStopName + ", busStopID=" + busStopID + "]";
	}

}
