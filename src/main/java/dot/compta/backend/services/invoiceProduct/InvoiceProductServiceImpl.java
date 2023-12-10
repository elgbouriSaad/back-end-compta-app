package dot.compta.backend.services.invoiceProduct;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.mappers.invoiceProduct.InvoiceProductMapper;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.repositories.invoiceProduct.InvoiceProductRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceProductServiceImpl implements InvoiceProductService{
	
	private final InvoiceProductRepository invoiceProductRepository;

	private final ProductRepository productRepository;

	private final InvoiceProductMapper invoiceProductMapper;
	
	@Override
	public void createInvoiceProducts(InvoiceModel invoice, List<RequestProductQuantityDto> productQuantities) {
		List<InvoiceProductModel> invoiceProducts = new ArrayList<>();
		for (RequestProductQuantityDto productQuantity : productQuantities) {
			Optional<ProductModel> product = productRepository.findById(productQuantity.getProductId());
			InvoiceProductModel invoiceProduct = invoiceProductMapper.mapToInvoiceProductModel(product.get());
			invoiceProduct.setInvoice(invoice);
			invoiceProduct.setProduct(product.get());
			invoiceProduct.setQuantity(productQuantity.getQuantity());
			invoiceProducts.add(invoiceProduct);
		}
		invoiceProductRepository.saveAll(invoiceProducts);
	}
	
	@Override
	public List<ResponseInvoiceProductDto> getInvoiceProducts(int invoiceId){
		List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(invoiceId);
		return invoiceProducts.stream()
				.map(invoiceProductMapper::mapToResponseInvoiceProductDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void updateInvoiceProducts(InvoiceModel invoice, List<RequestProductQuantityDto> productQuantities) {
		List<InvoiceProductModel> oldInvoiceProducts = invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(invoice.getId());
		processDeletedInvoiceProducts(oldInvoiceProducts, productQuantities);
		processUpdatedInvoiceProducts(invoice, oldInvoiceProducts, productQuantities);
		processAddedInvoiceProducts(invoice, oldInvoiceProducts, productQuantities);
	}
	
	@Override
	public void deleteInvoiceProducts(int invoiceId) {
		List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(invoiceId);
		for (InvoiceProductModel invoiceProduct : invoiceProducts) {
			invoiceProductRepository.logicalDeleteById(invoiceProduct.getId());
		}
	}
	
	private void processDeletedInvoiceProducts(List<InvoiceProductModel> oldInvoiceProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> newProductsIds = extractNewInvoiceProductsIds(productQuantities);
		oldInvoiceProducts.stream()
				.filter(e -> !newProductsIds.contains(e.getProduct().getId()))
				.forEach(e -> invoiceProductRepository.logicalDeleteById(e.getId()));
	}
	
	private static Set<Integer> extractNewInvoiceProductsIds(List<RequestProductQuantityDto> productQuantities) {
		return productQuantities.stream()
				.map(RequestProductQuantityDto::getProductId)
				.collect(Collectors.toSet());
	}
	
	private void processUpdatedInvoiceProducts(InvoiceModel invoice, List<InvoiceProductModel> oldInvoiceProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> oldProductsIds = extractOldProductsIds(oldInvoiceProducts);
		productQuantities.stream()
				.filter(e -> oldProductsIds.contains(e.getProductId()))
				.forEach(e -> invoiceProductRepository.updateQuantityByInvoiceIdAndProductId(e.getQuantity(), invoice.getId(), e.getProductId()));
	}
	
	private static Set<Integer> extractOldProductsIds(List<InvoiceProductModel> invoiceProducts) {
		return invoiceProducts.stream()
				.map(e -> e.getProduct().getId())
				.collect(Collectors.toSet());
	}
	
	private void processAddedInvoiceProducts(InvoiceModel invoice, List<InvoiceProductModel> oldInvoiceProducts, List<RequestProductQuantityDto> productQuantities) {
		Set<Integer> oldProductsIds = extractOldProductsIds(oldInvoiceProducts);
		List<RequestProductQuantityDto> newProductQuantitiesToAdd = productQuantities.stream()
				.filter(e -> !oldProductsIds.contains(e.getProductId()))
				.collect(Collectors.toList());
		createInvoiceProducts(invoice, newProductQuantitiesToAdd);
	}
	
}
