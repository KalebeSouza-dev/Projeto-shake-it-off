package ShakeItTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import controller.ShakeItOffFacade;
import exception.ShakeItOffException;

class DesafioControllerTest {
	private ShakeItOffFacade shakeTest;
	private DateTimeFormatter formatter;
	private String inicio;
	private String horaAtual;
	private String fim;
	// kalebe, renan, raiutu
	@BeforeEach
	void SetUp() {
		this.inicio = "02-03-2006 00:00:00";
		this.fim = "02-03-2100 00:00:00";
		this.shakeTest = new ShakeItOffFacade();
		shakeTest.registrarUsuario("Renan", "Renan@", "renamm", "senna12");
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		LocalDateTime hj = LocalDateTime.now();
		horaAtual = hj.format(formatter);
		shakeTest.criarDesafio("USR1","senna12","Madrugada", "desafio terminar projeto",inicio, fim, 20, "TEMPO");
		shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1"); 
		
	}
	@Test
    void criarDesafioSucessoRetornaDSF2() {
        assertEquals("DSF2",
            shakeTest.criarDesafio("USR1", "senna12", "OP1", "Campeonato de programação", inicio, fim, 2, "FREQUENCIA"));
    }

    @Test
    void criarDesafioIdUserNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio(null, "senna12", "OP1", "Campeonato", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioIdUserVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("", "senna12", "OP1", "Campeonato", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioSenhaNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", null, "Campeonato", "Descrição", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }
 
    @Test
    void criarDesafioSenhaVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "", "Campeonato", "Descrição", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioTituloNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", null, "Descrição", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("titulo não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioTituloVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "", "Descrição", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("titulo não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioDescricaoNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", null, inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("descricao não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioDescricaoVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("descricao não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioInicioNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", null, fim, 1, "FREQUENCIA")
        );
        assertEquals("inicio não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioFimNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", inicio, null, 1, "FREQUENCIA")
        );
        assertEquals("fim não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioMetaDiariaZero() {
        assertEquals("DSF2", shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", inicio, fim, 0, "FREQUENCIA"));
    }

    @Test
    void criarDesafioMetaDiariaNegativa() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", inicio, fim, -1, "FREQUENCIA")
        );
        assertEquals("metaDiariaMin não pode ser negativo.", e.getMessage());
    }

    @Test
    void criarDesafioEstrategiaNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", inicio, fim, 1, null)
        );
        assertEquals("estrategiaPontuacao não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioEstrategiaVazia() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "Campeonato", "Descrição", inicio, fim, 1, "")
        );
        assertEquals("estrategiaPontuacao não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void criarDesafioInicioDepoisDoFim() {
    	String inicioo = "04-03-2200 00:00:00";
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "OP1", "Campeonato de programação", inicioo, fim, 1, "FREQUENCIA")
        );
        assertEquals("inicio não pode ser depois do fim", e.getMessage());
    }

    @Test
    void criarDesafioEstrategiaInvalida() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senna12", "OP1", "Campeonato", inicio, fim, 1, "Faltas")
        );
        assertEquals("Estrategia de pontuacao invalida", e.getMessage());
    }

    @Test
    void criarDesafioUsuarioExisteSenhaIncorreta() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.criarDesafio("USR1", "senhaErrada", "OP1", "Campeonato", inicio, fim, 1, "FREQUENCIA")
        );
        assertEquals("Usuário não autenticado.", e.getMessage());
    }
    @Test
    void listarDesafiosIdUsuarioNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios(null, "senna12")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosIdUsuarioVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios("", "senna12")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosSenhaNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios("USR1", null)
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosSenhaVazia() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios("USR1", "")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosUsuarioNaoExiste() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios("USR100", "qualquer")
        );
        assertEquals("Usuário não existe", e.getMessage());
    }

    @Test
    void listarDesafiosUsuarioExisteSenhaIncorreta() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafios("USR1", "senhaErrada")
        );
        assertEquals("Usuário não autenticado.", e.getMessage());
    }

    @Test
    void listarDesafiosSucesso() {
    	shakeTest.criarDesafio("USR1", "senna12", "Olimpiada", "Olimpiadaaaa", inicio, fim, 5, "TEMPO");
   
        String esperado = "[DSF1] Madrugada (02/03/2006 a 02/03/2100), criado por: Renan\n"
        		+ "desafio terminar projeto\n"
        		+ "Meta diária minima: 20; Pontuação por: TEMPO\n"
        		+ "[DSF2] Olimpiada (02/03/2006 a 02/03/2100), criado por: Renan\n"
        		+ "Olimpiadaaaa\n"
        		+ "Meta diária minima: 5; Pontuação por: TEMPO\n";
       assertEquals(esperado, shakeTest.listarDesafios("USR1", "senna12"));
    }

    @Nested
    class ListarDesafios_SemDados {
        @BeforeEach
        void setUpNested() {
            shakeTest = new ShakeItOffFacade();
            shakeTest.registrarUsuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12"); // USR1
        }

        @Test
        void listarDesafiosSemDesafiosCadastrados() {
            ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
                shakeTest.listarDesafios("USR1", "senna12")
            );
            assertEquals("Nenhum desafio válido", e.getMessage());
        }
    }
    @Test
    void listarDesafiosAtivosIdUsuarioNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos(null, "senna12")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosAtivosIdUsuarioVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos("", "senna12")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosAtivosSenhaNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos("USR1", null)
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosAtivosSenhaVazia() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos("USR1", "")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void listarDesafiosAtivosUsuarioNaoExiste() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos("USR100", "qualquer")
        );
        assertEquals("Usuário não existe", e.getMessage());
    }

    @Test
    void listarDesafiosAtivosUsuarioExisteSenhaIncorreta() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.listarDesafiosAtivos("USR1", "senhaErrada")
        );
        assertEquals("Usuário não autenticado.", e.getMessage());
    }

    @Nested
    class ListarDesafiosAtivosSemAtivos {
        @BeforeEach
        void setUpNested() {
            shakeTest = new ShakeItOffFacade();
            shakeTest.registrarUsuario("kalebe", "kalebe@gmail.com", "gosto muito de Fórmula 1", "senna12"); // USR1
        }

        @Test
        void semDesafiosCadastrados() {
            ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
                shakeTest.listarDesafiosAtivos("USR1", "senna12")
            );
            assertEquals("Nenhum desafio válido", e.getMessage());
        }

        @Test
        void comDesafioCadastradoMasNaoAtivo() {
            shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", inicio, fim, 2, "FREQUENCIA");
            String out = shakeTest.listarDesafiosAtivos("USR1", "senna12");
            
            assertEquals("[DSF1] OBI (02/03/2006 a 02/03/2100), criado por: kalebe\n"
            		+ "Treinamento\n"
            		+ "Meta diária minima: 2; Pontuação por: FREQUENCIA\n"
            		, out);
        }
    }

    @Test
    void listarDesafiosAtivosComDesafiosJaFinalizadosRetornarApenasAtivos() {
        shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", inicio, fim, 2, "FREQUENCIA");
        String result = shakeTest.listarDesafiosAtivos("USR1", "senna12");
        String esperado = "[DSF1] Madrugada (02/03/2006 a 02/03/2100), criado por: Renan\n"
        		+"desafio terminar projeto\n"
        		+"Meta diária minima: 20; Pontuação por: TEMPO\n"
        		+"[DSF2] OBI (02/03/2006 a 02/03/2100), criado por: Renan\n"
        		+"Treinamento\n"
        		+"Meta diária minima: 2; Pontuação por: FREQUENCIA\n";
        assertEquals(esperado, result);
    }
    @Test
    void ingressarNoDesafioIdUsuarioNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio(null, "senna12", "DSF1")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioIdUsuarioVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("", "senna12", "DSF1")
        );
        assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioSenhaNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", null, "DSF1")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioSenhaVazia() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", "", "DSF1")
        );
        assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioIdDesafioNull() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", "senna12", null)
        );
        assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioIdDesafioVazio() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", "senna12", "")
        );
        assertEquals("idDesafio não pode ser vazio ou nulo.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioUsuarioEDesafioInexistentes() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR999", "qualquer", "DSF999")
        );
        assertEquals("Usuário não existe", e.getMessage());
    }

    @Test
    void ingressarNoDesafioUsuarioNaoExisteDesafioExiste() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR999", "qualquer", "DSF1")
        );
        assertEquals("Usuário não existe", e.getMessage());
    }

    @Test
    void ingressarNoDesafioUsuarioExisteDesafioNaoExiste() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF999")
        );
        assertEquals("Desafio não existe", e.getMessage());
    }

    @Test
    void ingressarNoDesafioUsuarioExisteSenhaIncorreta() {
        ShakeItOffException e = assertThrows(ShakeItOffException.class, () ->
            shakeTest.ingressarNoDesafio("USR1", "senhaErrada", "DSF1")
        );
        assertEquals("Usuário não autenticado.", e.getMessage());
    }

    @Test
    void ingressarNoDesafioUsuarioJaParticipa() {
    	shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", inicio, fim, 2, "FREQUENCIA");
    	shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
        String resultado = shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
        assertEquals("Não foi possível entrar no desafio.", resultado);
    }
    @Test
    void ingressarNoDesafioComSucesso() {
    	
    	shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", inicio, fim, 2, "FREQUENCIA");
        String resultado = shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF2");
        
        assertEquals("Você está participando!", resultado);
    }
    @Test
    void ingressarNoDesafioSucessoComOutroUsuario() {
        shakeTest.registrarUsuario("Savio", "savio@email", "testando", "123456");
        shakeTest.registrarUsuario("Kalebe", "kalebe@email", "testando", "123456");
        shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", inicio, fim, 2, "FREQUENCIA");
        
        shakeTest.ingressarNoDesafio("USR1", "senna12", "DSF1");
        shakeTest.ingressarNoDesafio("USR3", "123456", "DSF1");
        

        String resultado = shakeTest.ingressarNoDesafio("USR2", "123456", "DSF1");
        
        assertEquals("Você está participando!", resultado);
    }

    @Test
    void ingressarNoDesafioFalhaAntesDoInicio() {
        String dsfId = shakeTest.criarDesafio("USR1","senna12","OBI","TESTANDO","01-01-2050 00:00:00",fim,10,"FREQUENCIA");
        shakeTest.criarDesafio("USR1", "senna12", "OBI", "Treinamento", "04-02-2050 00:00:00", fim, 2, "FREQUENCIA");
        shakeTest.registrarUsuario("kalebim", "kalebe@", "alo", "acucar123");
        String resultado = shakeTest.ingressarNoDesafio("USR2", "acucar123", dsfId);
        assertEquals("Não foi possível entrar no desafio", resultado);
    }
    @Test
    void ingressarNoDesafioFalhaDepoisDoFim() {
        String dsfId = shakeTest.criarDesafio("USR1","senna12","OBI","TESTANDO","01-01-1900 00:00:00","01-01-2000 00:00:00",10,"FREQUENCIA"); // DSF2
        String resultado = shakeTest.ingressarNoDesafio("USR1", "senna12", dsfId);
        assertEquals("Não foi possível entrar no desafio", resultado);
    }
	@Test
	void finalizarDesafio() {
		String out = shakeTest.finalizarDesafio("USR1", "senna12","DSF1");
		assertEquals("Desafio finalizado.",out);
	}
	@Test
	void finalizarDesafioIdDiferente() {
		shakeTest.registrarUsuario("kalebe", "kalebe@", "kalebin", "senna12");
		String out = shakeTest.finalizarDesafio("USR2", "senna12","DSF1");
		assertEquals("Apenas o criador pode finalizar o desafio.",out);
	}
	@Test
	void finalizarDesafioJaFinalizado() {
		shakeTest.registrarUsuario("kalebe", "kalebe@", "kalebin", "senna12");
		String out = shakeTest.finalizarDesafio("USR2", "senna12","DSF1");
		assertEquals("Apenas o criador pode finalizar o desafio.",out);
	}
	@Test
	void finalizarDesafioUserInvalido() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,() -> {
			 shakeTest.finalizarDesafio("USR2", "senna12","DSF1");
		});
		assertEquals("Usuário não existe",err.getMessage());
	}
	@Test
	void finalizarDesafioUserNull() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,() -> {
			 shakeTest.finalizarDesafio(null, "senna12","DSF1");
		});
		assertEquals("idUsuario não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void finalizarDesafioUserVazio() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,() -> {
			 shakeTest.finalizarDesafio("", "senna12","DSF1");
		});
		assertEquals("idUsuario não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void finalizarDesafioUserSenhaErrada() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,() -> {
			 shakeTest.finalizarDesafio("USR1", "senna13","DSF1");
		});
		assertEquals("Usuário não autenticado.",err.getMessage());
	}
	@Test
	void finalizarDesafioInexistente() {
		ShakeItOffException err = assertThrows(ShakeItOffException.class,() -> {
			 shakeTest.finalizarDesafio("USR1", "senna12","DSF3");
		});
		assertEquals("Desafio não existe",err.getMessage());
	}
	// testes do ranking
	@Test
	void gerarRankingIdNulo() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio(null, "senna12", "DSF1");
		});
		assertEquals("idUsuario não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingIdVazio() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("", "senna12", "DSF1");
		});
		assertEquals("idUsuario não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingSenhaNull() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", null, "DSF1");
		});
		
		assertEquals("senha não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingSenhaVazio() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", "", "DSF1");
		});
		
		assertEquals("senha não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingSenhaUserFalho() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR10", "shashak", "DSF1");
		});
		
		assertEquals("Usuário não existe",err.getMessage());
	}
	@Test
	void gerarRankingSenhaUserSenhaErrada() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", "senna10", "DSF1");
		});
		
		assertEquals("Usuário não autenticado.",err.getMessage());
	}
	@Test
	void gerarRankingDesafioNull() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", "senna12", null);
		});
		
		assertEquals("idDesafio não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingDesafioVazio() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", "senna12", "");
		});
		
		assertEquals("idDesafio não pode ser vazio ou nulo.",err.getMessage());
	}
	@Test
	void gerarRankingDesafioInexistente() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		ShakeItOffException err = assertThrows(ShakeItOffException.class, () -> {
			shakeTest.calcularRankingDesafio("USR1", "senna12", "DSF6");
		});
		
		assertEquals("Desafio não existe",err.getMessage());
	}
	@Test
	void gerarRankingDesafioUsuarioForaDesafio() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		shakeTest.registrarUsuario("eliane", "trolrosa@gmail.com","tiro décimos de railton", "tirodecimos123");
		
		shakeTest.criarDesafio("USR1", "senna12", "Coliseu", "lutando pela mera mera no mi", inicio, fim, 10, "TEMPO");
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "CORRIDA", 10, "ALTA", 200, 100);
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "Tenis", 30, "ALTA", 200, 0);
		String out = shakeTest.calcularRankingDesafio("USR3", "tirodecimos123", "DSF1");
		
		
		assertEquals("Deve participar do desafio para calcular ranking.",out);
	}
	@Test
	void gerarRankingDesafio() {
		shakeTest.registrarUsuario("lucas", "lucas@gmail", "eu irrito natalia", "galante123");
		shakeTest.registrarUsuario("eliane", "trolrosa@gmail.com","tiro décimos de railton", "tirodecimos123");
		
		shakeTest.registrarCheckin("USR1", "senna12", "DSF1", horaAtual, "Natação", 20, "ALTA", 200, 100);
		
		shakeTest.registrarCheckin("USR2", "galante123", "DSF1", horaAtual, "MUSCULAÇÃO", 15, "ALTA", 100, 0);
		String out = shakeTest.calcularRankingDesafio("USR1", "senna12", "DSF1");
		
		assertEquals("Ranking calculado.",out);
	}
	
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
		
		//Testes de Relatório
		@Test
		void gerarRelatorioAutenticacaoFalha() {
			try {
				sys.gerarRelatorio("USR1", "sena12", "02-05-2006 00:00:00", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("Usuário não autenticado.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioIdUserNulo() {
			try {
				sys.gerarRelatorio(null, "sena12", "02-05-2006 00:00:00", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioIdUserVazio() {
			try {
				sys.gerarRelatorio("", "sena12", "02-05-2006 00:00:00", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("idUsuario não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioSenhaNula(){
			try {
				sys.gerarRelatorio("Renan", null, "02-05-2006 00:00:00", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioSenhaVazio(){
			try {
				sys.gerarRelatorio("USR1", "", "02-05-2006 00:00:00", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("senha não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioInicioNulo(){
			try {
				sys.gerarRelatorio("USR1", "senna12", null, "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("inicio não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioInicioVazio(){
			try {
				sys.gerarRelatorio("USR1", "senna12", "", "02-06-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("inicio não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioFimNulo(){
			try {
				sys.gerarRelatorio("USR1", "senna12", "02-05-2006 00:00:00", null);
			}catch(Exception e) {
				assertEquals("fim não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioFimVazio(){
			try {
				sys.gerarRelatorio("USR1", "senna12", "02-05-2006 00:00:00", "");
			}catch(Exception e) {
				assertEquals("fim não pode ser vazio ou nulo.", e.getMessage());
			}
		}
		@Test
		void gerarRelatorioNotInDesafio() {
			sys.gerarRelatorio("USR1", "senna12", "01-02-2006 00:00:00", "01-03-2006 00:00:00");
		}
		@Test
		void gerarRelatorioInicioDepoisDeFim() {
			try {
				sys.gerarRelatorio("USR1", "senna12", "02-05-2100 00:00:00", "02-05-2006 00:00:00");
			}catch(Exception e) {
				assertEquals("inicio não pode ser depois do fim", e.getMessage());
			}
		}
		@Test
		void gerarRelatoriosComCheckinsForaDoIntervalo() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().plusDays(1);
			LocalDateTime agora3 = LocalDateTime.now().plusDays(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF3");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "";
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr3));
		}
		@Test
		void gerarRelatoriosComCheckinsDentroDoIntervalo() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().minusDays(2);
			LocalDateTime agora3 = LocalDateTime.now().plusDays(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF3");
			sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
			sys.registrarCheckin("USR1", "senna12", "DSF3", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "Atividades de Renan\n" +
					"\n25-09-2025 Copa Cirrose CHK1 Ciclismo 5 ALTA\n" +
	                "25-09-2025 Copa Pistao CHK1 Ciclismo 5 ALTA\n" +
	                "25-09-2025 Copa do Mundo CHK1 Ciclismo 5 ALTA";;
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr3));
		}
		@Test
		void gerarRelatoriosComOrdemLexicograficaDiferente() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().minusMinutes(2);
			LocalDateTime agora3 = LocalDateTime.now().plusMinutes(1);
			LocalDateTime agora4 = LocalDateTime.now().plusMinutes(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        String dataHoraStr4 = agora4.format(formatter);
	        
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
	        sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
	        sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);
	        sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "Atividades de Renan\n" +
					"\n25-09-2025 Copa Cirrose CHK1 Ciclismo 5 ALTA\n" +
	                "25-09-2025 Copa Pistao CHK1 Ciclismo 5 ALTA";
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr4));
		}

		@Test
		void gerarRelatoriosComOrdemLexicografica() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().minusDays(2);
			LocalDateTime agora3 = LocalDateTime.now().plusDays(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    
		    String dataHoraStr1 = agora1.format(formatter);
		    String dataHoraStr2 = agora2.format(formatter);
		    String dataHoraStr3 = agora3.format(formatter);
		    
		    sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
		    sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
		    sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
		    sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "Atividades de Renan\n" +
					"\n25-09-2025 Copa Cirrose CHK1 Ciclismo 5 ALTA\n" +
		            "25-09-2025 Copa Pistao CHK1 Ciclismo 5 ALTA";
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr3));
		}
		
		@Test
		void gerarRelatoriosComHorasDiferentes() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().minusMinutes(2);
			LocalDateTime agora3 = LocalDateTime.now().plusMinutes(1);
			LocalDateTime agora4 = LocalDateTime.now().plusMinutes(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        String dataHoraStr4 = agora4.format(formatter);
		    
		    sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
		    sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);
		    sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "Atividades de Renan\n" +
					"\n25-09-2025 Copa Pistao CHK2 Ciclismo 5 ALTA\n" +
		            "25-09-2025 Copa Pistao CHK1 Ciclismo 5 ALTA";
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr4));
		}
		
		@Test
		void gerarRelatoriosComValoresLexicograficosEHorasDiferentes() {
			LocalDateTime agora1 = LocalDateTime.now();
			LocalDateTime agora2 = LocalDateTime.now().minusMinutes(2);
			LocalDateTime agora3 = LocalDateTime.now().plusMinutes(1);
			LocalDateTime agora4 = LocalDateTime.now().plusMinutes(2);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        
	        String dataHoraStr1 = agora1.format(formatter);
	        String dataHoraStr2 = agora2.format(formatter);
	        String dataHoraStr3 = agora3.format(formatter);
	        String dataHoraStr4 = agora4.format(formatter);
		    
		    sys.ingressarNoDesafio("USR1", "senna12", "DSF1");
		    sys.ingressarNoDesafio("USR1", "senna12", "DSF2");
		    sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);
		    sys.registrarCheckin("USR1", "senna12", "DSF1", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
		    sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr3, "Ciclismo", 5, "ALTA", 100, 10);
		    sys.registrarCheckin("USR1", "senna12", "DSF2", dataHoraStr1, "Ciclismo", 5, "ALTA", 100, 10);
					
			String expected = "Atividades de Renan\n\n" 
			+ "25-09-2025 Copa Cirrose CHK2 Ciclismo 5 ALTA\n"
		    + "25-09-2025 Copa Cirrose CHK1 Ciclismo 5 ALTA\n"
		    + "25-09-2025 Copa Pistao CHK2 Ciclismo 5 ALTA\n"
		    + "25-09-2025 Copa Pistao CHK1 Ciclismo 5 ALTA";
			assertEquals(expected, sys.gerarRelatorio("USR1", "senna12", dataHoraStr2, dataHoraStr4));
		}
	}
	
}
