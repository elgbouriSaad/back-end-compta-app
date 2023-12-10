package dot.compta.backend.services.quotationProduct;

import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;

import java.util.List;

public interface QuotationProductService {

    void createQuotationProducts(QuotationModel quotation, List<RequestProductQuantityDto> productQuantities);
    
    void updateQuotationProducts(QuotationModel quotation, List<RequestProductQuantityDto> productQuantities);
    
    void transformQuotationProducts(QuotationModel quotation, InvoiceModel invoice);
    
    void deleteQuotationProducts(int quotationId);

    List<ResponseQuotationProductDto> getQuotationProducts(int quotationId);

}
