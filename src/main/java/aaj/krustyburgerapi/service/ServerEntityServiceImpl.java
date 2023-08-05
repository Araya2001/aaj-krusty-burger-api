package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.entity.Server;
import aaj.krustyburgerapi.repository.ServerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ServerEntityServiceImpl extends AbstractEntityService<Server> implements ServerEntityService {
    private final ServerRepository serverRepository;

    @Autowired
    public ServerEntityServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Boolean deleteAll(List<Server> servers) {
        try {
            serverRepository.deleteAllInBatch(servers);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(Server contact) {
        try {
            serverRepository.delete(contact);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Server> findAll() {
        try {
            return serverRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Server> findById(Long id) {
        try {
            return serverRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Server> findAllBySpecs(List<SpecSearchCriteriaDTO> search) {
        try {
            return serverRepository.findAll(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Server> saveAll(List<Server> servers) {
        try {
            return serverRepository.saveAllAndFlush(servers);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Server save(Server contact) {
        try {
            return serverRepository.save(contact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Server> updateAll(List<Server> servers) {
        try {
            return serverRepository.saveAllAndFlush(servers.stream().filter(entity -> entity.getId() != null).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Server update(Server contact) {
        try {
            if (contact.getId() != null) {
                return serverRepository.saveAndFlush(contact);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Server> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize) {
        try {
            return serverRepository.findAll(resolveSpec(search), PageRequest.of(page, pageSize)).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long countBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return serverRepository.count(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Boolean existsBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return serverRepository.exists(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
