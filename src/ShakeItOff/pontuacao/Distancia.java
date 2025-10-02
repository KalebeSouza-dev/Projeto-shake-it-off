package ShakeItOff.pontuacao;

import java.util.List;

import model.CheckIn;

public class Distancia implements Pontuacao {

	@Override
	public int calculaPontuacao(List<CheckIn> checkins) {
		int out = 0;
		for (CheckIn c: checkins) {
			out += c.getDistancia();
		}
			return out;
	}

}
