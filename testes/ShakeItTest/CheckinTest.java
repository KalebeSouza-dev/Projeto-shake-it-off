package ShakeItTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.descriptor.CompositeTestSource;

import controller.DesafioController;
import controller.ShakeItOffFacade;
import controller.UsuarioController;
import exception.ShakeItOffException;
import model.CheckIn;
import model.Desafio;
import model.Usuario;

class CheckinTest {
	ShakeItOffFacade shakeTest;
	DesafioController DC;
	UsuarioController UC;
	DateTimeFormatter formatter;
	LocalDateTime hj;
	String horaAtual;
	LocalDateTime future;
	LocalDateTime passado;
	Usuario userTest;
	Desafio desafioTest;
	Desafio Dtest;
	CheckIn CH;

	@BeforeEach 
	void SetUp() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DC = new DesafioController();
		UC = new UsuarioController();
		
		hj = LocalDateTime.now();
		future = LocalDateTime.now().plusYears(50);
		passado = LocalDateTime.now().minusYears(50);
		horaAtual = hj.format(formatter);
		String dt = horaAtual;
		
		UC.registrarUsuario("kalebe", "kalebe@", "bom", "senna12");
		UC.registrarUsuario("carlos", "carlos@gmail.com", "gosto de açúcar", "acucar123");
		UC.registrarUsuario("eliane", "eliane@gmail.com", "profP2", "java123");
		userTest = UC.getUsuario("USR1");
		
		DC.criarDesafio(UC, "USR1", "senna12", "ICPC", "Programação Competitiva", "04-09-2000 00:00:00", "04-09-2100 00:00:00", 10, "FREQUENCIA");
		Dtest = DC.getDesafio("DSF1");
		
