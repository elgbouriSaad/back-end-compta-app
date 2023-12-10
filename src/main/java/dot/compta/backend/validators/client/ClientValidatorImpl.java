package dot.compta.backend.validators.client;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.client.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientValidatorImpl implements ClientValidator {

    private final ClientRepository clientRepository;

    @Override
    public void validateExists(int id) {
        if (!clientRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.CLIENT_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (clientRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.CLIENT_DELETED, id));
        }
    }

}
