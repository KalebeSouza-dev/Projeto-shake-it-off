package ShakeItTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ShakeItOffFacade;
import controller.UsuarioController;
import exception.ShakeItOffException;
import model.Desafio;
import model.Usuario;

class UsuarioControllerTest {
	ShakeItOffFacade shakeTest;
	Usuario userTest;
	Desafio desafioTest;
	UsuarioController UC;
	// commit test
	@BeforeEach 
	void SetUp() {
		shakeTest = new ShakeItOffFacade();
		LocalDateTime inicio = LocalDateTime.of(2000, 9, 4, 9, 0);
        LocalDateTime fim    = LocalDateTime.of(2100, 9, 4, 17, 30);
        UC = new UsuarioController();
        
		//userTest = new Usuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12");
		shakeTest.registrarUsuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12");
		shakeTest.registrarUsuario("carlos", "carlos@gmail.com", "gosto de açúcar", "acucar123");
		shakeTest.criarDesafio("USR1", "senna12", "ICPC", "Programação Competitiva", "25-09-2025 00:00:00", "05-10-2025 00:00:00", 10, "FREQUENCIA");
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
	}
	@Test  
	void user1(){
		UC.registrarUsuario("Gabriel","gb@gmail.com", "cp", "cpvida123");
		assertTrue(UC.autenticacao("USR1", "cpvida123"));
	}
	@Test
	void cadastraUsuario() {
		String out = shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galante_maioral");
		assertEquals("USR3",out);
		String out2 = shakeTest.registrarUsuario("lucas2", "lucas2@gmail.com", "amo java2", "galante_maioral2");
		assertEquals("USR4",out2);
	}
	@Test
	void cadastraUsuarioEmailRepetido() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galante_maioral");
		String out2 = shakeTest.registrarUsuario("Carlos", "lucas@gmail.com", "corinthias", "carlos1223");
		assertEquals("Email já cadastrado.",out2);
	}
	@Test
	void cadastraUsuarioSenhaInvalida() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galan");
		});
		assertEquals("Senha inválida (menos de 6 caracteres)", excp.getMessage());
	}

	@Test
	void cadastraUsuarioNomeNulo() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario(null, "lucas@gmail.com", "amo java", "galante");
		});
		assertEquals("Nome não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioEmailNulo() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas",null, "amo java", "galante");
		});
		assertEquals("Email não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioBioNula() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas","lucas@gmail.com", null, "galante");
		});
		assertEquals("Biografia não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioSenhaNula() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas","lucas@gmail.com", "amo java", null);
		});
		assertEquals("Senha não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioNomeVazio() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("", "lucas@gmail.com", "amo java", "galante");
		});
		assertEquals("Nome não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioEmailVazio() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas","", "amo java", "galante");
		});
		assertEquals("Email não pode ser vazio ou nulo.", excp.getMessage());
	}
	@Test
	void cadastraUsuarioBioVazia() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.registrarUsuario("lucas","lucas@gmail.com", "", "galante");
		});
		assertEquals("Biografia não pode ser vazio ou nulo.", excp.getMessage());
	}
	
	@Test
	void editaPerfil() {

		String out = shakeTest.editarPerfil("USR1", "senna12", "galante_capital", "lucas dantas", "lucasMoizinho@gmail.com", "amo java e igreja");

		assertEquals("Perfil atualizado",out);
	}
	@Test
	void editaPerfilEmailCadastrado() {
		String id = shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galante_maioral");
		String out = shakeTest.editarPerfil(id, "galante_maioral", "galante_capital", "lucas dantas", "kalebe@gmail.com","amo java e igreja");
		assertEquals("Não foi possível atualizar o perfil: email já cadastrado.",out);
	}
	
	@Test
	void editaPerfilApenasNome() {
		String id = shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galante_maioral");
		String out = shakeTest.editarPerfil(id, "galante_maioral", "", "lucas dantas", "", "");
		assertEquals("Perfil atualizado",out);
	}
	@Test
	void editaPerfilApenasNomeBio() {
		String id = shakeTest.registrarUsuario("lucas", "lucas@gmail.com", "amo java", "galante_maioral");
		String out = shakeTest.editarPerfil(id, "galante_maioral", "", "lucas dantas", "", "java e igreja é bom dms");
		assertEquals("Perfil atualizado",out);
	}
	@Test
	void editaPerfilUserNaoAutenticado() {

		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.editarPerfil("USER17", "senhaaa","JJ1212","Joarez","joarez1208@gmail.com","eu amo java");
		});
		assertEquals("Usuário não existe", excp.getMessage());
	}
	@Test
	void editaPerfilUserNaoAutenticado2() {
		
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.editarPerfil("USR1", "senhaaa","JJ1212","Joarez","joarez1208@gmail.com","eu amo java");
		});
	
		assertEquals("Usuário não autenticado.", excp.getMessage());
	}
	@Test
	void editaPerfilUserNaoAutenticado3() {
		
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.editarPerfil("USER17", "senna12","JJ1212","Joarez","joarez1208@gmail.com","eu amo java");
		});
		
		assertEquals("Usuário não existe", excp.getMessage());
	}
	@Test
	void editaPerfilNovaSenhaInvalida() {
		
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.editarPerfil("USR1","senna12","JJ2","Joarez","joarez1208@gmail.com","eu amo java");
		});
		assertEquals("Nova senha inválida", excp.getMessage());
	}
	@Test	
	void listarUsuarios() {
		String out = shakeTest.listarUsuarios("USR1", "senna12");
		assertEquals("[USR2] carlos (carlos@gmail.com) gosto de açúcar." + "\n" + "[USR1] kalebe (kalebe@gmail.com) gosto muito de Fórmula 1.",out);
	}
	@Test	
	void listarUsuariosUserNaoAutenticado() {
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.listarUsuarios("USR13", "senna12");
		});
		assertEquals("Usuário não existe", excp.getMessage());
	}
	@Test
	void listarUsuariosUserNaoAutenticado2() {
		
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.listarUsuarios("USR1", "CR1234567");
		});
		assertEquals("Usuário não autenticado.", excp.getMessage());
	}
	
	@Test	
	void listarUsuariosUserNaoAutenticado3() {
		
		ShakeItOffException excp = assertThrows(ShakeItOffException.class,() -> {
			shakeTest.listarUsuarios("USR13", "CR1234567");
		});
		assertEquals("Usuário não existe", excp.getMessage());
	}
	@Test
	void listarUsuariosSortados() {
		shakeTest.registrarUsuario("adson", "adson@gmail.com", "amo AA", "aadson123");
		shakeTest.registrarUsuario("bruno", "bruno@gmail.com", "amo AA", "bruno123");
		shakeTest.registrarUsuario("daniel", "daniel@gmail.com", "amo AA", "daniel");
		String out = shakeTest.listarUsuarios("USR1", "senna12");
		assertEquals("[USR3] adson (adson@gmail.com) amo AA.\n" + "[USR4] bruno (bruno@gmail.com) amo AA.\n" + "[USR2] carlos (carlos@gmail.com) gosto de açúcar.\n"+ 
				 "[USR5] daniel (daniel@gmail.com) amo AA.\n" + "[USR1] kalebe (kalebe@gmail.com) gosto muito de Fórmula 1.",out);
	}
	
	@Test 
	void existeUsuarioTest() {
		UC.registrarUsuario("Beltrao", "bel@gmail.com", "aaaaaa", "teste123");
		assertTrue(UC.existeUsuario("USR1"));
	}
	@Test 
	void existeUsuarioInexistenteTest() {
		UC.registrarUsuario("Beltrao", "bel@gmail.com", "aaaaaa", "teste123");
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {			
			UC.existeUsuario("USR10");
		});
		assertEquals("Usuário não existe",err.getMessage());
	}
}
