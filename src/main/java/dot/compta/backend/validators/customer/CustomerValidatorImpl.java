package dot.compta.backend.validators.customer;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerValidatorImpl implements CustomerValidator {

    private final CustomerRepository customerRepository;

    @Override
    public void validateExists(int id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.CUSTOMER_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (customerRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.CUSTOMER_DELETED, id));
        }
    }

}
