package dot.compta.backend.repositories.accountant;

import dot.compta.backend.models.accountant.AccountantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountantRepository extends JpaRepository<AccountantModel, Integer> {
    List<AccountantModel> findAllByDeletedFalse();

    // TODO not unique possible exception
    Optional<AccountantModel> findOneByCompanyNameIgnoreCase(String companyName);

    boolean existsByIdAndDeletedTrue(int id);
    
    @Modifying
	@Query("UPDATE AccountantModel a SET a.deleted = true WHERE a.id = :id")
	void logicalDeleteById(@Param("id") int id);
}
