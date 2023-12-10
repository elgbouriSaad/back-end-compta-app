package dot.compta.backend.mappers.product;

import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;

public interface ProductMapper {

    ProductModel mapToProductModel(RequestProductDto requestProductDto, CustomerModel customer);
    
    ProductModel mapToProductModel(UpdateProductDto updateProductDto, CustomerModel customer);

    ResponseProductDto mapToResponseProductDto(ProductModel productModel);

}
