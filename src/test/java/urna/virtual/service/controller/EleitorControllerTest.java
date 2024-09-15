package urna.virtual.service.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urna.virtual.controller.EleitorController;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.service.EleitorService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EleitorControllerTest {

    @Autowired
    EleitorController eleitorController;
    @MockBean
    EleitorRepository eleitorRepository;
    @MockBean
    EleitorService eleitorService;

    @Test
    void update(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.existsById(10L)).thenReturn(true);
        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));
        ResponseEntity<?> response = eleitorController.update(10L,eleitor01);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void delete(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.existsById(10L)).thenReturn(true);
        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));

        ResponseEntity<?> response = eleitorController.delete(10L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void delete2(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", Status.VOTOU, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.existsById(10L)).thenReturn(true);
        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));

        ResponseEntity<?> response = eleitorController.delete(10L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getById(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Mockito.when(eleitorRepository.existsById(10L)).thenReturn(true);
        Mockito.when(eleitorRepository.findById(10L)).thenReturn(Optional.of(eleitor01));

        ResponseEntity<?> response = eleitorController.getById(10L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getAll(){
        Eleitor eleitor01 = new Eleitor(
                10L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );
        Eleitor eleitor02 = new Eleitor(
                11L, "eleitor001", "07535338984", null, "Dev", "(45) 99830-3071", null, "email@email.com"
        );

        Mockito.when(eleitorRepository.findAllExcetoInativos()).thenReturn(List.of(eleitor02, eleitor01));

        ResponseEntity<?> response = eleitorController.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
