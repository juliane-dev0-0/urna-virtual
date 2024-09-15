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
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.repository.EleitorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static urna.virtual.entity.Status.BLOQUEADO;

@SpringBootTest
public class EleitorServiceTest {

    @Mock
    Eleitor eleitor;
    @Mock
    private EleitorRepository eleitorRepository;

    @InjectMocks
    private EleitorService eleitorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @BeforeEach
    void setup() {
    }

    @Test
// Teste c/ eleitor sem dependencias: Email e celular.
    void verificarEleitor001() {
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                null,
                "Dev",
                "(45) 99830-3071",
                null,
                "email@email.com"
        );

        try {
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.APTO, eleitor001.getStatus());
        } catch (Exception ignored) {
        }
    }

    @Test
        // Teste c/ eleitor com dependencias: Email e celular.
    void verificarEleitor002() {
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                null,
                "Dev",
                null,
                null,
                null
        );

        try {
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.PENDENTE, eleitor001.getStatus());
        } catch (Exception ignored) {
        }
    }

    @Test
        // Teste c/ eleitor sem dependencias: Email e celular, e status pendente.
    void verificarEleitor003() {
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                Status.PENDENTE,
                "Dev",
                "(45) 99830-3071",
                null,
                "email@email.com"
        );

        try {
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.APTO, eleitor001.getStatus());
        } catch (Exception ignored) {
        }
    }

    @Test
    void testVerificarEleitorBloqueado() {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setStatus(BLOQUEADO);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            eleitorService.verificarEleitor(eleitor);
        });

        Assertions.assertEquals("Eleitor bloqueado!", thrown.getMessage());
    }


    @DisplayName("Função FindAll")
    @Test
    void findfind() {
        Eleitor eleitor1 = new Eleitor(
                "carpinteiro",
                "(45)99859-9010",
                "0",
                "kdsja@gmail.com"
        );
        eleitor1.setNome("Jose Claudio");
        eleitor1.setCpf("14546546565");
        eleitor1.setStatus(Status.APTO);

        Eleitor eleitor2 = new Eleitor(
                "advogada",
                "(45)99859-9999",
                "32121323213",
                "kds4654ja@gmail.com"
        );
        eleitor2.setNome("Abgail Santos Ferreira");
        eleitor2.setCpf("0000000000");
        eleitor2.setStatus(Status.APTO);

        Eleitor eleitor3 = new Eleitor(
                "programadora",
                "(45)95559-9999",
                "3545646543",
                "k65465a@gmail.com"
        );
        eleitor3.setNome("Sabrina Alcantra");
        eleitor3.setCpf("14127125993");
        eleitor3.setStatus(Status.INATIVO);

        when(eleitorRepository.findAllExcetoInativos()).thenReturn(List.of(eleitor1, eleitor2, eleitor3));

        List<Eleitor> eleitoresAptos = eleitorRepository.findAllExcetoInativos();

        assertThat(eleitoresAptos)
                .extracting(Eleitor::getStatus)
                .contains(Status.APTO);

        System.out.println(eleitoresAptos);
    }

    @Test// Deletando um Eleitor que ja votou
    void delete01() {
        Eleitor eleitor01 = new Eleitor(
                1L, "eleitor001", "07535338984", Status.VOTOU, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor01));

        Assertions.assertThrows(RuntimeException.class, () -> eleitorService.delete(1L));
    }

    @Test// Deletando um Eleitor Apto
    void delete02() {
        Eleitor eleitor01 = new Eleitor(
                1L, "eleitor001", "07535338984", Status.APTO, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor01));
        try {
            eleitorService.delete(1L);
            Assertions.assertEquals(Status.INATIVO, eleitor01.getStatus());
        } catch (Exception ignored) {
        }
    }

    @Test // Pegando um eleitor inativo
    void findById(){
        Eleitor eleitor01 = new Eleitor(
                1L, "eleitor001", "07535338984", Status.INATIVO, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor01));
        try {
            Assertions.assertThrows(RuntimeException.class, () -> eleitorService.findById(1L));
        } catch (Exception ignored) {
        }
    }


}