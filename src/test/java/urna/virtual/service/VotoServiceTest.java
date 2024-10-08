package urna.virtual.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.controller.VotoController;
import urna.virtual.entity.*;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.repository.VotoRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class VotoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private EleitorRepository eleitorRepository;

    @Mock
    private VotoController votoController;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private VotoService votoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setup(){
        Eleitor eleitorApto = new Eleitor(
                10L, "eleitor001", "07535338984", Status.APTO, "Dev", "(45) 99830-3071", null, "email@email.com");
        Eleitor eleitorPendente = new Eleitor(
                11L, "eleitor001", "07535338984", Status.PENDENTE,"Dev","(45) 99830-3071",null,"email@email.com");
        Eleitor eleitorBloqueado = new Eleitor(
                12L, "eleitor001", "07535338984", Status.BLOQUEADO,"Dev","(45) 99830-3071",null,"email@email.com");

        Candidato prefeitoAtivo = new Candidato(
                20L, "prefeito-1", "519.963.910-20", Status.ATIVO, "22", 1, 0L);
        Candidato vereadorAtivo = new Candidato(
                30L, "vereador-1", "595.749.300-76", Status.ATIVO, "2222", 2, 0L);

        Candidato prefeitoInativo = new Candidato(
                21L, "prefeito-1", "519.963.910-20", Status.INATIVO, "22", 1, 0L);
        Candidato vereadorInativo = new Candidato(
                31L, "vereador-1", "595.749.300-76", Status.INATIVO, "2222", 2, 0L);

        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitorApto));
        Mockito.when(eleitorRepository.findById(11L)).thenReturn(Optional.of(eleitorPendente));
        Mockito.when(eleitorRepository.findById(12L)).thenReturn(Optional.of(eleitorBloqueado));

        Mockito.when(candidatoRepository.findById(20L)).thenReturn(Optional.of(prefeitoAtivo));
        Mockito.when(candidatoRepository.findById(30L)).thenReturn(Optional.of(vereadorAtivo));
        Mockito.when(candidatoRepository.findById(21L)).thenReturn(Optional.of(prefeitoInativo));
        Mockito.when(candidatoRepository.findById(31L)).thenReturn(Optional.of(vereadorInativo));

        Mockito.when(candidatoRepository.findById(0L)).thenReturn(Optional.empty());

    }

    @DisplayName("Testar processo de votação")
    @Test
    void votar() throws Exception {

        Long eleitorId = 1L;
        Eleitor eleitor = new Eleitor();
        eleitor.setId(eleitorId);
        eleitor.setStatus(Status.APTO);

        Candidato prefeito = new Candidato();
        prefeito.setId(1L);
        prefeito.setFuncao(1); // prefeito
        prefeito.setNome("Silvio Santos");
        prefeito.setStatus(Status.ATIVO);

        Candidato vereador = new Candidato();
        vereador.setId(2L);
        vereador.setFuncao(2); // vereador
        vereador.setNome("Fátima Bernades");
        vereador.setStatus(Status.ATIVO);

        Voto voto = new Voto();
        voto.setPrefeito(prefeito);
        voto.setVereador(vereador);
        voto.setDataHora(LocalDateTime.now());

        when(eleitorRepository.findById(eleitorId)).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(prefeito.getId())).thenReturn(Optional.of(prefeito));
        when(candidatoRepository.findById(vereador.getId())).thenReturn(Optional.of(vereador));

        assertDoesNotThrow(() -> {
            votoService.votar(voto, eleitorId);
            System.out.println("Voto registrado com sucesso!");
        });

        verify(eleitorRepository, times(1)).save(eleitor);
        verify(votoRepository, times(1)).save(voto);

        System.out.println("Voto salvo: Prefeito = " + voto.getPrefeito().getNome() +
                ", Vereador = " + voto.getVereador().getNome() + ", Data = " + voto.getDataHora() + ", HASH = " + voto.getHash());
    }

    @Test // Voto com Eleitor e Candidatos OK.
    void verificarVoto001()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertDoesNotThrow(() -> votoService.verificarVoto(voto,10L) );
    }
    @Test // Voto com Vereador no lugar de prefeito e vice-versa
    void verificarVoto002()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, vereador, vereador, null);
        Voto voto2 = new Voto(0L, null, prefeito, prefeito, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto, 10L));
        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto2, 10L));

    }
    @Test // Voto com Eleitor pendente e sendo bloqueado
    void verificarVoto004()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertThrows(IllegalStateException.class, () -> votoService.verificarVoto(voto, 11L));
    }

    @Test // Voto com Eleitor bloqueado
    void verificarVoto003()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto, 12L));
    }

    @Test // Voto com Candidatos inativos
    void verificarVoto005()   {
        Candidato prefeitoInativo = new Candidato();
        Candidato vereadorInativo = new Candidato();
        Candidato prefeitoAtivo = new Candidato();
        Candidato vereadorAtivo = new Candidato();
        prefeitoAtivo.setId(20L);
        vereadorAtivo.setId(30L);
        prefeitoInativo.setId(21L);
        vereadorInativo.setId(31L);

        Voto votoPrefeitoInativo = new Voto(0L, null, prefeitoInativo, vereadorAtivo, null);
        Voto votoVereadorInativo = new Voto(0L, null, prefeitoAtivo, vereadorInativo, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(votoPrefeitoInativo, 10L));
        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(votoVereadorInativo, 10L));
    }
    @Test // Candidatos nulos
    void verificarVoto006(){
        Voto votoNulo = new Voto(0L, null, null,  null, null);
        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(votoNulo, 10L));
    }

    @Test // C/ candidatos nao achados no banco!
    void verificarVoto007() throws Exception{
        Candidato prefeitoNaoExistente = new Candidato();
        Candidato vereadorNaoExistente = new Candidato();
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeitoNaoExistente.setId(0L);
        vereadorNaoExistente.setId(0L);
        prefeito.setId(20L);
        vereador.setId(30L);


        Voto votoPrefeitoNaoExistente = new Voto(0L, null, prefeitoNaoExistente,  vereador, null);
        Voto votoVereadorNaoExistente = new Voto(0L, null, prefeito,  vereadorNaoExistente, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(votoPrefeitoNaoExistente, 10L));
        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(votoVereadorNaoExistente, 10L));

    }

}

