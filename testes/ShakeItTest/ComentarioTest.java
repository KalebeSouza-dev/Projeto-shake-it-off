package ShakeItTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ShakeItOff.Comentario;
import controller.DesafioController;
import controller.ShakeItOffFacade;
import controller.UsuarioController;
import exception.ShakeItOffException;
import model.CheckIn;
import model.Desafio;
import model.Usuario;

class ComentarioTest {
	ShakeItOffFacade shakeTest;
	DesafioController DC;
	UsuarioController UC;
	DateTimeFormatter formatter;
	LocalDateTime hj;
	String horaAtual;
	LocalDateTime future;
	LocalDateTime passado;
	Usuario userTest;
	Desafio Dtest;
	CheckIn CH;

	@BeforeEach 
	void SetUp() {
		shakeTest = new ShakeItOffFacade();
		DC = new DesafioController();
		UC = new UsuarioController();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		hj = LocalDateTime.now();
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
        
		// -------------------------------------------------------
		shakeTest.registrarUsuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12");
		shakeTest.registrarUsuario("carlos", "carlos@gmail.com", "gosto de açúcar", "acucar123");
		
		shakeTest.criarDesafio("USR1", "senna12", "ICPC", "Programação Competitiva", "04-09-2000 00:00:00", "04-09-2100 00:00:00", 10, "FREQUENCIA");
		shakeTest.criarDesafio("USR1", "senna12", "des2", "segundo desafio", "04-09-2060 00:00:00", "04-09-2100 00:00:00", 10, "FREQUENCIA");
		
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF2");
		
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", dt, "CORRIDA", 35, "", 0, 0);
	}
	
