package ShakeItTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Usuario;

class UsuarioTest {
	Usuario kalebe;

	@BeforeEach
	void setUp() {
		kalebe = new Usuario("Kalebe","kalebinho_eu@gmail.com","f1 é muito bom","adorof1");
		kalebe.setID(1);
	}
	
	@Test
	void testToString() {
		String out = "[USR1] Kalebe (kalebinho_eu@gmail.com) f1 é muito bom.";
		assertEquals(kalebe.toString(),out);
	}
	@Test
	void testEquals() {
		Usuario teste = new Usuario("Savio","kalebinho_eu@gmail.com","amo lagoa","clash123");
		assertEquals(kalebe,teste);
	}
	@Test
	void testEqualsDiferente() {
		Usuario teste = new Usuario("Savio","savinho@gmail.com","amo lagoa","clash123");
		assertNotEquals(kalebe,teste);
	}
	@Test
	void testCompareToMaior() {
		Usuario teste = new Usuario("Andre","andre_eu@gmail.com","amo lagoa","clash123");
		assertEquals(10,kalebe.compareTo(teste));
	}
	@Test
	void testCompareToMenor() {
		Usuario teste = new Usuario("Andre","andre_eu@gmail.com","amo lagoa","clash123");
		assertEquals(-10,teste.compareTo(kalebe));
	}
	@Test
	void testCompareToIgual() {
		Usuario teste = new Usuario("Andre","andre_eu@gmail.com","amo lagoa","clash123");
		assertEquals(0,teste.compareTo(teste));
	}
	@Test
	void testEditarDados() {
		kalebe.editarDados("adorof1_e","Kalebe_e","kalebinho_eu@gmail.com_e","f1 é muito bom_e");
		String out = "[USR1] Kalebe_e (kalebinho_eu@gmail.com_e) f1 é muito bom_e.";
		assertEquals(out,kalebe.toString());
	}
	@Test
	void testEditarDadosNome() {
		kalebe.editarDados("","Kalebe_e","","");
		String out = "[USR1] Kalebe_e (kalebinho_eu@gmail.com) f1 é muito bom.";
		assertEquals(out,kalebe.toString());
	}
	@Test
	void testEditarDadosEmail() {
		kalebe.editarDados("","","kalebinho_eu@gmail.com_e","");
		String out = "[USR1] Kalebe (kalebinho_eu@gmail.com_e) f1 é muito bom.";
		assertEquals(out,kalebe.toString());
	}
	@Test
	void testEditarDadosMudarNada() {
		kalebe.editarDados("","","","");
		String out = "[USR1] Kalebe (kalebinho_eu@gmail.com) f1 é muito bom.";
		assertEquals(out,kalebe.toString());
	}

}
