package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.entity.Order;
import aaj.krustyburgerapi.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderEntityServiceImpl extends AbstractEntityService<Order> implements OrderEntityService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderEntityServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Boolean deleteAll(List<Order> orders) {
        try {
            orderRepository.deleteAllInBatch(orders);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(Order contact) {
        try {
            orderRepository.delete(contact);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAllBySpecs(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderRepository.findAll(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        try {
            return orderRepository.saveAllAndFlush(orders);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Order save(Order contact) {
        try {
            return orderRepository.save(contact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Order> updateAll(List<Order> orders) {
        try {
            return orderRepository.saveAllAndFlush(orders.stream().filter(entity -> entity.getId() != null).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Order update(Order contact) {
        try {
            if (contact.getId() != null) {
                return orderRepository.saveAndFlush(contact);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Order> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize) {
        try {
            return orderRepository.findAll(resolveSpec(search), PageRequest.of(page, pageSize)).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long countBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderRepository.count(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Boolean existsBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return orderRepository.exists(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
