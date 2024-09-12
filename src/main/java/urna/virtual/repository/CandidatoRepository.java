package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato ,Long> {
    List<Candidato> findAllByStatus(Status status);

    List<Candidato> findAllByFuncao(int funcao);
}
