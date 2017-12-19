package factory.repository;


import factory.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factory, Long> {
    Factory findByName(String name);
}
