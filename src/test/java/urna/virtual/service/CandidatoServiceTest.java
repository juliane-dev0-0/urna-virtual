package urna.virtual.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Autowired
    CandidatoService candidatoService;

    @Autowired
    CandidatoRepository candidatoRepository;

    @DisplayName("criando candidato")
    @Test
    void createCandidato01(){ // teste da função de criar candidato
      Candidato candidato1 = new Candidato(

              "14127125993",
              "1230",
              1,
              50
      );

        try{
            candidatoService.create(candidato1);
            Assertions.assertEquals(Status.APTO ,candidato1.getStatus() );
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
                529
        );
        candidato1.setStatus(Status.ATIVO);

        Candidato candidato2 = new Candidato(
                "1000000000",
                "234",
                1,
                800
        );
        candidato2.setStatus(Status.INATIVO);


        when(candidatoRepository.findAll()).thenReturn(List.of(candidato1, candidato2));
        List<Candidato> candidatosAtivos = candidatoService.findAll();

        // Verificar se retorna os ATIVOS SOmente
        assertThat(candidatosAtivos)//fznd a asserçao dos candidatos verificando os candidatos ativos
                .extracting(Candidato::getStatus)// extrai o status do candidato
                .containsOnly(Status.ATIVO); // aq mostra se está realmente ativo

    }
}