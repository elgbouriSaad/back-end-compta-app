package dot.compta.backend.mappers.quotationProduct;

import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;

public interface QuotationProductMapper {

    QuotationProductModel mapToQuotationProductModel(ProductModel product);

    ResponseQuotationProductDto mapToResponseQuotationProductDto(QuotationProductModel quotationProduct);

}
