package urna.virtual.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urna.virtual.entity.Apuracao;
import urna.virtual.entity.Voto;
import urna.virtual.service.VotoService;

@RestController
@RequestMapping("/api/voto")
public class VotoController {

    @Autowired
    VotoService votoService;

    // O método votar() deverá receber como argumento um objeto da classe Voto e o id do eleitor.
    @PostMapping("/votar")
    public ResponseEntity<?> votar(@RequestBody Voto voto , @RequestParam Long eleitorId){
        try{
           String response = votoService.votar(voto, eleitorId);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/apuracao")
    public ResponseEntity<?> realizarApuracao(){
        try{
            Apuracao response =  votoService.realizarApuracao();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
