package urna.virtual.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;
import urna.virtual.entity.Voto;
import urna.virtual.repository.VotoRepository;
import urna.virtual.service.VotoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VotoControllerTest {

    @Mock
    VotoRepository votoRepository;

    @InjectMocks
    VotoService votoService;

    @Test // Teste para inserção dos dados do voto
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


        Voto voto1 = new Voto(
                5L,
                null,
                prefeito,
                vereador,
                null
        );


        assertEquals(10L, prefeito.getId());
        assertEquals(50L, vereador.getId());


        System.out.println(voto1);
        System.out.println("Prefeito ID: " + prefeito.getId());
        System.out.println("Vereador ID: " + vereador.getId());
    }

    @Test
    void realizarApuracao() {

    }
}
