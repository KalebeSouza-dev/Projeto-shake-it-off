package ShakeItOff.pontuacao;

import java.util.List;

import model.CheckIn;

public interface Pontuacao {
	int calculaPontuacao(List<CheckIn> checkins);
}