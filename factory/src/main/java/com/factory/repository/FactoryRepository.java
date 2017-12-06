package com.factory.repository;

import com.factory.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factory, Long>{
}
