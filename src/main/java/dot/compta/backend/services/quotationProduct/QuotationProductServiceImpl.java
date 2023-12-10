package dot.compta.backend.services.quotationProduct;

import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.mappers.invoiceProduct.InvoiceProductMapper;
import dot.compta.backend.mappers.quotationProduct.QuotationProductMapper;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import dot.compta.backend.repositories.invoiceProduct.InvoiceProductRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import dot.compta.backend.repositories.quotationProduct.QuotationProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuotationProductServiceImpl implements QuotationProductService {

	private final QuotationProductRepository quotationProductRepository;

	private final ProductRepository productRepository;
	
	private final InvoiceProductRepository invoiceProductRepository;

	private final QuotationProductMapper quotationProductMapper;
	
	private final InvoiceProductMapper invoiceProductMapper;

	private static Set<Integer> extractNewQuotationProductsIds(List<RequestProductQuantityDto> productQuantities) {
		return productQuantities.stream()
				.map(RequestProductQuantityDto::getProductId)
				.collect(Collectors.toSet());
	}

	private static Set<Integer> extractOldProductsIds(List<QuotationProductModel> quotationProducs) {
		return quotationProducs.stream()
				.map(e -> e.getProduct().getId())
				.collect(Collectors.toSet());
	}

	@Override
	public void createQuotationProducts(QuotationModel quotation, List<RequestProductQuantityDto> productQuantities) {
		List<QuotationProductModel> quotationProducts = new ArrayList<>();
		for (RequestProductQuantityDto productQuantity : productQuantities) {
			Optional<ProductModel> product = productRepository.findById(productQuantity.getProductId());
			QuotationProductModel quotationProduct = quotationProductMapper.mapToQuotationProductModel(product.get());
			quotationProduct.setQuotation(quotation);
			quotationProduct.setProduct(product.get());
			quotationProduct.setQuantity(productQuantity.getQuantity());
			quotationProducts.add(quotationProduct);
		}
		quotationProductRepository.saveAll(quotationProducts);
	}

	@Override
	public void updateQuotationProducts(QuotationModel quotation, List<RequestProductQuantityDto> productQuantities) {
		List<QuotationProductModel> oldQuotationProducts = quotationProductRepository.findAllByQuotationIdAndDeletedFalse(quotation.getId());
		processDeletedQuotationProducts(oldQuotationProducts, productQuantities);
		processUpdatedQuotationProducts(quotation, oldQuotationProducts, productQuantities);
		processAddedQuotationProducts(quotation, oldQuotationProducts, productQuantities);
	}

	private void processAddedQuotationProducts(QuotationModel quotation, List<QuotationProductModel> oldQuotationProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> oldProductsIds = extractOldProductsIds(oldQuotationProducts);
		List<RequestProductQuantityDto> newProductQuantitiesToAdd = productQuantities.stream()
				.filter(e -> !oldProductsIds.contains(e.getProductId()))
				.collect(Collectors.toList());
		createQuotationProducts(quotation, newProductQuantitiesToAdd);
	}

	private void processUpdatedQuotationProducts(QuotationModel quotation, List<QuotationProductModel> oldQuotationProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> oldProductsIds = extractOldProductsIds(oldQuotationProducts);
		productQuantities.stream()
				.filter(e -> oldProductsIds.contains(e.getProductId()))
				.forEach(e -> quotationProductRepository.updateQuantityByQuotationIdAndProductId(e.getQuantity(), quotation.getId(), e.getProductId()));
	}

	private void processDeletedQuotationProducts(List<QuotationProductModel> oldQuotationProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> newProductsIds = extractNewQuotationProductsIds(productQuantities);
		oldQuotationProducts.stream()
				.filter(e -> !newProductsIds.contains(e.getProduct().getId()))
				.forEach(e -> quotationProductRepository.logicalDeleteById(e.getId()));
	}

	@Override
	public List<ResponseQuotationProductDto> getQuotationProducts(int quotationId) {
		List<QuotationProductModel> quotationProducts = quotationProductRepository.findAllByQuotationIdAndDeletedFalse(quotationId);
		return quotationProducts.stream()
				.map(quotationProductMapper::mapToResponseQuotationProductDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void transformQuotationProducts(QuotationModel quotation, InvoiceModel invoice) {
		List<QuotationProductModel> quotationProducts = quotationProductRepository.findAllByQuotationIdAndDeletedFalse(quotation.getId());
		List<InvoiceProductModel> invoiceProducts = new ArrayList<>();
		for (QuotationProductModel quotationProduct : quotationProducts) {
			InvoiceProductModel invoiceProduct = invoiceProductMapper.mapToInvoiceProductModel(quotationProduct, invoice);
			invoiceProducts.add(invoiceProduct);
		}
		invoiceProductRepository.saveAll(invoiceProducts);
	}
	
	@Override
	public void deleteQuotationProducts(int quotationId) {
		List<QuotationProductModel> quotationProducts = quotationProductRepository.findAllByQuotationIdAndDeletedFalse(quotationId);
		for (QuotationProductModel quotationProduct : quotationProducts) {
			quotationProductRepository.logicalDeleteById(quotationProduct.getId());
		}
	}

}
