package urna.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urna.virtual.entity.*;
import urna.virtual.repository.CandidatoRepository;
import urna.virtual.repository.EleitorRepository;
import urna.virtual.repository.VotoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class VotoService {

    @Autowired
    EleitorRepository eleitorRepository;

    @Autowired
    VotoRepository votoRepository;

    @Autowired
    CandidatoService candidatoService;

    private Eleitor verificarVoto(Voto voto, Long eleitorId ) throws Exception {
        // Verificando vereador e prefeito
        if(voto.getPrefeito().getFuncao() != 1) {
            throw new RuntimeException("O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!");
        }
        else if(voto.getVereador().getFuncao() != 2)  {
            throw new RuntimeException("O candidato escolhido para vereador é um candidato a prefeito. Refaça a requisição!");
        }

        // Verificando eleitor
        Optional<Eleitor> response = eleitorRepository.findById(eleitorId);
        if(response.isEmpty()){
            throw new RuntimeException("Eleitor de id "+ eleitorId + " não existe!");
        }
        else if(!response.get().isApto()){
            throw new RuntimeException("Eleitor de id "+ eleitorId + " não é apto para votar!");
        }
        return response.get();
    }
    public void votar(Voto voto , Long eleitorId) throws Exception{
        Eleitor eleitor = verificarVoto(voto, eleitorId);
        eleitor.setVotou();

        String hash = UUID.randomUUID().toString();
        voto.setDataHora(LocalDateTime.now());
        voto.setHash(hash);
    }
    public Apuracao realizarApuracao(){
        List<Candidato> prefeitos = candidatoService.findAllPrefeitos();
        List<Candidato> vereadores = candidatoService.findAllVereadores();

        HashMap<Candidato, Long> prefeitosEVotos = new HashMap<>();
        HashMap<Candidato, Long> vereadoresEVotos = new HashMap<>();

        for(Candidato candidato : prefeitos){
            Long totalVotos = votoRepository.findVotosByCandidato(candidato);
            prefeitosEVotos.put(candidato, totalVotos);
        }
        for(Candidato candidato : vereadores){
            Long totalVotos = votoRepository.findVotosByCandidato(candidato);
            vereadoresEVotos.put(candidato, totalVotos);
        }

        return new Apuracao(prefeitosEVotos, vereadoresEVotos);
    }

}
