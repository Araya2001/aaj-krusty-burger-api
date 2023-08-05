package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.entity.Customer;
import aaj.krustyburgerapi.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomerEntityServiceImpl extends AbstractEntityService<Customer> implements CustomerEntityService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerEntityServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Boolean deleteAll(List<Customer> customers) {
        try {
            customerRepository.deleteAllInBatch(customers);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(Customer contact) {
        try {
            customerRepository.delete(contact);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Customer> findAll() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try {
            return customerRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAllBySpecs(List<SpecSearchCriteriaDTO> search) {
        try {
            return customerRepository.findAll(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Customer> saveAll(List<Customer> customers) {
        try {
            return customerRepository.saveAllAndFlush(customers);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Customer save(Customer contact) {
        try {
            return customerRepository.save(contact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Customer> updateAll(List<Customer> customers) {
        try {
            return customerRepository.saveAllAndFlush(customers.stream().filter(entity -> entity.getId() != null).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Customer update(Customer contact) {
        try {
            if (contact.getId() != null) {
                return customerRepository.saveAndFlush(contact);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Customer> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize) {
        try {
            return customerRepository.findAll(resolveSpec(search), PageRequest.of(page, pageSize)).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long countBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return customerRepository.count(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Boolean existsBy(List<SpecSearchCriteriaDTO> search) {
        try {
            return customerRepository.exists(resolveSpec(search));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
