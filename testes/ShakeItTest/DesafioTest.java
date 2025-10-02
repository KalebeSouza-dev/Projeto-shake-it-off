package ShakeItTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ShakeItOff.pontuacao.Pontuacao;
import ShakeItOff.pontuacao.Tempo;
import exception.ShakeItOffException;
import model.CheckIn;
import model.Desafio;
import model.Usuario;

class DesafioTest {
	private Desafio desafioBase;
	private LocalDateTime inicio ;
    private LocalDateTime fim;
    private Usuario user1;
    private Usuario user2;
    private CheckIn checkin1;
    
	
	@BeforeEach
	void SetUp() {
		LocalDateTime inicio = LocalDateTime.of(2000, 9, 4, 9, 0);
        LocalDateTime fim    = LocalDateTime.of(2100, 9, 4, 17, 30);
        user1 = new Usuario("Renan", "Renan@", "sou foda", "123456");
		user1.setID(1);
		desafioBase = new Desafio("USR1", "Renan", "Maratona", "Prova de Competicao", inicio, fim, 1, "TEMPO");
		desafioBase.setID("DSF1");
		checkin1 = new CheckIn("USR1","Renan",LocalDateTime.now(), "CORRIDA", 45, "ALTA", 300, 150);
        checkin1.setId("CHK1");
	}
	
	@Test
	void addUsuario() {
		Usuario user1 = new Usuario("Renan", "Renan@", "sou foda", "123456");
		user1.setID(1);
		assertEquals("Você está participando!",desafioBase.addUsuario(user1));
		
	}

 
    @Test
    void toStringTest() {
        String resultado = desafioBase.toString();
        String out = "[DSF1] Maratona (04/09/2000 a 04/09/2100), criado por: Renan\n" +
        "Prova de Competicao\n" + "Meta diária minima: 1; Pontuação por: TEMPO";
        assertEquals(out,resultado);
    }

    @Test
    void usuarioInDesafioTest() {
        desafioBase.addUsuario(user1);
        assertTrue(desafioBase.usuarioInDesafio("USR1"));
    }

    @Test
    void addUsuarioTest() {
        assertFalse(desafioBase.usuarioInDesafio("USR999"));
    }

    @Test
    void addUsuarioTest2() {
        String resultado = desafioBase.addUsuario(user1);
        assertEquals("Você está participando!", resultado);
    }

    @Test
    void addUsuarioRepetido() {
    	desafioBase.addUsuario(user1);
        String resultado = desafioBase.addUsuario(user1);
        assertEquals("Não foi possível entrar no desafio.", resultado);
    }

    @Test
    void addCheckin() {
    	desafioBase.addCheckin(checkin1);
        assertNotNull(desafioBase.pegaCheckin("CHK1"));
    }

    @Test
    void pegaCheckinInexistente() {
        assertNull(desafioBase.pegaCheckin("CHK999"));
    }

    @Test
    void existeCheckin1() {
    	desafioBase.addCheckin(checkin1);
        assertTrue(desafioBase.existeCheckin("CHK1"));
    }

    @Test
    void existeCheckin2() {
        assertThrows(ShakeItOffException.class, () -> {        	
        	desafioBase.existeCheckin("CHK999");
        });
    }

    @Test
    void extrairId() {
    	desafioBase.setID("DSF123");
        assertEquals(123, desafioBase.extrairId());
    }

    @Test
    void getLenCheckinsVazio() {
        assertEquals(1, desafioBase.getLenCheckins());
    }

    @Test
    void getTamTest1() {
    	desafioBase.addCheckin(checkin1);
        assertEquals(2, desafioBase.getLenCheckins());
    }

    @Test
    void getListTest() {
    	desafioBase.addCheckin(checkin1);
        ArrayList<CheckIn> resultado = desafioBase.getCheckins();
        assertEquals(1, resultado.size());
    }

    @Test
    void verificarStatus1() {
        LocalDateTime agora = LocalDateTime.now();
        Desafio d = new Desafio("USR1", "João", "Teste", "Desc", 
                               agora.minusDays(1), agora.plusDays(1), 10, "TEMPO");
        assertTrue(d.verificarStatus());
    }

    @Test
    void verificarStatus2() {
        LocalDateTime agora = LocalDateTime.now();
        Desafio d = new Desafio("USR1", "João", "Teste", "Desc", 
                               agora.plusDays(1), agora.plusDays(2), 10, "TEMPO");
        assertFalse(d.verificarStatus());
    }

    @Test
    void compareTo1() {
        Desafio d1 = new Desafio("USR1", "João", "T1", "D1", inicio, fim, 10, "TEMPO");
        Desafio d2 = new Desafio("USR2", "Maria", "T2", "D2", inicio, fim, 20, "TEMPO");
        d1.setID("DSF5");
        d2.setID("DSF10");
        assertTrue(d1.compareTo(d2) < 0);
    }

    @Test
    void compareTo2() {
        Desafio d1 = new Desafio("USR1", "João", "T1", "D1", inicio, fim, 10, "TEMPO");
        Desafio d2 = new Desafio("USR2", "Maria", "T2", "D2", inicio, fim, 20, "TEMPO");
        d1.setID("DSF15");
        d2.setID("DSF8");
        assertTrue(d1.compareTo(d2) > 0);
    }

    @Test
    void compareTo3() {
        Desafio d1 = new Desafio("USR1", "João", "T1", "D1", inicio, fim, 10, "TEMPO");
        Desafio d2 = new Desafio("USR2", "Maria", "T2", "D2", inicio, fim, 20, "TEMPO");
        d1.setID("DSF10");
        d2.setID("DSF10");
        assertEquals(0, d1.compareTo(d2));
    }
}
