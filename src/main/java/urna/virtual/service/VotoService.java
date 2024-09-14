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

    @Autowired
    CandidatoRepository candidatoRepository;

    private Eleitor verificarVoto(Voto voto, Long eleitorId ) throws Exception {
        // Verificando vereador e prefeito
        if(voto.getPrefeito() == null || voto.getVereador() == null){
            throw new RuntimeException("Insira o prefeito e vereador para realizar o voto!");
        }

        Optional<Candidato> prefeito = candidatoRepository.findById(voto
                .getPrefeito()
                .getId());
        Optional<Candidato> vereador = candidatoRepository.findById(voto
                .getVereador()
                .getId());

        if(prefeito.isEmpty()){
            throw new RuntimeException("Prefeito de não encontrado no banco!");
        }else if(vereador.isEmpty()){
            throw new RuntimeException("Vereador de não encontrado no banco!");
        }

        if(prefeito.get().getFuncao() != 1) {
            throw new RuntimeException("O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!");
        }
        else if(vereador.get().getFuncao() != 2)  {
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
        eleitorRepository.save(eleitor);

        String hash = UUID.randomUUID().toString();
        voto.setDataHora(LocalDateTime.now());
        voto.setHash(hash);

        votoRepository.save(voto);
    }
    public Apuracao realizarApuracao(){
        List<Candidato> prefeitos = candidatoService.findAllPrefeitos();
        List<Candidato> vereadores = candidatoService.findAllVereadores();

        HashMap<Candidato, Long> prefeitosEVotos = new HashMap<>();
        HashMap<Candidato, Long> vereadoresEVotos = new HashMap<>();

        for(Candidato candidato : prefeitos){
            Long totalVotos = votoRepository.findVotosByPrefeito(candidato);
            prefeitosEVotos.put(candidato, totalVotos);
        }
        for(Candidato candidato : vereadores){
            Long totalVotos = votoRepository.findVotosByVereador(candidato);
            vereadoresEVotos.put(candidato, totalVotos);

        }

        return new Apuracao(prefeitosEVotos, vereadoresEVotos);
    }


}
