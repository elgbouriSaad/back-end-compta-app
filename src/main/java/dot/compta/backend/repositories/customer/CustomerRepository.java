package dot.compta.backend.repositories.customer;

import dot.compta.backend.models.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
    List<CustomerModel> findAllByDeletedFalse();

    List<CustomerModel> findAllByAccountantIdAndDeletedFalse(int id);

    // TODO not unique possible exception
    Optional<CustomerModel> findOneByCompanyNameIgnoreCase(String companyName);

    boolean existsByIdAndDeletedTrue(int id);
    
    @Modifying
	@Query("UPDATE CustomerModel cu SET cu.deleted = true WHERE cu.id = :id")
	void logicalDeleteById(@Param("id") int id);
}
