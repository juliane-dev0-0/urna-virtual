package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urna.virtual.entity.Eleitor;

public interface EleitorRepository extends JpaRepository<Eleitor,Long> {
}
