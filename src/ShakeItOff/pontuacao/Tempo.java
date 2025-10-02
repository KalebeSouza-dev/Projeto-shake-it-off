package ShakeItOff.pontuacao;

import java.util.List;

import model.CheckIn;

public class Tempo implements Pontuacao {
	
	@Override
	public int calculaPontuacao(List<CheckIn> checkins) {
		int out = 0;
		for (CheckIn c: checkins) {
			String intensidade = c.getIntensidade();
			switch (intensidade) {
				case "ALTA": out += c.getDuracaoMin() * 1.20; break;
				case "MODERADA": out += c.getDuracaoMin() * 1.10; break;
				case "LEVE": out += c.getDuracaoMin(); break;
			}
			
		}
			return out;
	}
	
}
