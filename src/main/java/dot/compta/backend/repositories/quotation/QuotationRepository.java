package dot.compta.backend.repositories.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.models.quotation.QuotationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuotationRepository extends JpaRepository<QuotationModel, Integer> {

    List<QuotationModel> findAllByDeletedFalse();
  
    List<QuotationModel> findAllByCustomerIdAndDeletedFalse(int customerId);

    List<QuotationModel> findAllByClientIdAndDeletedFalse(int clientId);
    
    List<QuotationModel> findAllByClientIdAndStatusAndDeletedFalse(int clientId, QuotationStatus status);

    boolean existsByIdAndDeletedTrue(int id);

    boolean existsByIdAndStatus(int id, QuotationStatus status);

    @Modifying
    @Query("UPDATE QuotationModel q SET q.status = :status WHERE q.id = :quotationId")
    void updateStatusById(@Param("status") QuotationStatus status, @Param("quotationId") int quotationId);
    
    @Modifying
	@Query("UPDATE QuotationModel q SET q.deleted = true WHERE q.id = :id")
	void logicalDeleteById(@Param("id") int id);
	
}
