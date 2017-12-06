package com.factory.service;

import com.factory.model.Factory;
import com.factory.repository.FactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FactoryService {

    private final FactoryRepository factoryRepository;

    @Autowired
    public FactoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public void addFactory(Factory factory){
        factoryRepository.save(factory);
    }
    public void deleteFactory(Long id){
        factoryRepository.delete(id);
    }
    public Factory getFactoryById(Long id){
        return factoryRepository.findOne(id);
    }
    public List<Factory> getAll(){
        return factoryRepository.findAll();
    }

}
