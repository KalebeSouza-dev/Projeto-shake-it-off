package controller;

import static utils.Validador.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.ShakeItOffException;
import model.CheckIn;
import model.Desafio;
import model.Usuario;
import ShakeItOff.Comentario;
import ShakeItOff.pontuacao.PontuacaoUsuario;


public class DesafioController {
	private Map<String, Desafio> desafios;
	
	public DesafioController() {
		this.desafios = new HashMap<>();
	}
	// US4
    public String criarDesafio(UsuarioController UC,String idUsuario, String senha,String titulo, String descricao, String inicio, String fim, int metaDiariaMin, String estrategiaPontuacao) {
		UC.autenticacao(idUsuario, senha);
    	
    	validador(titulo, "titulo");
		validador(descricao, "descricao");
		validador(inicio, "inicio");
		validador(fim, "fim");
		validadorInt(metaDiariaMin, "metaDiariaMin");
		validador(estrategiaPontuacao, "estrategiaPontuacao");
		validadorEstrategia(estrategiaPontuacao);
		LocalDateTime inicioDt = this.converteStringData(inicio);
		LocalDateTime fimDt = this.converteStringData(fim);
		validadorDataInicio(inicioDt,fimDt);
		
    	Desafio desafioAtual = new Desafio(idUsuario, UC.getUsuario(idUsuario).getNome(),titulo,descricao,inicioDt,fimDt,metaDiariaMin,estrategiaPontuacao);
        desafioAtual.verificarStatus();
        String idDesafio = String.format("DSF%d",this.desafios.size() + 1);
        desafioAtual.setID(idDesafio);
        this.desafios.put(idDesafio, desafioAtual);
        
        return idDesafio;
    }
    // US5
    public String ingressarNoDesafio(UsuarioController UC, String user, String senha, String idDesafio) {
        UC.autenticacao(user, senha);
        existeDesafio(idDesafio); 

        Usuario usuario = UC.getUsuario(user);
        if(!this.existeDesafio(idDesafio)) {
            throw new ShakeItOffException("Id do desafio inválido");
        }
        Desafio desafioAtual = this.getDesafio(idDesafio);
        LocalDateTime horaAtual = LocalDateTime.now();
        if(!(horaAtual.isAfter(desafioAtual.getInicio()) && horaAtual.isBefore(desafioAtual.getFim()))) {
            return "Não foi possível entrar no desafio";
        }

        String res = desafioAtual.addUsuario(usuario);
        return res;
    }
    public Desafio getDesafio(String idDesafio) {
  		return this.desafios.get(idDesafio);
  	}
      
