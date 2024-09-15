package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Status;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato ,Long> {
    List<Candidato> findAllByStatus(Status status);

    @Query("SELECT c FROM Candidato c WHERE c.funcao = :funcao AND c.status != 1")
    List<Candidato> findAllByFuncao(int funcao);
}
