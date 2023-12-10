package dot.compta.backend.mappers.product;

import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final ModelMapper modelMapper;

    @Override
    public ProductModel mapToProductModel(RequestProductDto requestProductDto, CustomerModel customer) {
        ProductModel product = modelMapper.map(requestProductDto, ProductModel.class);
        product.setCustomer(customer);
        return product;
    }
    
    @Override
    public ProductModel mapToProductModel(UpdateProductDto updateProductDto, CustomerModel customer) {
        ProductModel product = modelMapper.map(updateProductDto, ProductModel.class);
        product.setCustomer(customer);
        return product;
    }

    @Override
    public ResponseProductDto mapToResponseProductDto(ProductModel productModel) {
        ResponseProductDto responseProductDto = modelMapper.map(productModel, ResponseProductDto.class);
        responseProductDto.setCustomerId(productModel.getCustomer().getId());
        return responseProductDto;
    }

}
