package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	PremierLeagueDAO dao;
	Graph<Team, DefaultWeightedEdge> grafo;
	List<Team> lTeam;
	Map<Integer, Team> mTeam;
	//DATI LEO
	List<Team> teams;
	Map<Integer, Team> idMapTeams;
	List<Match> matches;
	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.mTeam = new TreeMap<>();
		
		this.lTeam = this.dao.listAllTeams();
		for(Team t : lTeam) {
			this.mTeam.put(t.getTeamID(), t);
		}
		Graphs.addAllVertices(this.grafo, this.lTeam);
		
		List<Match> matches = new ArrayList<> (this.dao.listAllMatches());
		
		//CALCOLO I PUNTEGGI IN CLASSIFICA
		for(Match m : matches) {
			Team home = this.mTeam.get(m.getTeamHomeID());
			Team guest = this.mTeam.get(m.getTeamAwayID());
			
			//int result = m.getResultOfTeamHome();
			
			if(m.getResultOfTeamHome() == 1) {
				//HA VINTO TEAM HOME
				home.aumentaPunteggio(3);
			}else if(m.getResultOfTeamHome() == -1) {
				//HA VINTO TEAM GUEST
				guest.aumentaPunteggio(3);
			}else {
				//PAREGGIO
				home.aumentaPunteggio(1);
				guest.aumentaPunteggio(1);
			}
		}
		//PROVA
		System.out.println("VERIFICA PUNTEGGI VALE");
		Collections.sort(lTeam);
		for(Team t : lTeam) {
			System.out.println(t.toString());
		}
		//FINE PROVA
		
		//INSERISCO GLI ARCHI
		for(Team t1 : lTeam) {
			for(Team t2 : lTeam) {
				if(!t1.equals(t2)) {
					
					DefaultWeightedEdge e1 = this.grafo.getEdge(t1, t2);
					DefaultWeightedEdge e2 = this.grafo.getEdge(t2, t1);
					
					if(e1 == null && e2 == null) {
						
						int diff = Math.abs(t1.getPunteggio()-t2.getPunteggio());
						
						if(diff != 0) {
							
							if(t1.getPunteggio() > t2.getPunteggio()) {
								// DA T1 A T2
								Graphs.addEdge(this.grafo, t1, t2, diff);
							}else {
								Graphs.addEdge(this.grafo, t2, t1, diff);
							}
						}
					}
				}
			}
		}
		System.out.println("GRAFO CREATO");
		System.out.println("VERTICI : "+this.grafo.vertexSet().size());
		System.out.println("ARCHI : "+this.grafo.edgeSet().size());
	}
	
	public void simula(int n, int x) {
		
	}

	/*public void creaGrafoLeo() {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		if(this.teams == null) {
			this.teams = new ArrayList<>(this.dao.listAllTeams());
		}
		
		this.idMapTeams = new TreeMap<>();
		
		for(Team t : this.teams) {
			idMapTeams.put(t.getTeamID(), t);
		}
		
		Graphs.addAllVertices(this.grafo, this.teams);
		
		if(this.matches == null)
			this.matches = new ArrayList<>(this.dao.listAllMatches());
		
		for(Match m : this.matches) {
			Team home = idMapTeams.get(m.getTeamHomeID());
			Team away = idMapTeams.get(m.getTeamAwayID());
			if(m.getResultOfTeamHome() == 1)
				home.increasePoints(3);
			else if(m.getResultOfTeamHome() == -1)
				away.increasePoints(3);
			else {
				home.increasePoints(1);
				away.increasePoints(1);
			}
		}
	
		Collections.sort(this.teams);
			//PROVA
			System.out.println("VERIFICA PUNTEGGI LEO");
			
			for(Team t : teams) {
				System.out.println(t.toString());
			}
			//FINE PROVA
				
		
		for(Team t1 : this.teams) {
			for(Team t2 : this.teams) {
				if(t1.compareTo(t2) < 0) {
					Graphs.addEdge(this.grafo, t1, t2, t1.getPoints() - t2.getPoints());
				}
			}
			
		}
		
		
	}*/
	public List<Team> getlTeam() {
		return lTeam;
	}
	public void setlTeam(List<Team> lTeam) {
		//lTeam = this.dao.listAllTeams();
		this.lTeam = lTeam;
	}
	public Graph<Team, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
}
