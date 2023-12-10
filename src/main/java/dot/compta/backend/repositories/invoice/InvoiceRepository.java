package dot.compta.backend.repositories.invoice;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.models.invoice.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceModel,Integer>{
	
	List<InvoiceModel> findAllByDeletedFalse();
	
	List<InvoiceModel> findAllByCustomerIdAndDeletedFalse(int customerId);

	List<InvoiceModel> findAllByClientIdAndDeletedFalse(int customerId);

	List<InvoiceModel> findAllByClientIdAndStatusAndDeletedFalse(int customerId, InvoiceStatus status);
	
	boolean existsByIdAndDeletedTrue(int id);
	
	boolean existsByIdAndStatus(int id, InvoiceStatus status);
	
	boolean existsByQuotationId(int id);
	
    @Modifying
    @Query("UPDATE InvoiceModel i SET i.status = :status WHERE i.id = :invoiceId")
    void updateStatusById(@Param("status") InvoiceStatus status, @Param("invoiceId") int invoiceId);
    
    @Modifying
	@Query("UPDATE InvoiceModel i SET i.deleted = true WHERE i.id = :id")
	void logicalDeleteById(@Param("id") int id);

}
