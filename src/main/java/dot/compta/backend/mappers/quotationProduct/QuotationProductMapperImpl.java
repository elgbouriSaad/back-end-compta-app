package dot.compta.backend.mappers.quotationProduct;


import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuotationProductMapperImpl implements QuotationProductMapper {

    private final ModelMapper modelMapper;

    @Override
    public QuotationProductModel mapToQuotationProductModel(ProductModel product) {
        QuotationProductModel quotationProductModel = modelMapper.map(product, QuotationProductModel.class);
        quotationProductModel.setId(0);
        return quotationProductModel;
    }

    @Override
    public ResponseQuotationProductDto mapToResponseQuotationProductDto(QuotationProductModel quotationProduct) {
    	ResponseQuotationProductDto responseQuotationProductDto = modelMapper.map(quotationProduct, ResponseQuotationProductDto.class);
    	responseQuotationProductDto.setProductId(quotationProduct.getProduct().getId());
        return responseQuotationProductDto;
    }

}
