package urna.virtual.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;

@SpringBootTest
public class EleitorServiceTest {

    @Autowired
    EleitorService eleitorService;

    // @BeforeEach
    void setup(){}

    @Test// Teste c/ eleitor sem dependencias: Email e celular.
    void verificarEleitor001(){
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                null,
                "Dev",
                "(45) 99830-3071",
                null,
                "email@email.com"
        );

        try{
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.APTO ,eleitor001.getStatus() );
        }catch(Exception ignored){}
    }

    @Test // Teste c/ eleitor com dependencias: Email e celular.
    void verificarEleitor002(){
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                null,
                "Dev",
                null,
                null,
                null
        );

        try{
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.PENDENTE , eleitor001.getStatus() );
        }catch(Exception ignored){}
    }

    @Test // Teste c/ eleitor sem dependencias: Email e celular, e status pendente.
    void verificarEleitor003(){
        Eleitor eleitor001 = new Eleitor(
                "eleitor001",
                "07535338984",
                Status.PENDENTE,
                "Dev",
                "(45) 99830-3071",
                null,
                "email@email.com"
        );

        try{
            eleitorService.verificarEleitor(eleitor001);
            Assertions.assertEquals(Status.APTO , eleitor001.getStatus() );
        }catch(Exception ignored){}
    }
}