	public boolean existeDesafio(String idDesafio) {
	    validador(idDesafio, "idDesafio");
	    if (!this.desafios.containsKey(idDesafio)) throw new ShakeItOffException("Desafio não existe");
		return true;
	}
   // US6
    public String listarDesafios(UsuarioController UC, String idUsuario,String senha) {
    	UC.autenticacao(idUsuario, senha);
    	validadorInt0Campo(this.desafios.size(),"Nenhum desafio válido");
    	
    	List<Desafio> dsf = new ArrayList<>(desafios.values());
    	Collections.sort(dsf);
    	StringBuffer out = new StringBuffer();
    	
    	for(Desafio desafio: dsf) {
    		out.append(desafio.toString() + "\n");
    	}
    	return out.toString();
    }
    public String listarDesafiosAtivos(UsuarioController UC, String idUser, String senha) {
    	UC.autenticacao(idUser, senha);
    	List<Desafio> dsf = new ArrayList<>(desafios.values());
    	Collections.sort(dsf);

    	StringBuffer out = new StringBuffer();
    	for(Desafio desafio: dsf) {
    		if(desafio.verificarStatus()) {
    			out.append(desafio.toString() + "\n");
    		}
    	}
    	validadorInt0Campo(out.toString().length(),"Nenhum desafio válido");
    	
        return out.toString();
    }
    public List<Desafio> getDesafios(){
    	return new ArrayList<>(this.desafios.values());
    }
    // US10
    public String finalizarDesafio(UsuarioController UC, String idUsuario, String senha, String idDesafio) {
    	UC.autenticacao(idUsuario, senha);
    	this.existeDesafio(idDesafio);
    	Desafio desafio = getDesafio(idDesafio);
		if (!desafio.getIdUsuario().equals(idUsuario)) {
			return "Apenas o criador pode finalizar o desafio.";
		} else if (!desafio.verificarStatus()) {
			return "Não é possível finalizar o desafio nesse momento.";
		}
		 
		desafio.setStatus(false);
		return "Desafio finalizado.";
    }
    // US11
    public String  calcularRankingDesafio(UsuarioController UC,String idUsuario, String senha, String idDesafio) {
    	UC.autenticacao(idUsuario, senha);
    	if(!this.existeDesafio(idDesafio)) throw new ShakeItOffException("Id do desafio inválido");
    	Usuario user = UC.getUsuario(idUsuario);
		Desafio desafio = this.getDesafio(idDesafio);

		if (!desafio.getUsuarios().contains(user)) {
			return "Deve participar do desafio para calcular ranking.";
		}
		
    	desafio.calculaRanking();
    	
    	return "Ranking calculado.";
    }
    // US12
    public String verRankingDesafio(UsuarioController UC, String idUser, String senha, String idDesafio,int topN) {
    	UC.autenticacao(idUser, senha);
    	this.existeDesafio(idDesafio);
    	Desafio desafioTemporario = getDesafio(idDesafio);
		if(desafioTemporario.getRanking().size() == 0 || desafioTemporario.verificarStatus()) {
			desafioTemporario.calculaRanking();
		}
		
		String out = desafioTemporario.getTitulo();
		List<PontuacaoUsuario> ranking = desafioTemporario.getRanking();
		
		for(int i = 0; i < Math.min(topN,ranking.size()); i++) {
			PontuacaoUsuario pont = ranking.get(i);
			Usuario user = pont.getUser();

			String colocacaoUser = String.format("#%d %s %s - %d pts",i + 1,user.getID(),
					user.getNome(),pont.getPontuacao());
			out += "\n" + colocacaoUser;
		}
		
		return out;
	}
    // US13
    public String gerarRelatorio(UsuarioController UC,String idUser, String senha,String inicio, String fim) {
		UC.autenticacao(idUser, senha);
		validador(inicio, "inicio");
		validador(fim, "fim");
		
		LocalDateTime inicioDt = this.converteStringData(inicio);
		LocalDateTime fimDt = this.converteStringData(fim);
		
		validadorDataInicio(inicioDt, fimDt);
		validadorPeriodo(inicioDt, fimDt);
		
		List<Desafio> desafiosSortados = this.ordenaDesafiosPorNome();
		String out2 = "Atividades de " + UC.getUsuario(idUser).getNome();
		String out = "";
		for(Desafio desafio : desafiosSortados) {
			if(!desafio.usuarioInDesafio(idUser)) continue;

			List<CheckIn> checkins = this.ordenaCheckins(desafio.getCheckins());
			for(CheckIn check : checkins) {
				if(check.getIdUser().equals(idUser) && check.getDataHora().isAfter(inicioDt)&&
						check.getDataHora().isBefore(fimDt)) {
					out += "\n" + desafio.exibirCheckin(check.getIdCheckin());
				}
			}
		}
		
		if(!out.equals("")) {
			out = out2 + "\n" + out;
		}
		return out;
	}
    // US7
    public String addCheckin(UsuarioController UC,String idUsuario,String senha,String idDesafio, String dataHora, String atividade, int duracaoMin, String intensidade, int calorias, int distancia) {
    	existeDesafio(idDesafio);
    	UC.autenticacao(idUsuario, senha);
    	validador(idDesafio, "idDesafio");
		validador(dataHora, "dataHora");
		validador(atividade, "atividade");
		validadorNull(intensidade, "intensidade");
		validadorIntCampo(calorias, "Quantidade de calorias inválida.");
		validadorIntCampo(distancia, "Distancia inválida");
		validadorInt0Campo(duracaoMin, "Duração inválida");
		validadaIntensidade(intensidade);
		
		Usuario usuario = UC.getUsuario(idUsuario);
		existeDesafio(idDesafio);
		Desafio desafio = getDesafio(idDesafio);
		
		if (!desafio.verificarStatus()) throw new ShakeItOffException("O desafio deve estar ativo para adicionar checkins.");
		if(!desafio.usuarioInDesafio(idUsuario)) return "Usuário não está cadastrado no desafio.";
		
		LocalDateTime dt = this.converteStringData(dataHora);
		LocalDate aux = LocalDate.now();
		
		if(!dt.toLocalDate().equals(aux)) throw new ShakeItOffException("O checkin deve ser do dia de hoje.");
		
		
		CheckIn novo = new CheckIn(idUsuario, usuario.getNome(), dt,  atividade, duracaoMin, intensidade, calorias, distancia);
		String id = "CHK" + desafio.getLenCheckins();
		novo.setId(id);
		desafio.addCheckin(novo);

		return id;
    }
    // US8
    public String listarCheckins(UsuarioController UC, String idUsuario,String senha, String idDesafio) {
    	UC.autenticacao(idUsuario, senha);
    	validador(idDesafio, "idDesafio");
		existeDesafio(idDesafio);
		
		Desafio desafio = getDesafio(idDesafio);
		ArrayList<CheckIn> aux = desafio.getCheckins();
		ordenarPorDataHora(aux);
		
		String out = "";
		for(CheckIn checkin: aux) {
			out += checkin.toString();
			out += "\n";
		}
		if(out.length() == 0) {
			return "Ainda não houve registro de check-in.";
		}
		return out;
    }
    // US9
    public String listarCheckinsUsuario(UsuarioController UC, String idUser,String senha) {
    	UC.autenticacao(idUser, senha);
    	List<Map.Entry<CheckIn, String>> aux = new ArrayList<>();
    	this.userInDesafios(idUser);
        for (Desafio desafio : desafios.values()) {
            for (CheckIn checkin : desafio.getCheckins()) {
                if (checkin.getIdUser().equals(idUser)) {
                    aux.add(Map.entry(checkin, desafio.getTitulo()));
                }
            }
        }
    	
    	aux.sort((a, b) -> a.getKey().compareTo(b.getKey()));
		
    	String out = "";
        for (Map.Entry<CheckIn, String> item : aux) {
            out += item.getKey().toString() + 
            "\n - Desafio: " + item.getValue() + "\n";
        }

        if (out.isEmpty()) {
            return "Ainda não houve registro de check-in.";
        }
        return out;
    }
    // US14
    public void curtirCheckin(UsuarioController UC, String idUsuario, String senha, String idDesafio, String idCheckin) {
		UC.autenticacao(idUsuario, senha);
		validador(idUsuario, "idUsuário");
		validador(senha, "senha");
		validador(idDesafio, "idDesafio");
		validador(idCheckin, "idCheckin");

		this.existeDesafio(idDesafio);

		Desafio desafio = this.getDesafio(idDesafio);
		
		desafio.existeCheckin(idCheckin);
		if(!desafio.usuarioInDesafio(idUsuario)) throw new ShakeItOffException("Deve participar do desafio para curtir check in dele.");
		
		CheckIn checkin = desafio.pegaCheckin(idCheckin);
		checkin.curtir(idUsuario);
	}
    // US15
	public String comentarCheckin(UsuarioController UC,String idUsuario, String senha, String idDesafio, String idCheckin, String comentario) {
		UC.autenticacao(idUsuario, senha);
		existeDesafio(idDesafio);
		validador(idDesafio, "idDesafio");
		validador(idCheckin, "idCheckin");
		
		if(comentario == null || comentario.trim().equals("")) return "Comentário não pode ser vazio.";

		this.existeDesafio(idDesafio);

		Desafio desafio = this.getDesafio(idDesafio);
		Usuario user = UC.getUsuario(idUsuario);
		
		if(!desafio.existeCheckin(idCheckin)) throw new ShakeItOffException("Checkin não existe");		
		if(!desafio.usuarioInDesafio(idUsuario)) return "Deve participar do desafio para curtir check in dele.";
		
		CheckIn checkin = desafio.pegaCheckin(idCheckin);
		Comentario novo = new Comentario(idUsuario, user.getNome(), comentario);
		checkin.addComentario(novo);
		
		return "";
	}
	
    public LocalDateTime converteStringData(String dataHora) {
    	DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    LocalDateTime dt = LocalDateTime.parse(dataHora, f);
	    return dt;
	    
    }
    private List<Desafio> ordenaDesafiosPorNome(){
    	Comparator<Desafio> ordenaDesafio = (d1,d2) -> d1.getTitulo().compareTo(d2.getTitulo());
    	List<Desafio> desafioCopy = this.getDesafios();
    	Collections.sort(desafioCopy,ordenaDesafio);
    	return desafioCopy;
    }
    private List<CheckIn> ordenaCheckins(List<CheckIn> checks){
    	List<CheckIn> checkins = new ArrayList<CheckIn>();
    	checkins.addAll(checks);
    	ordenarPorDataHora(checkins);
    	return checkins;
    }
    private static void ordenarPorDataHora(List<CheckIn> list) {
        Collections.sort(list);
    }
	private boolean userInDesafios(String idUser) {
		for(Desafio desafio:this.desafios.values()) {
			if (desafio.usuarioInDesafio(idUser)){
				return true;
			}
		}
		throw new ShakeItOffException("Usuário não participa de nenhum desafio.");
	}
}


