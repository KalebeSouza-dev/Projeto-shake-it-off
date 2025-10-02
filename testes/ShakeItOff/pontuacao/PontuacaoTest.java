package ShakeItOff.pontuacao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import controller.ShakeItOffFacade;

class PontuacaoTest {

	@Nested
	class testsUS12US13 {
		private ShakeItOffFacade sys;
		@BeforeEach
		void setUp() {
			sys = new ShakeItOffFacade();
			sys.registrarUsuario("Renan", "Renan@", "lalala", "senna12");
			sys.registrarUsuario("Kalebe", "Kalebe@", "lalala", "123456");
			sys.registrarUsuario("Railton", "Railton@", "Railtinhoeu", "123456");
			sys.criarDesafio("USR1", "senna12", "Copa Pistao", "vrummmm", "03-02-2000 00:00:00", "03-02-2100 00:00:00", 5, "TEMPO");
			sys.criarDesafio("USR1", "senna12", "Copa Cirrose", "test", "03-02-2000 00:00:00", "03-02-2100 00:00:00", 5, "FREQUENCIA");
			sys.criarDesafio("USR1", "senna12", "Copa do Mundo", "test", "03-02-2000 00:00:00", "03-02-2100 00:00:00", 5, "DISTANCIA");
		}
		@Test
		void visualizarRanking() {
			LocalDateTime agora = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        String dataHoraStr = agora.format(formatter);
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr, "Ciclismo", 10, "ALTA", 100, 10);
			String expected = "Copa Pistao\n" + "#1 USR1 Renan - 12 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF1", 1));
			
		}
		@Test
		void visualizarRankingDoisUsuarios() {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora.format(formatter);
	        String dataHoraStr2 = agora.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 10, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR2", "123456", "DSF1");
			sys.registrarCheckin("USR2", "123456", "DSF1", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);
			
			String expected = "Copa Pistao\n" + "#1 USR1 Renan - 12 pts\n"
					+ "#2 USR2 Kalebe - 12 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF1", 2));	
		}
		@Test
		void visualizarRankingTresUsuariosTempo() {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR2", "123456", "DSF1");
			sys.registrarCheckin("USR2", "123456", "DSF1", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR3", "123456", "DSF1");
			sys.registrarCheckin("USR3", "123456", "DSF1", dataHoraStr2, "Ciclismo", 6, "ALTA", 100, 10);
					
			String expected = "Copa Pistao\n" + "#1 USR2 Kalebe - 12 pts\n"
					+ "#2 USR3 Railton - 7 pts\n"
					+ "#3 USR1 Renan - 6 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF1", 3));	
		}
		
		@Test
		void visualizarRankingTresUsuariosTempo2() {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 10, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR2", "123456", "DSF1");
			sys.registrarCheckin("USR2", "123456", "DSF1", dataHoraStr2, "Ciclismo", 10, "MODERADA", 100, 10);
			
			sys.ingressarNoDesafio("USR3", "123456", "DSF1");
			sys.registrarCheckin("USR3", "123456", "DSF1", dataHoraStr2, "Ciclismo", 10, "LEVE", 100, 10);
					
			String expected = "Copa Pistao\n" + "#1 USR1 Renan - 12 pts\n"
					+ "#2 USR2 Kalebe - 11 pts\n"
					+ "#3 USR3 Railton - 10 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF1", 3));

		}
		@Test
		void visualizarRankingTresUsuariosTempoValoresIguais() {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(10);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 3, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 3, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR2", "123456", "DSF1");
			sys.registrarCheckin("USR2", "123456", "DSF1", dataHoraStr1, "Ciclismo", 10, "ALTA", 100, 10);
			
			sys.ingressarNoDesafio("USR3", "123456", "DSF1");
			sys.registrarCheckin("USR3", "123456", "DSF1", dataHoraStr2, "Ciclismo", 3, "ALTA", 100, 10);
			sys.registrarCheckin("USR3", "123456", "DSF1", dataHoraStr2, "Ciclismo", 3, "ALTA", 100, 10);

					
			String expected = "Copa Pistao\n" + "#1 USR2 Kalebe - 12 pts\n"
					+ "#2 USR1 Renan - 6 pts\n"
					+ "#3 USR3 Railton - 6 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF1", 3));	
		}
		@Test
		void visualizarRankingTresUsuariosFrequencia() {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR2", "123456", "DSF2");
			sys.registrarCheckin("USR2", "123456", "DSF2", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);
			sys.registrarCheckin("USR2", "123456", "DSF2", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR3", "123456", "DSF2");
			sys.registrarCheckin("USR3", "123456", "DSF2", dataHoraStr2, "Ciclismo", 6, "ALTA", 100, 10);
					
			String expected = "Copa Cirrose\n" + "#1 USR1 Renan - 3 pts\n"
					+ "#2 USR2 Kalebe - 2 pts\n"
					+ "#3 USR3 Railton - 1 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF2", 3));	
		}
		
		@Test
		void visualizarRankingTresUsuariosFrequenciaQtdCheckinsIguais() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
			LocalDateTime agora3 = LocalDateTime.now().plusSeconds(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR2", "123456", "DSF2");
			sys.registrarCheckin("USR2", "123456", "DSF2", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR3", "123456", "DSF2");
			sys.registrarCheckin("USR3", "123456", "DSF2", dataHoraStr1, "Ciclismo", 6, "ALTA", 100, 10);
					
			String expected = "Copa Cirrose\n" + "#1 USR3 Railton - 1 pts\n"
					+ "#2 USR2 Kalebe - 1 pts\n"
					+ "#3 USR1 Renan - 1 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF2", 3));	
		}
		@Test
		void visualizarRankingTresUsuariosDistancia() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
			LocalDateTime agora3 = LocalDateTime.now().plusSeconds(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF3");
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 30);

			sys.ingressarNoDesafio("USR2", "123456", "DSF3");
			sys.registrarCheckin("USR2", "123456", "DSF3", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 20);

			sys.ingressarNoDesafio("USR3", "123456", "DSF3");
			sys.registrarCheckin("USR3", "123456", "DSF3", dataHoraStr3, "Ciclismo", 6, "ALTA", 100, 10);
					
			String expected = "Copa do Mundo\n" + "#1 USR1 Renan - 30 pts\n"
					+ "#2 USR2 Kalebe - 20 pts\n"
					+ "#3 USR3 Railton - 10 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF3", 3));	
		}
		@Test
		void visualizarRankingTresUsuariosDistanciasIguais() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
			LocalDateTime agora3 = LocalDateTime.now().plusSeconds(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF3");
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR2", "123456", "DSF3");
			sys.registrarCheckin("USR2", "123456", "DSF3", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 15);
			sys.registrarCheckin("USR2", "123456", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 15);


			sys.ingressarNoDesafio("USR3", "123456", "DSF3");
			sys.registrarCheckin("USR3", "123456", "DSF3", dataHoraStr3, "Ciclismo", 6, "ALTA", 100, 30);
					
			String expected = "Copa do Mundo\n" + "#1 USR1 Renan - 30 pts\n"
					+ "#2 USR2 Kalebe - 30 pts\n"
					+ "#3 USR3 Railton - 30 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF3", 3));	
		}
		@Test
		void visualizarRankingTresUsuariosDistanciasEQtdsCeckinsIguais() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusSeconds(1);
			LocalDateTime agora3 = LocalDateTime.now().plusSeconds(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF3");
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR2", "123456", "DSF3");
			sys.registrarCheckin("USR2", "123456", "DSF3", dataHoraStr2, "Ciclismo", 10, "ALTA", 100, 10);

			sys.ingressarNoDesafio("USR3", "123456", "DSF3");
			sys.registrarCheckin("USR3", "123456", "DSF3", dataHoraStr1, "Ciclismo", 6, "ALTA", 100, 10);
					
			String expected = "Copa do Mundo\n" + "#1 USR3 Railton - 10 pts\n"
					+ "#2 USR2 Kalebe - 10 pts\n"
					+ "#3 USR1 Renan - 10 pts";
			assertEquals(expected, sys.verRankingDesafio("USR1", "senna12", "DSF3", 3));	
		}


		@Test
		void visualizarRankingUserNaoAutenticado() {
			try {
			sys.verRankingDesafio("USR1", "123457", "DSF1", 5);
			}catch(Exception e) {
				assertEquals("Usuário não autenticado.", e.getMessage());
			}
		}
		@Test
		void visualizarRankingIdUserNull() {
			try {
			sys.verRankingDesafio(null, "senna12", "DSF1", 5);
			}catch(Exception e) {
				assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void visualizarRankingIdUserVazio() {
			try {
			sys.verRankingDesafio("", "senna12", "DSF1", 5);
			}catch(Exception e) {
				assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		
		@Test
		void visualizarRankingSenhaNull() {
			try {
			sys.verRankingDesafio("USR1", null, "DSF1", 5);
			}catch(Exception e) {
				assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		
		@Test
		void visualizarRankingSenhaVazia() {
			try {
			sys.verRankingDesafio("USR1", "", "DSF1", 5);
			}catch(Exception e) {
				assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		
		@Test
		void visualizarRankingDesafioInválido() {
			try {
			sys.verRankingDesafio("USR1", "senna12", "DSF10", 5);
			}catch(Exception e) {
				assertEquals("Desafio não existe", e.getMessage());
			}
		}
		
		@Test
		void visualizarRankingDesafioNulo() {
			try {
			sys.verRankingDesafio("USR1", "senna12", null, 5);
			}catch(Exception e) {
				assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		
		@Test
		void visualizarRankingDesafioVazio() {
			try {
			sys.verRankingDesafio("USR1", "senna12", "", 5);
			}catch(Exception e) {
				assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
			}
		}


	}
}