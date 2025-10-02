package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Usuario implements Comparable<Usuario>{
	private String nome;
	private String email;
	private String bio;
	private String senha;
	private String ID;
	// retornar o ID 
	

	public Usuario(String nome, String email, String bio, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.bio = bio;
	}
	@Override
	public String toString() {
		return "["+ID+"] " + nome + " ("+email+") " +  bio + ".";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}
	
	public void editarDados(String novaSenha, String novoNome, String novoEmail, String novabio) {
		if(!novoNome.isEmpty()) this.nome = novoNome;
		if(!novoEmail.isEmpty()) this.email = novoEmail;
		if(!novabio.isEmpty()) this.bio = novabio;
		if(!novaSenha.isEmpty()) this.senha = novaSenha;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public String getID() {
		return ID;
	}
	public void setID(int n) {
		this.ID = "USR" + n;
	}
	@Override
	public int compareTo(Usuario o) {
		return this.nome.compareTo(o.getNome());
	}
	
}
