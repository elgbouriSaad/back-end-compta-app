package dot.compta.backend.controllers.product;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.services.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.PRODUCTS_URL)
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    @Transactional
    public void createProduct(@RequestBody @Validated RequestProductDto product) {
        productService.createProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    public List<ResponseProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    public ResponseProductDto getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping(APIConstants.CUSTOMER_PRODUCTS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    public List<ResponseProductDto> getProductsByCustomerId(@PathVariable int id) {
        return productService.getProductsByCustomerId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    @Transactional
    public void updateProduct(@RequestBody @Validated UpdateProductDto product, @PathVariable int id) {
        productService.updateProduct(product, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_PRODUCT_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_PRODUCT_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_PRODUCT_CONTROLLER_TAGS)
    @Transactional
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

}
