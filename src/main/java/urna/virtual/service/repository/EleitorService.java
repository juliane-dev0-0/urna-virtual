package urna.virtual.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urna.virtual.entity.Eleitor;

public interface EleitorService extends JpaRepository<Eleitor,Long> {
}
