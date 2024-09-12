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

    @PostMapping
    public ResponseEntity<?> votar(@RequestBody Voto voto , @RequestParam Long eleitorId){
        try{
           votoService.votar(voto, eleitorId);
            return ResponseEntity.ok("Voto contabilizado!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> realizarApuracao(){
        try{
            Apuracao response =  votoService.realizarApuracao();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
