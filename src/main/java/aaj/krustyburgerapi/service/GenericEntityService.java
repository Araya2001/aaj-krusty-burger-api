package aaj.krustyburgerapi.service;


import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;

import java.util.List;
import java.util.Optional;

public interface GenericEntityService<T> {
    Boolean deleteAll(List<T> tList);

    Boolean delete(T t);

    List<T> findAll();

    Optional<T> findById(Long id);

    List<T> findAllBySpecs(List<SpecSearchCriteriaDTO> search);

    List<T> saveAll(List<T> tList);

    T save(T t);

    List<T> updateAll(List<T> tList);

    T update(T t);

    List<T> findAllBySpecsWithPages(List<SpecSearchCriteriaDTO> search, Integer page, Integer pageSize);

    Long countBy(List<SpecSearchCriteriaDTO> search);

    Boolean existsBy(List<SpecSearchCriteriaDTO> search);
}
