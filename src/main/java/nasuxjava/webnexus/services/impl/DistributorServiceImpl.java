package nasuxjava.webnexus.services.impl;

import nasuxjava.webnexus.entity.Distributor;
import nasuxjava.webnexus.repository.DistributorRepository;
import nasuxjava.webnexus.services.DistributorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistributorServiceImpl implements DistributorService {

    private final DistributorRepository distributorRepository;

    @Autowired
    public DistributorServiceImpl(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    @Override
    public List<Distributor> getAllDistributors() {
        return distributorRepository.findAll();
    }

    @Override
    public String getNameDistributor(Long id) {
        Distributor distributor = distributorRepository.findById(id).orElse(null);
        return distributor.getName();
    }

    @Override
    public Distributor getDistributorById(Long id) {
        Optional<Distributor> distributor = distributorRepository.findById(id);
        return distributor.orElse(null);
    }

    @Override
    public Distributor getDistributorByName(String name) {
        Distributor distributor = distributorRepository.findByName(name);
        return distributor;
    }

    @Override
    public Distributor saveDistributor(Distributor distributor) {
        return distributorRepository.save(distributor);
    }

    @Override
    public void deleteDistributor(Long id) {
        distributorRepository.deleteById(id);
    }
}