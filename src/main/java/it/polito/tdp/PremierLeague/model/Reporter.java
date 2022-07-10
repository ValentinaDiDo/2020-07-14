package it.polito.tdp.PremierLeague.model;

public class Reporter {

	private int id;
	private Team squadraAssegnata;
	public Reporter(int id) {
		super();
		this.id = id;
	}
	public Team getSquadraAssegnata() {
		return squadraAssegnata;
	}
	public void setSquadraAssegnata(Team quadraAssegnata) {
		this.squadraAssegnata = quadraAssegnata;
	}
	public int getId() {
		return id;
	}
	
	
}
