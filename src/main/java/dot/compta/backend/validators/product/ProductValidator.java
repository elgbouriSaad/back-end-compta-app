package dot.compta.backend.validators.product;

import dot.compta.backend.validators.CustomValidator;

public interface ProductValidator extends CustomValidator {
    void validateNotExistByReferenceAndCustomerId(String reference, int id);
}
