package utils;

import java.time.LocalDateTime;

import exception.ShakeItOffException;

public class Validador {
	public static void validador(String string, String campo) {
		if (string == null || string.trim().isEmpty()) {
			 throw new ShakeItOffException(campo + " não pode ser vazio ou nulo.");
		}
	}
	public static void validadorNull(String string, String campo) {
		if (string == null) {
			 throw new ShakeItOffException(campo + " não pode ser nula.");
		}
	}
	public static void validadorInt(int i, String campo) {
		if (i < 0) { 
			 throw new ShakeItOffException(campo + " não pode ser negativo.");
		}
	}
	public static void validadorIntCampo(int i, String campo) {
		if (i < 0) {
			 throw new ShakeItOffException(campo);
		}
	}

	public static void validadorInt0Campo(int i, String campo) {
		if (i <= 0) {
			 throw new ShakeItOffException(campo);
		}
	}
	public static void validadorData(LocalDateTime dt, String campo) {
		if (dt == null) {
			 throw new ShakeItOffException(campo + " não pode ser nulo.");
		}
	}
	public static void validadorDataInicio(LocalDateTime  inicio, LocalDateTime fim) {
		if(inicio.isAfter(fim)) {
		    throw new ShakeItOffException("inicio não pode ser depois do fim");
		}
		
	}
	public static void validadorSenha(String senha, String out) {
		if(senha.length() < 6 && !senha.trim().isEmpty()) {
			throw new ShakeItOffException(out + " senha inválida");
		} 
	}
	public static void validadorEstrategia(String estrategiaPontuacao) {
		if(!(estrategiaPontuacao.equals("FREQUENCIA") || estrategiaPontuacao.equals("TEMPO") || estrategiaPontuacao.equals("DISTANCIA"))) {
    		throw new ShakeItOffException("Estrategia de pontuacao invalida");
    	}
	}
	public static void validadorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		if((fim.toLocalDate().toEpochDay() - inicio.toLocalDate().toEpochDay()) > 90) {
			throw new ShakeItOffException("Período muito grande");
		}
	}
	public static void validadaIntensidade(String intensidade) {
		if(!(intensidade.trim().equals("")) && !(intensidade.equals("LEVE") || 
			intensidade.equals("MODERADA") || intensidade.equals("ALTA"))){
			throw new ShakeItOffException("Intensidade diferente do esperado");
		}
	}
}
