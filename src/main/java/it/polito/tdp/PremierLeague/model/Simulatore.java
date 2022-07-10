package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulatore {

	//DATI IN INGRESSO
	private int N; //NUMERO TOTALE INTERVISTATORI
	private int X; //INTERVISTATORI PER PARTITA
	Graph<Team, DefaultWeightedEdge> grafo;
	List<Match> partite; //DA ORDINARE IN ORDINE CRONOLOGICO

	//DATI IN USCITA
	private double reporterMedi;//REPORTER MEDI PER OGNI PARTITA
	
	//LE SEGUENTI 2 VARIABILI SARANNO DA AGGIORNARE AD OGNI PARTITA PER MANTENERE TRACCIA	
	private int totReporterPartite;
	private int numPartite;
	
	private int partiteCritiche; //NUMERO PARTITE IN CUI IL NUMERO DI REPORTER ERA < X
	
	//STATO DEL MONDO : intervistatori liberi / occupati ecc
	//List<Reporter> reporter;
	Map<Team, Integer> reporterTeam;
	List<Team> team;
	Map<Integer, Team> mTeam;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	public Simulatore(int n, int x, Graph<Team, DefaultWeightedEdge> grafo, List<Match> partite) {
		super();
		N = n;
		X = x;
		this.grafo = grafo;
		this.partite = partite;
	}
	
	public void init() {
		this.reporterMedi = 0.0;
		this.totReporterPartite = 0;
		this.numPartite = 0;
		this.partiteCritiche = 0;
		
		Collections.sort(this.partite); //ORDINO LE PARTITE IN ORDINE CRONOLOGICO
		
		this.queue = new PriorityQueue<>();
		
		this.reporterTeam = new TreeMap<>();
		
		this.team = new ArrayList<>(this.grafo.vertexSet());
		for(Team t : team) {
			this.reporterTeam.put(t, N);
		}
		
		this.mTeam = new TreeMap<>();
		for(Team t : team) {
			this.mTeam.put(t.getTeamID(), t);
		}
		
		
		//CREO GLI EVENTI DA METTERE NELLA QUEUE
		for(Match m : partite) {
			Team home = this.mTeam.get(m.getTeamHomeID());
			Team guest = this.mTeam.get(m.getTeamAwayID());
			
			int risultato = m.getResultOfTeamHome();
			if(risultato == 1) {
				
				//TEAM HOME VINCENTE - MODIFICO GIORNALISTI TEAM HOME
				if(Math.random() < 0.5) {
					//PROMUOVO GIORNALISTI
					if(this.reporterTeam.get(home)>0) {
						
					Team migliore = cercaTeamMigliore(home);
					if(migliore != null) {
						Event e = new Event(m.getDate(), EventType.PROMOZIONE, migliore);
						this.queue.add(e);
					}else {
						Event e = new Event(m.getDate(), EventType.REGOLARE, home);
						this.queue.add(e);
					}
					}
						
				}else {
					//I GIORNALISTI RIMANGONO NELLO STESSO TEAM
					Event e = new Event(m.getDate(), EventType.REGOLARE, home);
					this.queue.add(e);
				}
				
				//MODIFICO GIORNALISTI TEAM GUEST
				
			}else if(risultato == -1) {
				//TEAM GUEST VINCENTE
				
				
			}else {
				//PAREGGIO 
				
				
				
			}
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}
	public void processEvent(Event e) {
		
	}
	
	public Team cercaTeamMigliore(Team precedente) {
		//SE NON CI SONO TEAM MIGLIORI RITORNO NULL
		if(this.grafo.incomingEdgesOf(precedente).size() == 0)
			return null;
		
		List<Avversario> battenti = new LinkedList<Avversario>();
		
		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(precedente)) {
			Team battente = this.grafo.getEdgeSource(e);
			int peso = (int) this.grafo.getEdgeWeight(e);
			
			Avversario a = new Avversario(battente, peso);
			battenti.add(a);
		}
		
		Collections.sort(battenti);
		Team ultimo = battenti.get(battenti.size()-1).getAvversario();
		return ultimo;
	}
}
