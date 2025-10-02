package ShakeItOff;

public class Comentario {
	private String idUsuario;
	private String nomeUser;
	private String comentario;
	
	public Comentario(String idUsuario, String nome, String comentario) {
		this.idUsuario = idUsuario;
		this.nomeUser = nome;
		this.comentario = comentario;
	}
	
	@Override
	public String toString() {
		return this.idUsuario + " " + this.nomeUser + ": " + this.comentario;
	}

}
