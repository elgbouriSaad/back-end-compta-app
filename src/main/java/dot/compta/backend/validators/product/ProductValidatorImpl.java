package dot.compta.backend.validators.product;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductValidatorImpl implements ProductValidator {

    private final ProductRepository productRepository;

    @Override
    public void validateNotExistByReferenceAndCustomerId(String reference, int id) {
        if (productRepository.existsByReferenceIgnoreCaseAndCustomerIdAndDeletedFalse(reference, id)) {
            throw new CustomBadInputException(String.format(ValidationConstants.PRODUCT_ALREADY_EXISTS, reference, id));
        }
    }

    @Override
    public void validateExists(int id) {
        if (!productRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.PRODUCT_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (productRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.PRODUCT_DELETED, id));
        }
    }

}
