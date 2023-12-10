package dot.compta.backend.repositories.client;

import dot.compta.backend.models.client.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    List<ClientModel> findAllByDeletedFalse();

    List<ClientModel> findAllByCustomerIdAndDeletedFalse(int id);

    // TODO not unique possible exception
    Optional<ClientModel> findOneByCompanyNameIgnoreCase(String companyName);

    boolean existsByIdAndDeletedTrue(int id);
    
    @Modifying
	@Query("UPDATE ClientModel c SET c.deleted = true WHERE c.id = :id")
	void logicalDeleteById(@Param("id") int id);
}
