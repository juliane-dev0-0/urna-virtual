package urna.virtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import urna.virtual.entity.Candidato;
import urna.virtual.service.CandidatoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/candidato")
public class CandidatoController {

    @Autowired
    CandidatoService candidatoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Candidato candidato) {
        try {
            Candidato response = candidatoService.create(candidato);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(location).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Candidato candidato) {
        try {
            candidatoService.update(id, candidato);
            return ResponseEntity.ok("Candidato de  id: " + id + " foi atualizado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            candidatoService.delete(id);
            return ResponseEntity.ok("Candidato de id: " + id + " deletado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Candidato response = candidatoService.findById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Candidato> response = candidatoService.findAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
