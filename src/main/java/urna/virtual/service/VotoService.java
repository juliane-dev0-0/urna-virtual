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
        }
        else if(vereador.isEmpty()){
            throw new RuntimeException("Vereador de não encontrado no banco!");
        }

        if(prefeito.get().getFuncao() != 1) {
            throw new RuntimeException("O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!");
        }
        else if(vereador.get().getFuncao() != 2)  {
            throw new RuntimeException("O candidato escolhido para vereador é um candidato a prefeito. Refaça a requisição!");
        }

        if(!prefeito.get().isAtivo()){
            throw new RuntimeException("O candidato escolhido para prefeito está inativo. Refaça a requisição!");
        }
        else if(!vereador.get().isAtivo()){
            throw new RuntimeException("O candidato escolhido para vereador está inativo. Refaça a requisição!");
        }

        // Verificando eleitor
        Optional<Eleitor> eleitor = eleitorRepository.findById(eleitorId);
        if(eleitor.isEmpty()){
            throw new RuntimeException("Eleitor de id "+ eleitorId + " não existe!");
        }
        else if(eleitor.get().isPendente()){
            // Este status deverá ser atribuído quando um eleitor com status PENDENTE tentar votar
            eleitor.get().setBloqueado();
            eleitorRepository.save(eleitor.get());
            throw new RuntimeException("Usuário com cadastro pendente tentou votar. O usuário será bloqueado!");
        }
        // Somente eleitores com status APTO poderão votar.
        else if(!eleitor.get().isApto()){
            throw new RuntimeException("Eleitor de id "+ eleitorId + " não é apto para votar!");
        }


        return eleitor.get();
    }

    public String votar(Voto voto , Long eleitorId) throws Exception{
        Eleitor eleitor = verificarVoto(voto, eleitorId);
        eleitor.setVotou();
        eleitorRepository.save(eleitor);

        String hash = UUID.randomUUID().toString();
        voto.setDataHora(LocalDateTime.now());
        voto.setHash(hash);
        votoRepository.save(voto);

        // Se tudo der certo, o atributo data e hora de votação deverá ser setado no objeto,
        // o hash de votação deverá ser gerado e setado no objeto,
        // o objeto deverá ser persistido
        // e o hash deverá ser retornado.

        return voto.getHash();
    }

    public Apuracao realizarApuracao(){
        List<Candidato> prefeitos = candidatoService.findAllPrefeitos();
        List<Candidato> vereadores = candidatoService.findAllVereadores();
        Long totalVotos = votoRepository.count();

        for(Candidato prefeito : prefeitos){
            Long votos = votoRepository.findVotosByPrefeito(prefeito);
            prefeito.setVotosApurados(votos);
        }
        for(Candidato vereador : vereadores){
            Long votos = votoRepository.findVotosByVereador(vereador);
            vereador.setVotosApurados(votos);
        }

        Collections.sort(prefeitos, (list1, list2) -> Long.compare(list2.getVotosApurados(), list1.
                getVotosApurados()));

        Collections.sort(vereadores, (list1, list2) -> Long.compare(list2.getVotosApurados(), list1.
                getVotosApurados()));

        return new Apuracao(vereadores, prefeitos , totalVotos);
    }

}
