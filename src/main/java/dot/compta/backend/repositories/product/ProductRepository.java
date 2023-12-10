package dot.compta.backend.repositories.product;

import dot.compta.backend.models.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    boolean existsByReferenceIgnoreCaseAndCustomerIdAndDeletedFalse(String reference, int id);

    boolean existsByIdAndDeletedTrue(int id);

    // TODO not unique possible exception
    Optional<ProductModel> findOneByReferenceIgnoreCase(String reference);

    List<ProductModel> findAllByDeletedFalse();

    List<ProductModel> findAllByCustomerIdAndDeletedFalse(int id);
    
    @Modifying
	@Query("UPDATE ProductModel p SET p.deleted = true WHERE p.id = :id")
	void logicalDeleteById(@Param("id") int id);

}
