package aaj.krustyburgerapi.repository;

import aaj.krustyburgerapi.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
}
