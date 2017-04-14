/*
 *
 * (C) Copyright 2017 Tom Ginader
 *
 * Anyone can use the Room Scheduler program because it's open source and on github.com
 *
 * Contributers: Michael Gildein, Tom Ginader
 */
package com.marist.mscs721;

import java.sql.Timestamp;

public class Meeting {
	
	private Timestamp startTime = null;
	private Timestamp stopTime = null;
	private String subject = null;

	
	public Meeting(Timestamp newStartTime, Timestamp newEndTime, String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		if (newSubject.isEmpty()) {
			setSubject("N/A");
		}
		else {
			setSubject(newSubject);
		}
	}

	public String toString() {
		if(this.getStartTime() != null && this.getStopTime() != null && getSubject() != null) {
			return this.getStartTime().toString() + " - " + this.getStopTime() + ": " + getSubject();
		}
		return "One of the times specified is null or the subject is null";
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getStopTime() {
		return stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
