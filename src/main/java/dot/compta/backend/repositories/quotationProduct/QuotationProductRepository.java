package dot.compta.backend.repositories.quotationProduct;

import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuotationProductRepository extends JpaRepository<QuotationProductModel, Integer> {

	void deleteById(int id);

	List<QuotationProductModel> findAllByQuotationIdAndDeletedFalse(int quotationId);

	@Modifying
	@Query("UPDATE QuotationProductModel qm SET qm.quantity = :quantity WHERE qm.quotation.id = :quotationId AND qm.product.id = :productId")
	void updateQuantityByQuotationIdAndProductId(
			@Param("quantity") int quantity, @Param("quotationId") int quotationId, @Param("productId") int productId);

	@Modifying
	@Query("UPDATE QuotationProductModel qm SET qm.deleted = true WHERE qm.id = :id")
	void logicalDeleteById(@Param("id") int id);

	List<QuotationProductModel> findAllByQuotationId(int quotationId);
}
