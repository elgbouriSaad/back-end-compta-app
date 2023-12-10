package dot.compta.backend.repositories.expenseReport;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseReportRepository extends JpaRepository<ExpenseReportModel, Integer> {

    boolean existsByIdAndDeletedTrue(int id);
    
    boolean existsByIdAndStatus(int id, ExpenseReportStatus status);

    // TODO not unique possible exception
    Optional<ExpenseReportModel> findOneByLabelIgnoreCase(String label);

    List<ExpenseReportModel> findAllByDeletedFalse();

    List<ExpenseReportModel> findAllByCustomerIdAndDeletedFalse(int id);
    
    @Modifying
    @Query("UPDATE ExpenseReportModel e SET e.status = :status WHERE e.id = :expenseReportId")
    void updateStatusById(@Param("status") ExpenseReportStatus status, @Param("expenseReportId") int expenseReportId);
    
	@Modifying
	@Query("UPDATE ExpenseReportModel e SET e.deleted = true WHERE e.id = :id")
	void logicalDeleteById(@Param("id") int id);

}
