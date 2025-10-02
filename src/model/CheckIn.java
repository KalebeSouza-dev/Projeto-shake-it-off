package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import ShakeItOff.Comentario;
import exception.ShakeItOffException;

public class CheckIn implements Comparable<CheckIn>{
	private String idCheckin;
	private String idUser;
	private String nomeUser;
	private String atividade;
	private String intensidade;
	private LocalDateTime dataHora;
	private int duracaoMin;
	private int calorias;
	private int distancia;
	private HashSet<String> curtidas;
	private ArrayList<Comentario> comentarios;
	
	public CheckIn(String idUsuario, String nomeUser, LocalDateTime dataHora, String atividade, int duracaoMin, String intensidade, int calorias, int distancia) {
		this.idUser = idUsuario;
		this.nomeUser = nomeUser;
		this.dataHora = dataHora;
		this.atividade = atividade;
		this.intensidade = intensidade;
		this.duracaoMin = duracaoMin;
		this.calorias = calorias;
		this.distancia = distancia;
		this.curtidas = new HashSet<>();
		this.comentarios = new ArrayList<>();
	}
	
	@Override
	public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatada = this.dataHora.format(formatter);
	    String out = "";
	    
        out += "[" + this.idCheckin + "]" + " - " +
	           formatada + " " + "[" + idUser + "] " + nomeUser + " - " +
	           atividade + " - " +
	           duracaoMin + "min";
	    if(!intensidade.equals(""))
	    	out += " - INTENSIDADE: " + intensidade;
	    if(!(this.calorias == 0))
	    	out += " - " + calorias + " kcal";
	    
	    out += "\nLikes: " + this.curtidas.size();
	    out += "\nComentários:";
	    
	    if (!(comentarios.size() == 0)) {
	    	for(Comentario comentario: comentarios) {
	    		out += "\n- " + comentario.toString();
	    	}
	    }
	    
	    return out;
	} 
	@Override
	public int compareTo(CheckIn o) {
		if(o.getDataHora().isBefore(this.dataHora)) return 1;
		else if (o.getDataHora().isEqual(this.dataHora)) return 0;
		else return -1;
	}
	public void setId(String idCheckin) {
		this.idCheckin = idCheckin;
	}
	public String getIntensidade() {
		return intensidade;
	}

	public void setIntensidade(String intensidade) {
		this.intensidade = intensidade;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	public String exibirCheckin(String nomeDesafio) {
		//DD-MM-YYYY <titulo do desafio> <#CHK> <Atv> <Tempo>min <Intensidade>
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dataFormatada = this.dataHora.format(formatter); 
		return String.format("%s %s %s %s %d %s",dataFormatada,nomeDesafio,this.idCheckin,this.atividade,this.duracaoMin,this.intensidade);
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public int getDuracaoMin() {
		return duracaoMin;
	}

	public void setDuracaoMin(int duracaoMin) {
		this.duracaoMin = duracaoMin;
	}

	public int getCalorias() {
		return calorias;
	}

	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public String getIdUser() {
		return idUser;
	}
	public String getIdCheckin() {
		return this.idCheckin;
	}
	public void addComentario(Comentario comentario) {
		comentarios.add(comentario);
	}
	
	public HashSet<String> getCurtidas() {
		return curtidas;
	}

	public void curtir(String idUser) {
		if(curtidas.contains(idUser))throw new ShakeItOffException("Checkin já curtido pelo usuário");
		this.curtidas.add(idUser);
	}
	
}