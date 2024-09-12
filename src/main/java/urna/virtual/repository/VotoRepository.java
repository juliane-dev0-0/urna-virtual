package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Voto;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto,Long> {

    @Query("SELECT COUNT(*) from Voto v WHERE v.candidato = :candidato ")
    public Long findVotosByCandidato(Candidato candidato);
}
