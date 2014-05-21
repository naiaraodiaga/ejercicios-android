package com.naiaraodiaga.earthquake;

import java.sql.Timestamp;
import java.util.Date;

public class Earthquake {

	private long _id;
	private String idStr;
	private String place;
	private Date time;
	private String detail;
	private double magnitude;
	private double lat;
	private double lon;
	private String url;
	
	public Earthquake() {
		
	}
	
	public Earthquake(long id, String idStr, String place, Long time, String detail, double magnitude, double lat, double lon, String url) {
		this(idStr, place, time, detail, magnitude, lat, lon, url);
		this._id = id;
	}
	
	public Earthquake(String idStr, String place, Long time, String detail, double magnitude, double lat, double lon, String url) {
		Date date = new Date(time);
		
		this.idStr = idStr;
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
	
	
	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return this.magnitude + " " + this.place;
	}
	
}
