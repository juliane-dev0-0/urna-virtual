package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urna.virtual.entity.Voto;

public interface VotoRepository extends JpaRepository<Voto,Long> {
}
