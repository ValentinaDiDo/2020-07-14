package it.polito.tdp.PremierLeague.model;

public class Avversario implements Comparable<Avversario>{
	private Team avversario;
	private int differenzaPunti;
	
	public Avversario(Team avversario, int differenzaPunti) {
		super();
		this.avversario = avversario;
		this.differenzaPunti = differenzaPunti;
	}
	public Team getAvversario() {
		return avversario;
	}
	public int getDifferenzaPunti() {
		return differenzaPunti;
	}
	
	
	@Override
	public String toString() {
		return avversario.getName()+" | "+differenzaPunti;
	}
	@Override
	public int compareTo(Avversario o) {
		// TODO Auto-generated method stub
		return (int)(this.differenzaPunti - o.differenzaPunti);
	}
	
	
}
