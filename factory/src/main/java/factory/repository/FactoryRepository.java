package factory.repository;


import factory.model.Factory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FactoryRepository extends PagingAndSortingRepository<Factory, Long> {
    Factory findByName(String name);

  //  Page<Factory> search(SearchQuery searchQuery);
}
