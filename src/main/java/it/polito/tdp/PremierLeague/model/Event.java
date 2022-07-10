package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;

public class Event implements Comparable <Event>{

	public enum EventType{
		PROMOZIONE,
		BOCCIATURA,
		REGOLARE
	}
	
	private LocalDateTime time;
	private EventType type;
	private Team squadraAssegnata;
	//private Reporter reporter;


	
	
	public Event(LocalDateTime time, EventType type, Team squadraAssegnata) {
		super();
		this.time = time;
		this.type = type;
		this.squadraAssegnata = squadraAssegnata;
		//this.reporter = reporter;
	}

	


	public LocalDateTime getTime() {
		return time;
	}




	public EventType getType() {
		return type;
	}




	public Team getSquadraAssegnata() {
		return squadraAssegnata;
	}




	




	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}
	
	
	
}
