package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.entity.OrderItem;
import aaj.krustyburgerapi.repository.OrderItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderItemEntityServiceImpl extends AbstractEntityService<OrderItem> implements OrderItemEntityService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemEntityServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Boolean deleteAll(List<OrderItem> orderItems) {
        try {
            orderItemRepository.deleteAllInBatch(orderItems);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(OrderItem contact) {
        try {
            orderItemRepository.delete(contact);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<OrderItem> findAll() {
        try {
            return orderItemRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        try {
            return orderItemRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<OrderItem> findAllBySpecs(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderItemRepository.findAll(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        try {
            return orderItemRepository.saveAllAndFlush(orderItems);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public OrderItem save(OrderItem contact) {
        try {
            return orderItemRepository.save(contact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<OrderItem> updateAll(List<OrderItem> orderItems) {
        try {
            return orderItemRepository.saveAllAndFlush(orderItems.stream().filter(entity -> entity.getId() != null).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public OrderItem update(OrderItem contact) {
        try {
            if (contact.getId() != null) {
                return orderItemRepository.saveAndFlush(contact);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<OrderItem> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize) {
        try {
            return orderItemRepository.findAll(resolveSpec(search), PageRequest.of(page, pageSize)).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long countBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderItemRepository.count(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Boolean existsBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderItemRepository.exists(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
