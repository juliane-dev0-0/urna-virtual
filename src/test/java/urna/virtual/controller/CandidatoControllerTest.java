package urna.virtual.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.controller.CandidatoController;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.repository.CandidatoRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CandidatoControllerTest {

    @Autowired
    CandidatoController candidatoController;

    @MockBean
    CandidatoRepository candidatoRepository;

    @Test // 400
    void update(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76",null, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(true);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        ResponseEntity<?> response = candidatoController.update(1L,candidato);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test // 200
    void update2(){
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(false);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = candidatoController.update(1L, new Candidato());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void delete(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76", Status.ATIVO, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(true);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        ResponseEntity<?> response = candidatoController.delete(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void delete2(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76", Status.INATIVO, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(true);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        ResponseEntity<?> response = candidatoController.delete(1L);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test // 200
    void getById(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76",null, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(true);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        ResponseEntity<?> response = candidatoController.getById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test // 400
    void getById2(){
        Mockito.when(candidatoRepository.existsById(1L)).thenReturn(false);
        Mockito.when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = candidatoController.getById(1L);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test // 200
    void getAll(){
        Candidato candidato = new Candidato(
                1L, "candidato-01", "595.749.300-76",null, "2222", 2, 0L
        );
        Candidato candidato2 = new Candidato(
                1L, "candidato-01", "595.749.300-76",null, "2222", 2, 0L
        );
        Mockito.when(candidatoRepository.findAll()).thenReturn(List.of(candidato,candidato2));

        ResponseEntity<?> response = candidatoController.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test // 400
    void getAll2(){
        Mockito.when(candidatoRepository.findAll()).thenReturn(null);

        ResponseEntity<?> response = candidatoController.getAll();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
