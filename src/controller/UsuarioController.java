package controller;

import static utils.Validador.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.ShakeItOffException;
import model.CheckIn;
import model.Usuario;

public class UsuarioController {
	private Map<String, Usuario> usuarios;
	
	public UsuarioController() {
		usuarios = new HashMap<>();
	}
	// US1
	public String registrarUsuario(String nome, String email, String bio, String senha) {
		validador(nome, "Nome");
		validador(email, "Email");
		validador(bio, "Biografia");
		validador(senha, "Senha");
		
		if(senha.length() < 6) throw new ShakeItOffException("Senha inválida (menos de 6 caracteres)");
		if(existeEmail(email)) return "Email já cadastrado.";
	
		Usuario user = new Usuario(nome, email, bio, senha);
		String id = "USR" + (usuarios.size() + 1);
		usuarios.put(id, user);
		user.setID(usuarios.size()); 
		
		return id;	 
	}
	// US2
	public String editarPerfil(String idUsuario, String senha, String novaSenha, String novoNome, String novoEmail, String novabio) {
		autenticacao(idUsuario, senha);
		validadorSenha(novaSenha, "Nova");
		if(existeEmail(novoEmail)) return "Não foi possível atualizar o perfil: email já cadastrado.";
		
		this.usuarios.get(idUsuario).editarDados(novaSenha, novoNome, novoEmail, novabio);
		
		return "Perfil atualizado";
	}
	// US3
	public String listarUsuarios(String idUsuario, String senha) {
		autenticacao(idUsuario,senha);
		List<Usuario> listaOrdenada = new ArrayList<>(this.usuarios.values());
		ordenarUsuarios(listaOrdenada);
		
		String out = "";
		for (Usuario u: listaOrdenada) {
			if(out.equals("")) {
				out += u.toString();
				continue; 
			}
			out += "\n" + u.toString();
		}
		
		return out;
	}
	
	public boolean autenticacao(String idUsuario, String senha) {
		validador(idUsuario, "idUsuario");
		validador(senha,"senha");
		existeUsuario(idUsuario);
		if (!this.usuarios.get(idUsuario).getSenha().equals(senha)) throw new ShakeItOffException("Usuário não autenticado.");
		
		return true;
	}
	
	private static void ordenarUsuarios(List<Usuario> list) {
		Collections.sort(list);
	}
	private boolean existeEmail(String email) {
		for (Usuario u: usuarios.values()) {
			if (u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	public boolean existeUsuario(String idUsuario) {
		if (!this.usuarios.containsKey(idUsuario)) throw new ShakeItOffException("Usuário não existe");
		return true;
	}

	public Usuario getUsuario(String idUser) {
		return this.usuarios.get(idUser);
	}
	
}