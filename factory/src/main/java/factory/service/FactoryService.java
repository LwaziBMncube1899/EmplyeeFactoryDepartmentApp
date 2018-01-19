package factory.service;

import factory.model.Factory;
import factory.repository.FactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FactoryService {

    private static final int PAGE_SIZE = 1;
    private final FactoryRepository factoryRepository;

    @Autowired
    public FactoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }



    public Factory findFactoryById(Long id) {
        return factoryRepository.findOne(id);
    }

    public Factory findByName(String name) {
        return factoryRepository.findByName(name);
    }

    public Page<Factory> findAll(Pageable pageable) {

        return    factoryRepository.findAll(pageable);
    }


    public void deleteAllFactores() {
        factoryRepository.deleteAll();
    }
    public void deleteFactoryById(Long id) {
        factoryRepository.delete(id);
    }

    public void saveFactory(Factory factory) {
        factoryRepository.save(factory);
    }

    public void updateFactory(Factory factory) {
        saveFactory(factory);
    }

    public boolean isFactoryExist(Factory factory){
        return findByName(factory.getName()) !=null;
    }

}
