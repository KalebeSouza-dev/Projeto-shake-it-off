package controller;

import static utils.Validador.*;

import java.time.LocalDateTime;

import exception.ShakeItOffException;
import model.CheckIn;
import model.Desafio;
import model.Usuario;

public class ShakeItOffFacade{
	private UsuarioController UC;
	private DesafioController DC;
	
	public ShakeItOffFacade() {
		UC = new UsuarioController();
		DC = new DesafioController();
	}
	
	// usuario
	public String registrarUsuario(String nome, String email, String bio, String senha) {
		return UC.registrarUsuario(nome, email, bio, senha);
	}
	public String editarPerfil(String idUsuario, String senha, String novaSenha, String novoNome, String novoEmail, String novabio) {
		return UC.editarPerfil(idUsuario, senha, novaSenha, novoNome, novoEmail, novabio);
	}
	public String listarUsuarios(String idUsuario, String senha) {
		return UC.listarUsuarios(idUsuario, senha);
	}
	
	//desafio
		public String criarDesafio(String idUsuario, String senha, String titulo, String descricao, String inicio, String fim, int metaDiariaMin, String estrategiaPontuacao) {
		return DC.criarDesafio(UC, idUsuario, senha, titulo, descricao, inicio, fim, metaDiariaMin, estrategiaPontuacao);
	}
	public String ingressarNoDesafio(String idUser,String senha, String idDesafio) {
		return DC.ingressarNoDesafio(UC, idUser, senha, idDesafio);
	}
	public String listarDesafios(String idUser, String senha) {
		return DC.listarDesafios(UC, idUser, senha);
	} 
	public String listarDesafiosAtivos(String idUser, String senha) {
		return DC.listarDesafiosAtivos(UC, idUser, senha);
	}
	
	//checkin
	public String registrarCheckin(String idUsuario, String senha, String idDesafio, String dataHora, String atividade, int duracaoMin, String intensidade, int calorias, int distancia) {
		return DC.addCheckin(UC,idUsuario, senha, idDesafio, dataHora, atividade, duracaoMin, intensidade, calorias, distancia);
	}
	
	public String listarCheckinDesafio(String idUsuario, String senha, String idDesafio) {
		return DC.listarCheckins(UC, idUsuario, senha, idDesafio);
	}
	
	public String listarCheckinUsuario(String idUsuario, String senha) {
		return DC.listarCheckinsUsuario(UC, idUsuario, senha);
	}
	
	//Ranking e Relatórios
	public String finalizarDesafio(String idUsuario, String senha, String idDesafio) {
		return DC.finalizarDesafio(UC, idUsuario, senha, idDesafio);
	}
	public String calcularRankingDesafio(String idUsuario, String senha, String idDesafio) {
		return DC.calcularRankingDesafio(UC, idUsuario, senha, idDesafio);
	}
	public String verRankingDesafio(String idUser, String senha, String idDesafio,int topN) {
		return DC.verRankingDesafio(UC, idUser, senha, idDesafio, topN);
	}
	public String gerarRelatorio(String idUser, String senha,String inicio, String fim) {
		return DC.gerarRelatorio(UC, idUser, senha, inicio, fim);
	}
	
	//Interações Sociais
	public void curtirCheckin(String idUsuario, String senha, String idDesafio, String idCheckin) {
		DC.curtirCheckin(UC, idUsuario, senha, idDesafio, idCheckin);
	}
	public String comentarCheckin(String idUsuario, String senha, String idDesafio, String idCheckin, String comentario) {
		return DC.comentarCheckin(UC, idUsuario, senha, idDesafio, idCheckin, comentario);
	}
}
