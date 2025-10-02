package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ShakeItOff.pontuacao.Distancia;
import ShakeItOff.pontuacao.Frequencia;
import ShakeItOff.pontuacao.Pontuacao;
import ShakeItOff.pontuacao.PontuacaoUsuario;
import ShakeItOff.pontuacao.Tempo;
import exception.ShakeItOffException;

public class Desafio implements Comparable<Desafio>{
	private List<Usuario> usuarios;
    private Map<String,CheckIn> checkins;
    private String idUsuario;
    private String nomeCriador;
    private String titulo;
    private Pontuacao estrategiaDePontuacao;
    private String tipoEstrategia;
    private String ID;
    private String descricao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
	private int metaDiariaMin;
	private boolean status;
    private List<PontuacaoUsuario> ranking;
    
    public Desafio(String idUsuario, String nomeCriador,String titulo, String descricao, LocalDateTime inicio, LocalDateTime fim, int metaDiariaMin, String estrategiaPontuacao) {
    	this.idUsuario = idUsuario;
    	this.nomeCriador = nomeCriador;
    	this.titulo = titulo;
    	this.descricao = descricao;
    	this.usuarios = new ArrayList<>();
    	this.checkins = new HashMap<>();
    	this.ranking = new ArrayList<>();
    	this.inicio = inicio;
    	this.fim = fim;
    	this.metaDiariaMin = metaDiariaMin;
    	this.tipoEstrategia = estrategiaPontuacao;
    	
    	switch (estrategiaPontuacao) {
		case "TEMPO": estrategiaDePontuacao = new Tempo(); break;
		case "FREQUENCIA": estrategiaDePontuacao = new Frequencia(); break;
		case "DISTANCIA": estrategiaDePontuacao = new Distancia();	break;
	}

    	
    }
    public void setRanking(List<PontuacaoUsuario> ranking) {
		this.ranking = ranking;
	} 
    public String toString() {
    	DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	
    	String l1 = String.format("[%s] %s (%s a %s), criado por: %s\n", this.ID,this.titulo,this.inicio.format(formater),this.fim.format(formater),this.nomeCriador);
    	String l2 = String.format("\nMeta diária minima: %d; Pontuação por: %s", this.metaDiariaMin,this.tipoEstrategia);
        return l1 + this.descricao + l2;
    }
    
    public boolean usuarioInDesafio(String idUser) {
        for(Usuario user: this.usuarios) {
        	if(user.getID().equals(idUser)) {
        		return true;
        	}
        }
        return false;
    }
    
    public String addUsuario(Usuario usuario) {
       LocalDateTime horaAtual = LocalDateTime.now();
       if(this.usuarioInDesafio(usuario.getID())){
    	   return "Não foi possível entrar no desafio.";
       }
       
       this.usuarios.add(usuario);
       return "Você está participando!";
    }
    
    private String getNomeUser(String idUser) {
		for(Usuario user: this.usuarios) {
	     	if(user.getID().equals(idUser)) {
	     		return user.getNome();
	        }
	    }
        return "User não está no desafio";
    }
    
    public void addCheckin(CheckIn novo) {
        checkins.put(novo.getIdCheckin(), novo);
    }
     
    public String exibirCheckin(String idCheckin) {
    	CheckIn checkin = this.pegaCheckin(idCheckin);
    	return checkin.exibirCheckin(this.getTitulo());
    }
    
    public CheckIn pegaCheckin(String idCheckin) {
    	if(!checkins.containsKey(idCheckin)) return null;
    	
    	return checkins.get(idCheckin);
    }
    
    public boolean existeCheckin(String idCheckin) {
    	if(checkins.containsKey(idCheckin)) return true;
    	throw new ShakeItOffException("Checkin não existe");
    }
    
    public void calculaRanking() {
    	ArrayList<PontuacaoUsuario> rankingGeral = new ArrayList<>();
    	List<Usuario> users = this.usuarios;

    	for (Usuario u: users) {
    		List<CheckIn> out = achaCheckInUser(u);
    		LocalDateTime antiga = pegaDataAntiga(out);
    		int pont = this.estrategiaDePontuacao.calculaPontuacao(out);
    		PontuacaoUsuario r = new PontuacaoUsuario(u, pont, out.size(), antiga);
    		rankingGeral.add(r);
    	}
    	
    	Collections.sort(rankingGeral);
    	this.setRanking(rankingGeral);
    }
    private LocalDateTime pegaDataAntiga(List<CheckIn> out) {
    	LocalDateTime antiga = out.get(0).getDataHora();
    	for (CheckIn c: out) {
			if (c.getDataHora().isBefore(antiga)){
				antiga = c.getDataHora();
			}
		}
    	return antiga;
    }
    private List<CheckIn> achaCheckInUser(Usuario user) {
    	List<CheckIn> ci = this.getCheckins();
    	ArrayList<CheckIn> out = new ArrayList<>();
		for (CheckIn c: ci) {
			if (c.getIdUser().equals(user.getID())){
					out.add(c);
			}
		}
    	return out;
    	
    }
    public int extrairId() {
        String numbers = this.ID.replaceAll("[^0-9]", "");
        return Integer.parseInt(numbers);
    }
    public int getLenCheckins() {
    	return checkins.size() + 1;
    }
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public int getMetaDiariaMin() {
		return metaDiariaMin;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public ArrayList<CheckIn> getCheckins() {
		ArrayList<CheckIn> aux = new ArrayList<>();
		for(CheckIn checkin: checkins.values()) {
			aux.add(checkin);
		}
		return aux;
	}
	public String getIdUsuario() {
		return idUsuario;
	}

	public String getID() {
		return ID;
	}
	public void setID(String id) {
		this.ID = id;
	}
	public LocalDateTime getInicio() {
		return inicio;
	}
	public LocalDateTime getFim() {
		return fim;
	}
	public String getTitulo() {
		return this.titulo;
	}
	public List<PontuacaoUsuario> getRanking(){
		return this.ranking;
	}
	public boolean verificarStatus() {
	
		LocalDateTime horaAtual = LocalDateTime.now();
		if (horaAtual.isAfter(this.inicio) && horaAtual.isBefore(this.fim)) {
			this.status = true;
		} else {
			this.status = false;
		}
		return this.status;
	}
	@Override
	public int compareTo(Desafio dsf) {
		return extrairId() - dsf.extrairId();
	}
	
}