		DC.ingressarNoDesafio(UC, "USR1", "senna12", "DSF1");
		DC.ingressarNoDesafio(UC, "USR2", "acucar123", "DSF1");
		DC.addCheckin(UC, "USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "", 0, 0);
		CH = Dtest.pegaCheckin("CHK1");	
        
		
		
		shakeTest = new ShakeItOffFacade();
        
		shakeTest.registrarUsuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12");
		shakeTest.registrarUsuario("carlos", "carlos@gmail.com", "gosto de açúcar", "acucar123");
		
		shakeTest.criarDesafio("USR1", "senna12", "ICPC", "Programação Competitiva", "04-09-2000 00:00:00", "04-09-2100 00:00:00", 10, "FREQUENCIA");
		shakeTest.criarDesafio("USR1", "senna12", "des2", "segundo desafio", "04-09-2060 00:00:00", "04-09-2100 00:00:00", 10, "FREQUENCIA");
		
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF2");
	}
	// US 7 - registrar CheckIn
    @Test
	void autenticacaoCheckIn() {
    	try {
			shakeTest.registrarCheckin("USR1", "senna11", "DSF1", "19-09-2050", "CORRIDA", 35, "", 0, 0);
    	} catch (ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		}
	}
    @Test
	void autenticacaoCheckIn2() {
    		String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "CORRIDA", 35, "", 0, 0);
			assertEquals("Usuário não está cadastrado no desafio.", out);
	}
	@Test
	void registrarCheckIn() {
		String dt = horaAtual;
		String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "", 0, 0);
		assertEquals("CHK1", out);
	}
	@Test
	void registrarCheckIn2() {
		String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 35, "ALTA", 290, 10);
		
		assertEquals("CHK1", out);
	}
	@Test
	void desafioFinalizadoCheckIn2() {
		String out = shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 35, "ALTA", 290, 10);
		assertEquals("Não foi possível entrar no desafio.", out);		
	}
	@Test
	void registrarCheckIn4() {
		try {
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", "04-09-2060 00:00:00", "CORRIDA", 35, "", 0, 0);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("O checkin deve ser do dia de hoje.", e.getMessage());
		}
	}
	@Test
	void registrarCheckInHora() {
		// depois
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		} catch (ShakeItOffException e) {
			assertEquals("O desafio deve estar ativo para adicionar checkins.", e.getMessage());
		}
	}
	@Test
	void registrarCheckInHora2() {
		// antes
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		} catch (ShakeItOffException e) {
			assertEquals("O desafio deve estar ativo para adicionar checkins.", e.getMessage());
		}
	}
	@Test
	void registrarCheckInNull() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin(null, "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
		};	
	}
	@Test
	void registrarCheckInNull2() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
		} catch (ShakeItOffException e) {
			assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
		};
		
	}
	@Test
	void registrarCheckInNull3() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", null, "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
		};	
	}
	@Test
	void registrarCheckInNull4() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
		};
	}
	@Test
	void registrarCheckInNull5() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", null, dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
		};	
	}
	@Test
	void registrarCheckInNull6() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", "", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
		};
	}
	@Test
	void registrarCheckInNull7() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, null, 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("atividade não pode ser vazio ou nulo.", e.getMessage());
		};	
	}
	@Test
	void registrarCheckInNull8() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "", 35, "ALTA", 290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("atividade não pode ser vazio ou nulo.", e.getMessage());
		};
	}
	@Test
	void registrarCheckInNull9() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 0, "ALTA", 290, 10);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("Duração inválida", e.getMessage());
		};	
	}
	@Test
	void registrarCheckInNull10() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", -290, 10);
			assertEquals("CHK1", out);
			fail();
		} catch (ShakeItOffException e) {
			assertEquals("Quantidade de calorias inválida.", e.getMessage());
		};	
	}

	@Test
	void registrarCheckIn3() {
		// multiplos checkins
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
		String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 35, "", 0, 0);
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		String out2 = shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", horaAtual, "CORRIDA", 35, "", 0, 0);
		
		assertEquals("CHK1", out);
		assertEquals("CHK2", out2);
	}
	
	@Test
	void autenticacaoInvalidoCheckIn() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senhaErrada", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			fail("Uma exceção era esperada ao passar senha errada");
		} catch (ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		};
	}
	@Test
	void desafioFinalizadoCheckIn() {
		try {
			String dt = horaAtual;
			String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF2", dt, "CORRIDA", 35, "ALTA", 290, 10);
			assertEquals("", out);
			//A mensagem de erro está sendo lançado como exceção
			//fail();
		} catch (ShakeItOffException e) {
			assertEquals("O desafio deve estar ativo para adicionar checkins.", e.getMessage());
		}
	}
	
	@Test
	void userForaDoDesafioCheckIn() {
		String dt = horaAtual;
		String out = shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		assertEquals("Usuário não está cadastrado no desafio.", out);
	}
	@Test
	void duracaoInvalidaCheckIn() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", -1, "ALTA", 290, 10);
			fail("Uma exceção era esperada ao passar duração nula");
		} catch (ShakeItOffException e) {
			assertEquals("Duração inválida", e.getMessage());
		};
	}
	@Test
	void duracaoInvalidaCheckIn2() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", -1, "ALTA", 290, 10);
			fail("Uma exceção era esperada ao passar duração negativa");
		} catch (ShakeItOffException e) {
			assertEquals("Duração inválida", e.getMessage());
		};
	}
	@Test
	void CaloriaInvalidaCheckIn() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 1, "ALTA", -10, 10);
			fail("Uma exceção era esperada ao passar caloria negativa");
		} catch (ShakeItOffException e) {
			assertEquals("Quantidade de calorias inválida.", e.getMessage());
		};
	}
	
	// US 8 - Listar check-in
	@Test
	void listarCheckIn() {
		String dt = horaAtual;
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		String out = shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF1");
		assertEquals("[CHK1] - "+ dt +" [USR1] kalebe - CORRIDA - 35min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n", out);
	}
	@Test
	void listarCheckIn2() {
		try {
			String dt = horaAtual;
			shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
			shakeTest.listarCheckinDesafio("USR1", "senna11", "DSF1");
			fail("Uma exceção era esperada ao passar senha errada.");
		}	catch (ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		};
	}
	@Test
	void listarCheckIn3() {
		String dt = horaAtual;
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 0, 0);
		String out = shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF1");
		assertEquals("[CHK1] - " + dt + " [USR1] kalebe - CORRIDA - 35min - INTENSIDADE: ALTA\n"
				+ "Likes: 0\n"
				+ "Comentários:\n", out);
	}
	@Test
	void listarCheckIn4() {
		String dt = horaAtual;
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "", 290, 10);
		String out = shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF1");
		assertEquals("[CHK1] - "+ dt +" [USR1] kalebe - CORRIDA - 35min - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n", out);
	}
	@Test
	void listarCheckIn5() {
		String dt = horaAtual;
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "", 290, 10);
		String out = shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF1");
		assertEquals("[CHK1] - "+ dt +" [USR1] kalebe - CORRIDA - 35min - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n", out);
	}
	@Test
	void desafioSemCheckIn() {
		String out = shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF1");
		assertEquals("Ainda não houve registro de check-in.", out);
	}
	@Test
	void listarSemDesafioCheckIn() {
		try {
		String out = shakeTest.registrarCheckin("USR2", "acucar123", "DSF2", horaAtual, "Ciclismo", 90, "ALTA", 290, 10);
		//assertEquals("Usuário não está cadastrado no desafio.", out);
		fail();
		} catch (ShakeItOffException e) {
			assertEquals("O desafio deve estar ativo para adicionar checkins.", e.getMessage());
		};
	}
	@Test
	void ordemCheckIn() {
		String dt = horaAtual;
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "Ciclismo", 90, "ALTA", 290, 10);
		
		String out = shakeTest.listarCheckinDesafio("USR2", "acucar123", "DSF1");
		assertEquals("[CHK2] - " + dt + " [USR2] carlos - Ciclismo - 90min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ "[CHK1] - " + dt + " [USR1] kalebe - CORRIDA - 35min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n" , out);
	}
	@Test
	void ordemCheckIn2() {
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		String dt = horaAtual;
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "Ciclismo", 90, "ALTA", 290, 10);
		String out = shakeTest.listarCheckinDesafio("USR2", "acucar123", "DSF1");
		assertEquals("[CHK2] - "+dt+" [USR2] carlos - Ciclismo - 90min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ "[CHK1] - "+dt+" [USR1] kalebe - CORRIDA - 35min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0"
				+ "\nComentários:\n"
				, out);
	}
	
	// US9 lista
	@Test
	void usuarioNoCheckIn() {
		try {
			shakeTest.listarCheckinUsuario("USR1", "senna11");
			fail("Uma exceção era esperada ao passar");
		} catch (ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		};
	}
	@Test
	void usuarioNoCheckIn2() {
		try {
			shakeTest.listarCheckinUsuario("USR2", "acucar123");
			fail("Uma exceção era pois o usuario não faz parte de nenhum desafio");
		} catch (ShakeItOffException e) {
			assertEquals("Usuário não participa de nenhum desafio.", e.getMessage());
		};
	}
	@Test
	void usuarioNoCheckIn3() {
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		String out = shakeTest.listarCheckinUsuario("USR2", "acucar123");
		assertEquals("Ainda não houve registro de check-in.", out);
	}
	@Test
	void ordemCheckIn3() {
		String dt = horaAtual;
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "Ciclismo", 90, "ALTA", 290, 10);
		
		String out = shakeTest.listarCheckinUsuario("USR2", "acucar123");
		assertEquals("[CHK2] - "+dt+" [USR2] carlos - Ciclismo - 90min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ " - Desafio: ICPC\n" 
				+ "[CHK1] - "+dt+" [USR2] carlos - CORRIDA - 35min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ " - Desafio: ICPC\n" , out);
	}
	@Test
	void ordemCheckIn4() {
		String dt = horaAtual; 
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "CORRIDA", 35, "ALTA", 290, 10);
		shakeTest.registrarCheckin("USR2", "acucar123", "DSF1", dt, "Ciclismo", 90, "ALTA", 290, 10);
		String out = shakeTest.listarCheckinUsuario("USR2", "acucar123");
		assertEquals("[CHK2] - "+dt+" [USR2] carlos - Ciclismo - 90min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ " - Desafio: ICPC\n"
				+ "[CHK1] - "+dt+" [USR2] carlos - CORRIDA - 35min - INTENSIDADE: ALTA - 290 kcal\n"
				+ "Likes: 0\n"
				+ "Comentários:\n"
				+ " - Desafio: ICPC\n", out);
	}
	@Test
	void userSemCheckIn() {
		shakeTest.ingressarNoDesafio("USR2", "acucar123", "DSF1");
		String out = shakeTest.listarCheckinUsuario("USR2", "acucar123");
		assertEquals("Ainda não houve registro de check-in." , out);
	}
	@Test
	void listarCheckInDesafioInexistente() {
	    try {
	        shakeTest.listarCheckinDesafio("USR1", "senna12", "DSF999");
	        fail("Uma exceção era esperada para desafio inexistente.");
	    } catch (ShakeItOffException e) {
	        assertEquals("Desafio não existe", e.getMessage());
	    }
	}
	@Test
	void registrarCheckInIntensidadeInvalida() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 30, "*********", 200, 10);
	        fail("Uma exceção era esperada por intensidade inválida.");
	    } catch (ShakeItOffException e) {
	        assertEquals("Intensidade diferente do esperado", e.getMessage());
	    }
	}
	@Test
	void registrarCheckInIntensidadeInvalida2() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 30, "leve", 200, 10);
	        fail("Uma exceção era esperada por intensidade inválida.");
	    } catch (ShakeItOffException e) {
	        assertEquals("Intensidade diferente do esperado", e.getMessage());
	    }
	}
	@Test
	void registrarCheckInIntensidadeInvalida3() {
        String dt = horaAtual;
        String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 30, "", 200, 10);
        assertEquals("CHK1", out);
	}
	@Test
	void listarCheckInUsuarioMultiplosDesafios() {
	    String dt = horaAtual;
	    shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF2");
	    
	    shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 40, "LEVE", 300, 10);
	    shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "Ciclismo", 90, "LEVE", 100, 5);
	    
	    String out = shakeTest.listarCheckinUsuario("USR1", "senna12");
	    assertEquals("[CHK2] - "+dt+" [USR1] kalebe - Ciclismo - 90min - INTENSIDADE: LEVE - 100 kcal\n"
	    		+ "Likes: 0\n"
	    		+ "Comentários:\n"
	    		+ " - Desafio: ICPC\n"
	    		+ "[CHK1] - "+dt+" [USR1] kalebe - CORRIDA - 40min - INTENSIDADE: LEVE - 300 kcal\n"
	    		+ "Likes: 0\n"
	    		+ "Comentários:\n"
	    		+ " - Desafio: ICPC\n", out);
	}
	@Test
	void usuarioInexistenteCheckIn() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR999", "senha123", "DSF1", dt, "CORRIDA", 20, "ALTA", 100, 5);
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Usuário não existe", e.getMessage());
	    }
	}
	@Test
	void usuarioInexistenteCheckIn2() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR1", "senha11", "DSF1", dt, "CORRIDA", 20, "ALTA", 100, 5);
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Usuário não autenticado.", e.getMessage());
	    }
	}
	@Test
	void desafioInexistenteCheckIn() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR1", "senna12", "DSF999", dt, "CORRIDA", 35, "ALTA", 200, 10);
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Desafio não existe", e.getMessage());
	    }
	}
	@Test
	void listarCheckInUsuarioSenhaVazia() {
	    try {
	        shakeTest.listarCheckinUsuario("USR1", "");
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
	    }
	}
	@Test
	void listarCheckInUsuarioSenhaVazia2() {
	    try {
	        shakeTest.listarCheckinUsuario("USR1", null);
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
	    }
	}
	@Test
	void registrarCheckInDuracao0() {
	    try {
	        String dt = horaAtual;
	        shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 0, "ALTA", 100, 5);
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Duração inválida", e.getMessage());
	    }
	}
	@Test
	void registrarCheckInCaloriaZero() {
	    String dt = horaAtual;
	    String out = shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CAMINHADA", 30, "ALTA", 0, 5);
	    assertEquals("CHK1", out);
	}
	@Test
	void registrarCheckInCaloriaNeg() {
		try {
		    String dt = horaAtual;
		    shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CAMINHADA", 30, "ALTA", -1000, 5);
		} catch (ShakeItOffException e) {
	        assertEquals("Quantidade de calorias inválida.", e.getMessage());
	    }
	}

}
