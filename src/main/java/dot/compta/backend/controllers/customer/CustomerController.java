package dot.compta.backend.controllers.customer;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.services.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.CUSTOMERS_URL)
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    @Transactional
    public void createCustomer(@RequestBody @Validated RequestCustomerDto customer) {
        customerService.createCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    public List<ResponseCustomerDto> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    public ResponseCustomerDto getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping(APIConstants.ACCOUNTANT_CUSTOMERS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_ACCOUNTANTGET_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_ACCOUNTANTGET_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    public List<ResponseCustomerDto> getCustomersByAccountantId(@PathVariable int id) {
        return customerService.getCustomersByAccountantId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    @Transactional
    public void updateCustomer(@RequestBody @Validated UpdateCustomerDto customer, @PathVariable int id) {
        customerService.updateCustomer(customer, id);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_CUSTOMER_CONTROLLER_TAGS)
    @Transactional
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }
}
