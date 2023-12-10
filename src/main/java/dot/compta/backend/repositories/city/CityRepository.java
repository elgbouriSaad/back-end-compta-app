package dot.compta.backend.repositories.city;

import dot.compta.backend.models.city.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {

    boolean existsByNameIgnoreCase(String name);

    // TODO not unique possible exception
    Optional<City> findOneByNameIgnoreCase(String name);

}
