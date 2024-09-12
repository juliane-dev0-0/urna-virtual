package urna.virtual.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urna.virtual.entity.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato ,Long> {
}
