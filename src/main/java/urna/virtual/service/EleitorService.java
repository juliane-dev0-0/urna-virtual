package urna.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urna.virtual.entity.Eleitor;
import urna.virtual.entity.Status;
import urna.virtual.repository.EleitorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class EleitorService {

    @Autowired
    EleitorRepository eleitorRepository;

    public void verificarEleitor(Eleitor eleitor) throws Exception {
        // Verificando se eleitor ja existe e está bloqueado
        eleitorRepository.findById(eleitor.getId())
                .ifPresent(opEleitor -> {
                    if (opEleitor.isBloqueado()) {
                        throw new RuntimeException("Eleitor bloqueado!");
                    }
                });

        // Verificar pendencias

        // Em caso de atualização, se o eleitor já estiver INATIVO,
        // o status de inativo deverá ser mantido.
        if(eleitor.getCpf() == null || eleitor.getEmail() == null && !eleitor.isInativo() && !eleitor.isBloqueado()){
            // Este status deverá ser atribuído quando o eleitor a ser salvo ou atualizado estiver: sem CPF ou sem endereço de e-mail cadastro.
            System.out.println(eleitor.isBloqueado());
            eleitor.setPendente();
        }
        // Verificar status
        else if(eleitor.isPendente() || eleitor.getStatus() == null ) {
            /* Este status deverá ser atribuído quando o eleitor a ser salvo ou atualizado:
               não tiver pendência de cadastro,
               não estiver inativo,
               não estiver bloqueado e
               não tiver votado. */
            eleitor.setApto();
        }
    }

    public Eleitor create(Eleitor eleitor) throws Exception {
        eleitor.setId(0L);
        verificarEleitor(eleitor);
        return eleitorRepository.save(eleitor);
    }

    public Eleitor update(Long id, Eleitor eleitor) throws Exception {
        if (!eleitorRepository.existsById(id)) {
            throw new NoSuchElementException("Eleitor de id " + id + " não encontrado!");
        }
        eleitor.setId(id);
        verificarEleitor(eleitor);
        return eleitorRepository.save(eleitor);
    }

    public void delete(Long id) throws Exception {
        Optional<Eleitor> response = eleitorRepository.findById(id);
        Eleitor eleitor = response
                .orElseThrow(() -> new NoSuchElementException("Eleitor de id " + id + " não encontrado!") );

        //O sistema jamais deverá deletar de fato o eleitor, apenas mudar o status para INATIVO.
        // Se o usuário já votou, o eleitor não poderá ser inativado.
        if(eleitor.getStatus() == Status.VOTOU ){
            throw new RuntimeException("Eleitor já votou. Não foi possível inativá-lo");
        }else{
            eleitor.setInativo();
            eleitorRepository.save(eleitor);
        }
    }

    public List<Eleitor> findAll() throws Exception {
        List<Eleitor> eleitores = eleitorRepository.findAllExcetoInativos();
        if (eleitores.isEmpty()) {
            throw new Exception("Nenhum eleitor ativo encontrado.");
        }
        return eleitores;
    }

    public Eleitor findById(Long id) throws Exception {
        Optional<Eleitor> response = eleitorRepository.findById(id);
        Eleitor eleitor = response
                .orElseThrow(() -> new NoSuchElementException("Eleitor de id " + id + " não encontrado!") );

        if(eleitor.getStatus() == Status.INATIVO ){
            throw new RuntimeException("Eleitor Inativo!");
        }else{
            return eleitor;
        }

    }
}
