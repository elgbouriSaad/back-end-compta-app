package dot.compta.backend.repositories.address;

import dot.compta.backend.models.address.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressModel, Integer> {
}
