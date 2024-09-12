package urna.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import urna.virtual.entity.Eleitor;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor,Long> {
    @Query("SELECT e FROM Eleitor e WHERE e.status != 1")
    public List<Eleitor> findAllExcetoInativos();
}
