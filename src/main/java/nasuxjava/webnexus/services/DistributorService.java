package nasuxjava.webnexus.services;

import nasuxjava.webnexus.entity.Distributor;
import java.util.List;

public interface DistributorService {
    List<Distributor> getAllDistributors();

    String getNameDistributor(Long id);

    Distributor getDistributorById(Long id);

    Distributor getDistributorByName(String name);

    Distributor saveDistributor(Distributor distributor);

    void deleteDistributor(Long id);
}