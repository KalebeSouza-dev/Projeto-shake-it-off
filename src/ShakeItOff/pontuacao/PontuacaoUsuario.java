package ShakeItOff.pontuacao;

import java.time.LocalDateTime;

import model.Usuario;

public class PontuacaoUsuario implements Comparable<PontuacaoUsuario>{
	private Usuario user;
	private int pontuacao;
	private int quantCheckIn;
	private LocalDateTime dataAntiga;
	
	public PontuacaoUsuario(Usuario user, int pontuacao, int quantCheckIn, LocalDateTime dataAntiga) {
		this.user = user;
		this.pontuacao = pontuacao;
		this.quantCheckIn = quantCheckIn;
		this.dataAntiga = dataAntiga;
	}

	@Override
	public int compareTo(PontuacaoUsuario arg0) {
		int criterioPontuacao = arg0.getPontuacao() - this.pontuacao;
		if (criterioPontuacao == 0) {
			int criterioQCheckIn = arg0.getQuantCheckIn() - this.quantCheckIn;
			if (criterioQCheckIn == 0) {
				//return arg0.getDataAntiga().compareTo(this.getDataAntiga());
				return dataAntiga.compareTo(arg0.getDataAntiga());
			}
			return criterioQCheckIn;
		} 
		return criterioPontuacao;
	}

	public Usuario getUser() {
		return user;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public int getQuantCheckIn() {
		return quantCheckIn;
	}

	public LocalDateTime getDataAntiga() {
		return dataAntiga;
	}
	
}
