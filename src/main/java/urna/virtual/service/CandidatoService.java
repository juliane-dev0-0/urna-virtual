package urna.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urna.virtual.entity.Candidato;
import urna.virtual.repository.CandidatoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    CandidatoRepository candidatoRepository;

    public Candidato create(Candidato candidato) throws Exception {
        return candidatoRepository.save(candidato);
    }

    public Candidato update(Long id, Candidato candidato) throws Exception {
        if (!candidatoRepository.existsById(id)) {
            throw new NoSuchElementException("Candidato de id " + id + " não encontrado!");
        }

        candidato.setId(id);
        return candidatoRepository.save(candidato);
    }

    public void delete(Long id) throws Exception {
        if (!candidatoRepository.existsById(id)) {
            throw new NoSuchElementException("Candidato de id " + id + " não encontrado!");
        }
        candidatoRepository.deleteById(id);
    }

    public List<Candidato> findAll() throws Exception {
        return candidatoRepository.findAll();
    }

    public Candidato findById(Long id) throws Exception {
        Optional<Candidato> response = candidatoRepository.findById(id);

        return response
                .orElseThrow(() -> new NoSuchElementException("Candidato de id " + id + " não encontrado!") );
    }
}