package urna.virtual.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.controller.EleitorController;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.service.EleitorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EleitorControllerTest {

    @Autowired
    EleitorController eleitorController;

    @Autowired
    EleitorService eleitorService;
    @MockBean
    EleitorRepository eleitorRepository;


    @Test // 200
    void update(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        when(eleitorRepository.existsById(10L)).thenReturn(true);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));
        ResponseEntity<?> response = eleitorController.update(10L,eleitor01);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void update2(){ // 400
        when(eleitorRepository.existsById(10L)).thenReturn(false);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = eleitorController.update(10L, new Eleitor());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test // 200
    void delete(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        when(eleitorRepository.existsById(10L)).thenReturn(true);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));

        ResponseEntity<?> response = eleitorController.delete(10L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test // 400
    void delete2(){
        when(eleitorRepository.existsById(10L)).thenReturn(false);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = eleitorController.delete(10L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test // 200
    void getById(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        when(eleitorRepository.existsById(10L)).thenReturn(true);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));

        ResponseEntity<?> response = eleitorController.getById(10L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test // 400
    void getById2(){
        when(eleitorRepository.existsById(10L)).thenReturn(false);
        when(eleitorRepository.findById(10L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = eleitorController.getById(10L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @DisplayName("Teste OK no getAll <(^^)>")
    @Test // 200
    void getAll(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Eleitor eleitor02 = new Eleitor(
                11L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );

        when(eleitorRepository.findAllExcetoInativos()).thenReturn(List.of(eleitor02, eleitor01));

        ResponseEntity<?> response = eleitorController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Teste BAD REQueST no getAll <(^^)>")
    @Test // 400 bad request
    void getAllBAD(){
        //vou deixar vazio para dar o erro (mudei o service para dar um exception quando estiver vazio)
        when(eleitorRepository.findAllExcetoInativos()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = eleitorController.getAll();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        System.out.println(response);
    }
}
