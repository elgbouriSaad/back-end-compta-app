package dot.compta.backend.repositories.country;

import dot.compta.backend.models.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByNameIgnoreCase(String name);

    // TODO not unique possible exception
    Optional<Country> findOneByNameIgnoreCase(String name);
}
