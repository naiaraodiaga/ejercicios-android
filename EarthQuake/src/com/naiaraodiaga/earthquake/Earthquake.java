package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
import java.util.Date;

public class Earthquake {

	public String place;
	public Date time;
	public String detail;
	public double magnitude;
	public double lat;
	public double lon;
	public String url;
	
	public Earthquake(String place, Long time, String detail, double magnitude, double lat, double lon, String url) {
		Date date = new Date(time);
		
		this.place = place;
		this.time = date;
		this.detail = detail;
		this.magnitude = magnitude;
		this.lat = lat;
		this.lon = lon;
		this.url = url;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Long time) {
		Date date = new Date(time);
		this.time = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

	
}
