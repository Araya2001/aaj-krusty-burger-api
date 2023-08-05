package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.entity.Cook;
import aaj.krustyburgerapi.repository.CookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CookEntityServiceImpl extends AbstractEntityService<Cook> implements CookEntityService {
    private final CookRepository cookRepository;

    @Autowired
    public CookEntityServiceImpl(CookRepository cookRepository) {
        this.cookRepository = cookRepository;
    }

    @Override
    public Boolean deleteAll(List<Cook> cooks) {
        try {
            cookRepository.deleteAllInBatch(cooks);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(Cook contact) {
        try {
            cookRepository.delete(contact);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Cook> findAll() {
        try {
            return cookRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Cook> findById(Long id) {
        try {
            return cookRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Cook> findAllBySpecs(List<SpecSearchCriteriaDTO> search) {
        try {
            return cookRepository.findAll(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cook> saveAll(List<Cook> cooks) {
        try {
            return cookRepository.saveAllAndFlush(cooks);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Cook save(Cook contact) {
        try {
            return cookRepository.save(contact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cook> updateAll(List<Cook> cooks) {
        try {
            return cookRepository.saveAllAndFlush(cooks.stream().filter(entity -> entity.getId() != null).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Cook update(Cook contact) {
        try {
            if (contact.getId() != null) {
                return cookRepository.saveAndFlush(contact);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cook> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize) {
        try {
            return cookRepository.findAll(resolveSpec(search), PageRequest.of(page, pageSize)).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long countBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return cookRepository.count(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Boolean existsBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return cookRepository.exists(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
