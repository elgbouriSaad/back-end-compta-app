package dot.compta.backend.mappers.invoiceProduct;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InvoiceProductMapperImpl implements InvoiceProductMapper{
	
	private final ModelMapper modelMapper;

    @Override
    public InvoiceProductModel mapToInvoiceProductModel(ProductModel product) {
    	InvoiceProductModel invoiceProductModel = modelMapper.map(product, InvoiceProductModel.class);
        invoiceProductModel.setId(0);
        return invoiceProductModel;
    }
    
    @Override
    public ResponseInvoiceProductDto mapToResponseInvoiceProductDto(InvoiceProductModel invoiceProduct) {
    	ResponseInvoiceProductDto responseInvoiceProductDto = modelMapper.map(invoiceProduct, ResponseInvoiceProductDto.class);
    	responseInvoiceProductDto.setProductId(invoiceProduct.getProduct().getId());
        return responseInvoiceProductDto;
    }
    
    @Override
    public InvoiceProductModel mapToInvoiceProductModel(QuotationProductModel quotationProduct, InvoiceModel invoice) {
    	InvoiceProductModel invoiceProduct = modelMapper.map(quotationProduct, InvoiceProductModel.class);
    	invoiceProduct.setInvoice(invoice);
    	invoiceProduct.setId(0);
    	return invoiceProduct;
    }

}
