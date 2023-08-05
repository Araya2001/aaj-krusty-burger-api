package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.SpecSearchCriteriaDTO;
import aaj.krustyburgerapi.jpa.specification.GenericSpecificationBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Log4j2
public abstract class AbstractEntityService<T> {
    protected Specification<T> resolveSpec(List<SpecSearchCriteriaDTO> searchParameters) {
        try {
            GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
            return builder.buildFromParams(searchParameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
