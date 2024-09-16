package urna.virtual.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.entity.Voto;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.service.EleitorService;
import urna.virtual.service.VotoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VotoControllerTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private EleitorRepository eleitorRepository;

//    @Mock
//    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test // Teste STATUS OK
    void votar() {
        Candidato prefeito = new Candidato(
                "14127125993",
                "1",
                1,
                536L
        );
        prefeito.setNome("Jose Aparecido");
        prefeito.setStatus(Status.ATIVO);
        prefeito.setId(10L);

        Candidato vereador = new Candidato(
                "11223377889",
                "12",
                2,
                3322L
        );
        vereador.setNome("Claudio Marçola");
        vereador.setStatus(Status.ATIVO);
        vereador.setId(50L);

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
                vereador,
                null
        );

        when(eleitorRepository.findById(2L)).thenReturn(Optional.of(eleitor1));
        when(candidatoRepository.findById(10L)).thenReturn(Optional.of(prefeito));
        when(candidatoRepository.findById(50L)).thenReturn(Optional.of(vereador));


        ResponseEntity<?> response = votoController.votar(voto1, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());



        System.out.println(response);
        System.out.println(voto1);
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
