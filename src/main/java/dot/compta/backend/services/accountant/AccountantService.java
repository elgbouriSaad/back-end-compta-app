package dot.compta.backend.services.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;

import java.util.List;

public interface AccountantService {

    void createAccountant(RequestAccountantDto requestAccountantDto);

    void updateAccountant(RequestAccountantDto requestAccountantDto, int id);

    void deleteAccountant(int id);

    List<ResponseAccountantDto> getAccountants();

    ResponseAccountantDto getAccountantById(int id);

}
