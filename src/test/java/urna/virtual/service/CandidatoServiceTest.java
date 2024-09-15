package urna.virtual.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;
import urna.virtual.repository.CandidatoRepository;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

class CandidatoServiceTest {

    @Autowired
    Candidato candidato;

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("criando candidato")
    @Test
    void createCandidato01(){ // teste da função de criar candidato
      Candidato candidato1 = new Candidato(

              "14127125993",
              "1230",
              1,
              50L
      );
      candidato1.setNome("jose claudio");


        try{
            candidatoService.create(candidato1);

            Assertions.assertEquals(Status.ATIVO ,candidato1.getStatus() );
            System.out.println(candidato1);
        }catch(Exception ignored){

        }

    }

    @DisplayName("findAll ATIVOS")
    @Test
    void findAllAtivos() throws Exception {
        // Criar candidatos com status
        Candidato candidato1 = new Candidato(
                "14127125993",
                "123",
                2,
                529L
        );
        candidato1.setNome("fulano");
        candidato1.setStatus(Status.ATIVO);

        Candidato candidato2 = new Candidato(
                "1000000000",
                "234",
                1,
                800L
        );
        candidato2.setStatus(Status.INATIVO);


        when(candidatoRepository.findAll()).thenReturn(List.of(candidato1, candidato2));

        List<Candidato> candidatosAtivos = candidatoService.findAll();
        System.out.println(candidatosAtivos);

        assertThat(candidatosAtivos)
                .extracting(Candidato::getStatus)
                .containsOnly(Status.ATIVO);
    }
    @Test// Enviando candidato com Funcao invalida
    void verificarCandidato01(){
        Candidato candidatoInvalido = new Candidato(
                null, "candidato-01", "595.749.300-76", null, "2222", 3, null
        );

        Assertions.assertThrows(RuntimeException.class, () ->candidatoService.validarCandidato(candidatoInvalido));
    }

    @Test // Deletando um Candidato ativo
    void delete01(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76", Status.ATIVO, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        try {
            candidatoService.delete(1L);
            Assertions.assertEquals(Status.INATIVO, candidato.getStatus());
        } catch (Exception ignored) {}
    }

    @Test // Deletando um Candidato já inativo
    void delete02(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76", Status.INATIVO, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));
        Assertions.assertThrows(RuntimeException.class, () -> candidatoService.delete(1L));

    }
}