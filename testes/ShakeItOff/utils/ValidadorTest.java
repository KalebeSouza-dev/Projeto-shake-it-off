package ShakeItOff.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exception.ShakeItOffException;

import static utils.Validador.*;

import java.time.LocalDateTime;

class ValidadorTest {

	@Test
	void testNull() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validador(null,"idUser");
		});
		assertEquals("idUser não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void testVazio() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validador("","idUser");
		});
		assertEquals("idUser não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void testValidarNulo() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorNull(null,"idUser");
		});
		assertEquals("idUser não pode ser nula.",err.getMessage());
	}
	@Test
	void testValidarInt() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorInt(-10,"idUser");
		});
		assertEquals("idUser não pode ser negativo.",err.getMessage());
	}
	@Test
	void testValidarIntCampo() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorIntCampo(-1,"índice não pode ser negativo");
		});
		assertEquals("índice não pode ser negativo",err.getMessage());
	}
	@Test
	void testValidarIntCampo0() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorInt0Campo(0,"índice não pode ser 0");
		});
		assertEquals("índice não pode ser 0",err.getMessage());
	}
	@Test
	void testValidarData() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorData(null,"início");
		});
		assertEquals("início não pode ser nulo.",err.getMessage());
	}
	@Test
	void testValidarDataInicio() {
		LocalDateTime inicio = LocalDateTime.of(2027, 12,24,8,50);
		LocalDateTime fim = LocalDateTime.of(2026, 12,24,8,50);
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorDataInicio(inicio,fim);
		});
		assertEquals("inicio não pode ser depois do fim",err.getMessage());
	}
	@Test
	void testValidarDataInicioDiferencaPequena() {
		LocalDateTime inicio = LocalDateTime.of(2026, 12,24,8,51);
		LocalDateTime fim = LocalDateTime.of(2026, 12,24,8,50);
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorDataInicio(inicio,fim);
		});
		assertEquals("inicio não pode ser depois do fim",err.getMessage());
	}
	@Test
	void testValidarSenha() {
		
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorSenha("zelu","nova");
		});
		assertEquals("nova senha inválida",err.getMessage());
	}
	@Test
	void validarEstrategia() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorEstrategia("estrategia errada");
		});
		assertEquals("Estrategia de pontuacao invalida",err.getMessage());
	}
	@Test
	void validarIntensidade() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadaIntensidade("MUITO ALTA");
		});
		assertEquals("Intensidade diferente do esperado",err.getMessage());
	}
	@Test
	void validarPeriodo() {
		LocalDateTime inicio = LocalDateTime.of(2026, 12,24,8,51);
		LocalDateTime fim = LocalDateTime.of(2027, 12,24,8,50);
		ShakeItOffException err = assertThrows(ShakeItOffException.class,()-> {
			validadorPeriodo(inicio,fim);
		});
		assertEquals("Período muito grande",err.getMessage());
	}
}
