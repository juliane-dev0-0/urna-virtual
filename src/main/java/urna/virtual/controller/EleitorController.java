package urna.virtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import urna.virtual.entity.Eleitor;
import urna.virtual.service.EleitorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eleitor")
public class EleitorController {
    
    @Autowired
    EleitorService eleitorService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Eleitor eleitor) {
        try {
            Eleitor response = eleitorService.create(eleitor);
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
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Eleitor eleitor) {
        try {
            eleitorService.update(id, eleitor);
            return ResponseEntity.ok("Eleitor de  id: " + id + " foi atualizado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            eleitorService.delete(id);
            return ResponseEntity.ok("Eleitor de id: " + id + " deletado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Eleitor response = eleitorService.findById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Eleitor> response = eleitorService.findAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
