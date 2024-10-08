package urna.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;
import urna.virtual.repository.CandidatoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    CandidatoRepository candidatoRepository;

    public void validarCandidato(Candidato candidato) throws Exception{
        if(candidato.getFuncao() != 1 && candidato.getFuncao() != 2){
            throw new RuntimeException("Função de candidato invalido! Tente 1 para prefeito e 2 para vereador");
        }
    }

    public Candidato create(Candidato candidato) throws Exception {
        candidato.setStatus(Status.ATIVO); //foi definido para ficar ativo assim que salvar o candidato
        validarCandidato(candidato);
        return candidatoRepository.save(candidato);
    }

    public Candidato update(Long id, Candidato candidato) throws Exception {
        if (!candidatoRepository.existsById(id)) {
            throw new NoSuchElementException("Candidato de id " + id + " não encontrado!");
        }
        validarCandidato(candidato);
        candidato.setId(id);
        candidato.setStatus(Status.ATIVO);
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

    public List<Candidato> findAll() {
        return candidatoRepository.findAll().stream() //stream transforma os elementos em algu funcional, filtro,  mapeamento essas coisas
                .filter(c -> c.getStatus() == Status.ATIVO) // mandando um fltro aqui, ele pega o candidato e filtra pelo
                // status pq no findall só pode aparecer candidatos ativos
                .collect(Collectors.toList());//o collect vai converter o strem filtrado em uma lista <('-')>
    }


    public Candidato findById(Long id) throws Exception {
        Optional<Candidato> response = candidatoRepository.findById(id);

        return response
                .orElseThrow(() -> new NoSuchElementException("Candidato de id " + id + " não encontrado!") );
    }

    public List<Candidato> findAllPrefeitos(){
        return candidatoRepository.findAllByFuncao(1);
    }
    public List<Candidato> findAllVereadores(){
        return candidatoRepository.findAllByFuncao(2);
    }
}