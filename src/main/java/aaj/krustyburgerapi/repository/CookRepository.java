package aaj.krustyburgerapi.repository;

import aaj.krustyburgerapi.entity.Cook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CookRepository extends JpaRepository<Cook, Long>, JpaSpecificationExecutor<Cook> {
}
