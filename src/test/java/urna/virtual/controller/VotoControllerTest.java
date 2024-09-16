package urna.virtual.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.entity.Voto;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.repository.VotoRepository;
import urna.virtual.service.EleitorService;
import urna.virtual.service.VotoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class VotoControllerTest {

    @MockBean
     CandidatoRepository candidatoRepository;

    @MockBean
     EleitorRepository eleitorRepository;

    @MockBean
    VotoRepository votoRepository;

    @Autowired
     VotoController votoController;

    @BeforeEach
    void setup(){
        Candidato prefeito = new Candidato(
                10L,
                "Jose Aparecido",
                "14127125993",
                Status.ATIVO,
                "1",
                1,
                536L
        );
        Candidato vereador = new Candidato(
                20L,
                "Claudio Marçola",
                "11223377889",
                Status.ATIVO,
                "12",
                2,
                3322L
        );

        Eleitor eleitor1 = new Eleitor(
                2L,
                "Maria Gabriela",
                "07535338984",
                Status.APTO,
                "Padeira",
                "(45) 9999-8989",
                null,
                "email@email.com"
        );



        when(eleitorRepository.findById(2L)).thenReturn(Optional.of(eleitor1));
        when(candidatoRepository.findById(10L)).thenReturn(Optional.of(prefeito));
        when(candidatoRepository.findById(20L)).thenReturn(Optional.of(vereador));


    }
    @Test // Teste STATUS OK
    void votar() {
        Candidato prefeito = new Candidato();
        Candidato vereador = new Candidato();
        prefeito.setId(10L);
        vereador.setId(20L);
        Voto voto = new Voto(
                5L,
                null,
                prefeito,
                vereador,
                null
        );
        ResponseEntity<?> response = votoController.votar(voto, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        System.out.println(response);
        System.out.println("Prefeito ID: " + prefeito.getId());
        System.out.println("Vereador ID: " + vereador.getId());
    }

    @Test // test BAD REQUEST
    void votarBadRequest() {
        Candidato prefeito = new Candidato(
                "14127125993",
                "1",
                1,
                536L
        );
        prefeito.setNome("Jose Aparecido");
        prefeito.setStatus(Status.ATIVO);
        prefeito.setId(10L);

        // Não cria o vereador para simular um erro

        Eleitor eleitor1 = new Eleitor(
                2L,
                "Maria Gabriela",
                "07535338984",
                null,
                "Padeira",
                "(45) 9999-8989",
                null,
                "email@email.com"
        );

        Voto voto1 = new Voto(
                5L,
                null,
                prefeito,
                null,
                null
        );


        when(eleitorRepository.findById(2L)).thenReturn(Optional.of(eleitor1));
        when(candidatoRepository.findById(10L)).thenReturn(Optional.of(prefeito));


        ResponseEntity<?> response = votoController.votar(voto1, 2L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        System.out.println(response.getStatusCode());
    }


    @Test
    void realizarApuracao() {

    }
}
