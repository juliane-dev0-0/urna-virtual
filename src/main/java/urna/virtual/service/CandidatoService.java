package urna.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;
import urna.virtual.repository.CandidatoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    CandidatoRepository candidatoRepository;


    public Candidato create(Candidato candidato) throws Exception {
        candidato.setStatus(Status.ATIVO); //foi definido para ficar ativo assim que salvar o candidato
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

        Optional<Candidato> response = candidatoRepository.findById(id);
        Candidato candidato = response
                .orElseThrow(() -> new NoSuchElementException("Candidato de id " + id + " não encontrado!"));

        if (candidato.getStatus() == Status.ATIVO) {
            candidato.setStatus(Status.INATIVO);
            candidatoRepository.save(candidato);
        } else {
            throw new RuntimeException("Não foi possível inativar o candidato, pois ele já está inativo.");
        }
    }

    public List<Candidato> findAll() throws Exception {
        return candidatoRepository.findAllByStatus(Status.ATIVO);
        //vai retornar todos os candidatos ativos
    }


    public Candidato findById(Long id) throws Exception {
        Optional<Candidato> response = candidatoRepository.findById(id);

        return response
                .orElseThrow(() -> new NoSuchElementException("Candidato de id " + id + " não encontrado!") );
    }
}