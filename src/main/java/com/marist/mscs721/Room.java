/*
 *
 * (C) Copyright 2017 Tom Ginader
 *
 * Anyone can use the Room Scheduler program because it's open source and on github.com
 *
 * Contributers: Michael Gildein, Tom Ginader
 */
package com.marist.mscs721;

import java.util.ArrayList;

public class Room {	
	
	private String name;
	private int capacity;
	private ArrayList<Meeting> meetings;
	private String building;
	private String location;
	
	
	public Room(String newName, int newCapacity, String newBuilding, String newLocation) {
		setName(newName);
		setCapacity(newCapacity);
		setBuilding(newBuilding);
		setLocation(newLocation);
		setMeetings(new ArrayList<Meeting>());
	}

	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if(capacity >= 5) {
			this.capacity = capacity;
		}
		else {
			this.capacity = -1;
		}
	}

	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setBuilding(String building){
		this.building = building;
	}

	public String getBuilding(){
		return building;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}
}
