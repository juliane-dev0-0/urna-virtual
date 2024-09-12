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

    public void verificarEleitor(Eleitor eleitor){
        // Verificar pendencias

        // Em caso de atualização, se o eleitor já estiver INATIVO,
        // o status de inativo deverá ser mantido.
        if(eleitor.getCpf() == null || eleitor.getEmail() == null && !eleitor.isInativo() ){
            // Este status deverá ser atribuído quando o eleitor a ser salvo ou atualizado estiver: sem CPF ou sem endereço de e-mail cadastro.
            eleitor.setPendente();
        }
        // Verificar status
        else if(eleitor.isPendente() || eleitor.getStatus() == null){
            /* Este status deverá ser atribuído quando o eleitor a ser salvo ou atualizado:
               não tiver pendência de cadastro,
               não estiver inativo,
               não estiver bloqueado e
                não tiver votado. */
            eleitor.setApto();
        }
    }

    public Eleitor create(Eleitor eleitor) throws Exception {
        verificarEleitor(eleitor);
        return eleitorRepository.save(eleitor);
    }

    public Eleitor update(Long id, Eleitor eleitor) throws Exception {
        if (!eleitorRepository.existsById(id)) {
            throw new NoSuchElementException("Eleitor de id " + id + " não encontrado!");
        }

        verificarEleitor(eleitor);
        eleitor.setId(id);
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

        // eleitorRepository.deleteById(id);
    }

    public List<Eleitor> findAll() throws Exception {
        return eleitorRepository.findAllExcetoInativos();
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
