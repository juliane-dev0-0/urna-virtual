package urna.virtual.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import urna.virtual.entity.*;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.repository.VotoRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class VotoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private EleitorRepository eleitorRepository;

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

        Candidato vereador = new Candidato();
        vereador.setId(2L);
        vereador.setFuncao(2); // vereador
        vereador.setNome("Fátima Bernades");

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


}

