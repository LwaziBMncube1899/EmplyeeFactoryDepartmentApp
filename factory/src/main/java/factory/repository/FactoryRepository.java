package factory.repository;


import factory.model.Factory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactoryRepository extends PagingAndSortingRepository<Factory, Long> {
    Factory findByName(String name);


    @Query("SELECT f FROM Factory f WHERE f.name LIKE %:searchTerm%")
    List<Factory> searchWithJPQLQuery(@Param("searchTerm")String searchTerm);
}
