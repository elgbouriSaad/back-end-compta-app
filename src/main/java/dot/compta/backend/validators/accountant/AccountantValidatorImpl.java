package dot.compta.backend.validators.accountant;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountantValidatorImpl implements AccountantValidator {

    private final AccountantRepository accountantRepository;

    @Override
    public void validateExists(int id) {
        if (!accountantRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.ACCOUNTANT_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (accountantRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.ACCOUNTANT_DELETED, id));
        }
    }

}