	@Test
	void autenticacaoCurtir() {
    	try {
			shakeTest.curtirCheckin("USR1", "senna11", "DSF1", "CHK1");
    	} catch (ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir2() {
    	try {
			shakeTest.curtirCheckin("USR2", "acucar123", "DSF1", "CHK1");
    	} catch (ShakeItOffException e) {
			assertEquals("Deve participar do desafio para curtir check in dele.", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir3() {
    	try {
			shakeTest.curtirCheckin("USR2", "acucar123", "DSF1", "CHK2");
    	} catch (ShakeItOffException e) {
			assertEquals("Checkin não existe", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir4() {
    	try {
			shakeTest.curtirCheckin("USR2", "acucar123", "DSF3", "CHK1");
    	} catch (ShakeItOffException e) {
			assertEquals("Desafio não existe", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir5() {
    	try {
			shakeTest.curtirCheckin("USR3", "acucar123", "DSF1", "CHK1");
    	} catch (ShakeItOffException e) {
			assertEquals("Usuário não existe", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir6() {
    	try {
			shakeTest.curtirCheckin("USR3", "acucar123", "DSF1", "CHK1");
    	} catch (ShakeItOffException e) {
			assertEquals("Usuário não existe", e.getMessage());
		}
	}
	@Test
	void autenticacaoCurtir7() {
		//verifica uma nova curtida
		int i = CH.getCurtidas().size();
		assertEquals(0, i);
		DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
		i = CH.getCurtidas().size();
		assertEquals(1, i);
	}
	@Test
	void autenticacaoCurtir8() {
		//duas curtidas do user??
		DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
		int i = CH.getCurtidas().size();
		assertEquals(1, i);
		
		try {
		DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
		i = CH.getCurtidas().size();
		assertEquals(1, i);
		} catch(ShakeItOffException e) {
			assertEquals("Checkin já curtido pelo usuário", e.getMessage());
		}
		
	}
	@Test
	void autenticacaoCurtir9() {
		//duas curtidas
		int i = CH.getCurtidas().size();
		assertEquals(0, i);
		DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
		DC.curtirCheckin(UC, "USR2", "acucar123", "DSF1", "CHK1");
		i = CH.getCurtidas().size();
		assertEquals(2, i);
	}
	@Test
	void autenticacaoCurtir10() {
		try {
			DC.curtirCheckin(UC, "USR3", "java123", "DSF1", "CHK1");
			CH.getCurtidas().size();
			fail();
			} catch(ShakeItOffException e) {
				assertEquals("Deve participar do desafio para curtir check in dele.", e.getMessage());
			}
	}
	@Test
	void autenticacaoCurtir11() {
		try {
			DC.curtirCheckin(UC, "USR3", "java123", "DSF1", "CHK1");
			CH.getCurtidas().size();
			fail();
			} catch(ShakeItOffException e) {
				assertEquals("Deve participar do desafio para curtir check in dele.", e.getMessage());
			}
	}
	@Test
	void autenticacaoCurtir12() {
	    try {
	        // senha vazia
	        shakeTest.curtirCheckin("USR1", "", "DSF1", "CHK1");
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
	    }
	}

	@Test
	void autenticacaoCurtir13() {
	    try {
	        // senha nula
	        shakeTest.curtirCheckin("USR1", null, "DSF1", "CHK1");
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
	    }
	}

	@Test
	void autenticacaoCurtir14() {
	    // mesmo check-in curtido por mais de dois usuários diferentes
	    DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
	    DC.curtirCheckin(UC, "USR2", "acucar123", "DSF1", "CHK1");
	    int i = CH.getCurtidas().size();
	    assertEquals(2, i);
	}

	@Test
	void autenticarComentario() {
		try {
			shakeTest.comentarCheckin("USR1", "senna11", "DSF1", "CHK1", "Senna MP4");
		} catch(ShakeItOffException e) {
			assertEquals("Usuário não autenticado.", e.getMessage());
		}
	}
	@Test
	void comentario1() {
		String out = shakeTest.comentarCheckin("USR1", "senna12", "DSF1", "CHK1", "");
		assertEquals("Comentário não pode ser vazio.", out);
	}
	@Test
	void comentario2() {
		String out = shakeTest.comentarCheckin("USR1", "senna12", "DSF1", "CHK1", null);
		assertEquals("Comentário não pode ser vazio.", out);
	}
	@Test
	void comentario3() {
		try {
		shakeTest.comentarCheckin("USR1", "senna12", "DSF3", "CHK1" , "Senna MP4");
		fail();
		} catch(ShakeItOffException e) {
			assertEquals("Desafio não existe", e.getMessage());
		}
	}
	@Test
	void comentario4() {
		try {
			shakeTest.comentarCheckin("USR1", "senna12", "DSF1", "CHK3", "Senna MP4");
			fail();
		} catch(ShakeItOffException e) {
			assertEquals("Checkin não existe", e.getMessage());
		}
	}
	@Test
	void comentario5() {
		try {
			shakeTest.comentarCheckin("USR5", "senna12", "DSF1", "CHK1", "Senna MP4");
			fail();
		} catch(ShakeItOffException e) {
			assertEquals("Usuário não existe", e.getMessage());
		}
	}
	@Test
	void comentario6() {
			String out = DC.comentarCheckin(UC,"USR3", "java123", "DSF1", "CHK1", "Senna MP4");
			assertEquals("Deve participar do desafio para curtir check in dele.", out);
	}
	@Test
	void comentario7() {
			String out = DC.comentarCheckin(UC,"USR3", "java123", "DSF1", "CHK1", "Senna MP4");
			assertEquals("Deve participar do desafio para curtir check in dele.", out);
	}
	@Test
	void comentario8() {
			DC.comentarCheckin(UC,"USR1", "senna12", "DSF1", "CHK1", "Senna MP4");
			ArrayList<Comentario> out = CH.getComentarios();
			assertEquals("USR1 kalebe: Senna MP4", out.get(0).toString());
	}
	@Test
	void comentario9() {
			DC.comentarCheckin(UC,"USR1", "senna12", "DSF1", "CHK1", "Senna MP4");
			ArrayList<Comentario> out = CH.getComentarios();
			assertEquals("USR1 kalebe: Senna MP4", out.get(0).toString());
	}
	@Test
	void comentario10() {
	    DC.comentarCheckin(UC,"USR1", "senna12", "DSF1", "CHK1", "Senna MP4");
	    DC.comentarCheckin(UC,"USR1", "senna12", "DSF1", "CHK1", "Lewis Hamilton 44");
	    ArrayList<Comentario> out = CH.getComentarios();

	    assertEquals("USR1 kalebe: Senna MP4", out.get(0).toString());
	    assertEquals("USR1 kalebe: Lewis Hamilton 44", out.get(1).toString());
	}
	@Test
	void comentario11() {
		try {
	    DC.comentarCheckin(UC,"USR1", "senna12", "DSF2", "CHK1", "Senna MP4");
	    DC.comentarCheckin(UC,"USR1", "senna12", "DSF1", "CHK1", "Lewis Hamilton 44");
	    
		} catch(ShakeItOffException e) {
			assertEquals("Desafio não existe", e.getMessage());
		}
	}
	@Test
	void comentario12() {
	    try {
	        // senha incorreta 
	        shakeTest.comentarCheckin("USR1", "senhaErrada", "DSF1", "CHK1", "teste");
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Usuário não autenticado.", e.getMessage());
	    }
	}

	@Test
	void comentario13() {
	    // dois comentários em sequência pelo mesmo usuário
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Primeiro");
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Segundo");
	    ArrayList<Comentario> out = CH.getComentarios();

	    assertEquals("USR1 kalebe: Primeiro", out.get(0).toString());
	    assertEquals("USR1 kalebe: Segundo", out.get(1).toString());
	}

	@Test
	void comentario14() {
        String out = shakeTest.comentarCheckin("USR2", "acucar123", "DSF1", "CHK1", "oi");
        assertEquals("Deve participar do desafio para curtir check in dele.", out);
	}

	@Test
	void comentario15() {
	    String out = shakeTest.comentarCheckin("USR1", "senna12", "DSF1", "CHK1", "   ");
	    assertEquals("Comentário não pode ser vazio.", out);
	}
	@Test
	void comentario16() {
	    try {
	        shakeTest.comentarCheckin("USR2", "acucar123", "DSF2", "CHK1", "oi");
	        fail();
	    } catch (ShakeItOffException e) {
	        assertEquals("Checkin não existe", e.getMessage());
	    }
	}
	@Test
	void curtirEComentarCheckIn() {
	    DC.curtirCheckin(UC, "USR1", "senna12", "DSF1", "CHK1");
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Muito bom!");

	    int curtidas = CH.getCurtidas().size();
	    ArrayList<Comentario> comentarios = CH.getComentarios();

	    assertEquals(1, curtidas);
	    assertEquals("USR1 kalebe: Muito bom!", comentarios.get(0).toString());
	}
	@Test
	void ordemComentariosCheckIn() {
	    // Primeiro
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Primeiro comentário");
	    // Segundo
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Segundo comentário");
	    // Terceiro
	    DC.comentarCheckin(UC, "USR1", "senna12", "DSF1", "CHK1", "Terceiro comentário");

	    ArrayList<Comentario> comentarios = CH.getComentarios();

	    assertEquals(3, comentarios.size());
	    assertEquals("USR1 kalebe: Primeiro comentário", comentarios.get(0).toString());
	    assertEquals("USR1 kalebe: Segundo comentário", comentarios.get(1).toString());
	    assertEquals("USR1 kalebe: Terceiro comentário", comentarios.get(2).toString());
	}
	
}