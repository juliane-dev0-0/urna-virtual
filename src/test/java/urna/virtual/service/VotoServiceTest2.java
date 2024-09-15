package urna.virtual.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.entity.Voto;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;

import java.util.Optional;

@SpringBootTest
public class VotoServiceTest {

    @Autowired
    VotoService votoService;

    @MockBean
    EleitorRepository eleitorRepository;

    @MockBean
    CandidatoRepository candidatoRepository;

    @BeforeEach
    void setup(){
        Eleitor eleitorApto = new Eleitor(
                10L, "eleitor001", "07535338984", Status.APTO, "Dev", "(45) 99830-3071", null, "email@email.com");
        Eleitor eleitorPendente = new Eleitor(
                10L, "eleitor001", "07535338984", Status.PENDENTE,"Dev","(45) 99830-3071",null,"email@email.com");
        Eleitor eleitorBloqueado = new Eleitor(
                10L, "eleitor001", "07535338984", Status.BLOQUEADO,"Dev","(45) 99830-3071",null,"email@email.com");

        Candidato prefeitoAtivo = new Candidato(
                20L, "prefeito-1", "519.963.910-20", Status.ATIVO, "22", 1, 0L);
        Candidato vereadorAtivo = new Candidato(
                30L, "vereador-1", "595.749.300-76", Status.ATIVO, "2222", 2, 0L
        );
        Candidato prefeitoInativo = new Candidato(
                20L, "prefeito-1", "519.963.910-20", Status.INATIVO, "22", 1, 0L);
        Candidato vereadorInativo = new Candidato(
                30L, "vereador-1", "595.749.300-76", Status.INATIVO, "2222", 2, 0L
        );

        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitorApto));
        Mockito.when(eleitorRepository.findById(11L)).thenReturn(Optional.of(eleitorPendente));
        Mockito.when(eleitorRepository.findById(12L)).thenReturn(Optional.of(eleitorBloqueado));

        Mockito.when(candidatoRepository.findById(20L)).thenReturn(Optional.of(prefeitoAtivo));
        Mockito.when(candidatoRepository.findById(30L)).thenReturn(Optional.of(vereadorAtivo));
        Mockito.when(candidatoRepository.findById(21L)).thenReturn(Optional.of(prefeitoInativo));
        Mockito.when(candidatoRepository.findById(31L)).thenReturn(Optional.of(vereadorInativo));

    }

    @Test // Voto com Eleitor e Candidatos OK.
    void verificarVoto01()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertDoesNotThrow(() -> votoService.verificarVoto(voto,10L) );
    }
    @Test // Voto com Vereador no lugar de prefeito.
    void verificarVoto02()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, vereador, prefeito, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto, 10L));
    }
    @Test // Voto com Eleitor pendente e sendo bloqueado
    void verificarVoto04()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertThrows(IllegalStateException.class, () -> votoService.verificarVoto(voto, 11L));
    }

    @Test // Voto com Eleitor bloqueado
    void verificarVoto03()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(20L);
        vereador.setId(30L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto, 12L));
    }

    @Test // Voto com Candidatos inativos
    void verificarVoto05()   {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(21L);
        vereador.setId(31L);

        Voto voto = new Voto(0L, null, prefeito, vereador, null);

        Assertions.assertThrows(RuntimeException.class, () -> votoService.verificarVoto(voto, 10L));
    }

}