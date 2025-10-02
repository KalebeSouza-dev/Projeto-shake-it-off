package ShakeItOff.pontuacao;

import java.util.List;

import model.CheckIn;

public class Frequencia implements Pontuacao{

	@Override
	public int calculaPontuacao(List<CheckIn> checkins) {
		return checkins.size();
	}

}
