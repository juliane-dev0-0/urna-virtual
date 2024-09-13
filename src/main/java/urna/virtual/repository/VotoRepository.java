package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urna.virtual.entity.Candidato;
import urna.virtual.entity.Voto;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto,Long> {

    @Query("SELECT COUNT(v) from Voto v WHERE v.prefeito = :prefeito ")
    public Long findVotosByPrefeito(Candidato prefeito);

    @Query("SELECT COUNT(v) from Voto v WHERE v.vereador = :vereador ")
    public Long findVotosByVereador(Candidato vereador);
}
