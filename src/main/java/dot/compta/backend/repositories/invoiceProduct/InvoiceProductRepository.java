package dot.compta.backend.repositories.invoiceProduct;

import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProductModel, Integer>{
	
	List<InvoiceProductModel> findAllByInvoiceId(int invoiceId);
	
	List<InvoiceProductModel> findAllByInvoiceIdAndDeletedFalse(int invoiceId);
	
	@Modifying
	@Query("UPDATE InvoiceProductModel im SET im.deleted = true WHERE im.id = :id")
	void logicalDeleteById(@Param("id") int id);
	
	@Modifying
	@Query("UPDATE InvoiceProductModel im SET im.quantity = :quantity WHERE im.invoice.id = :invoiceId AND im.product.id = :productId")
	void updateQuantityByInvoiceIdAndProductId(
			@Param("quantity") int quantity, @Param("invoiceId") int invoiceId, @Param("productId") int productId);
	
}
