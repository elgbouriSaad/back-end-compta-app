package dot.compta.backend.services.product;

import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;

import java.util.List;

public interface ProductService {

    void createProduct(RequestProductDto requestProductDto);

    void updateProduct(UpdateProductDto updateProductDto, int id);

    void deleteProduct(int id);
    
    void deleteCustomerProducts(int customerId);

    List<ResponseProductDto> getProducts();

    ResponseProductDto getProductById(int id);

    List<ResponseProductDto> getProductsByCustomerId(int id);

}
