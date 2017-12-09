package org.iiitb.bunching.modal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusStop {
	
	private String latitude;
	private String longitude;
	private String busStopName;
	private String sourceToDestination;
	private String destinationToSource;
	public BusStop() {
	}

	public BusStop(String latitude, String longitude, String busStopName,String directionOne,String directionTwo) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.busStopName = busStopName;
		this.sourceToDestination = directionOne;
		this.destinationToSource = directionTwo;
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

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBusStopName() {
		return busStopName;
	}

	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
		
	}

	public String getSourceToDestination() {
		return sourceToDestination;
	}

	public void setSourceToDestination(String sourceToDestination) {
		this.sourceToDestination = sourceToDestination;
	}

	public String getDestinationToSource() {
		return destinationToSource;
	}

	public void setDestinationToSource(String destinationToSource) {
		this.destinationToSource = destinationToSource;
	}
	
	
}
