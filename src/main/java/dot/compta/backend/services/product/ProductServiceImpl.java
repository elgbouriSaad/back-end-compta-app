package dot.compta.backend.services.product;

import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.mappers.product.ProductMapper;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.product.ProductValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductMapper productMapper;

	private final ProductRepository productRepository;

	private final CustomerRepository customerRepository;

	private final ProductValidator productValidator;

	private final CustomerValidator customerValidator;

	@Override
	public void createProduct(RequestProductDto requestProductDto) {
		customerValidator.validateExistsAndNotDeleted(requestProductDto.getCustomerId());
		productValidator.validateNotExistByReferenceAndCustomerId(requestProductDto.getReference(), requestProductDto.getCustomerId());
		Optional<CustomerModel> customer = customerRepository.findById(requestProductDto.getCustomerId());
		productRepository.save(productMapper.mapToProductModel(requestProductDto, customer.get()));
	}

	@Override
	public List<ResponseProductDto> getProducts() {
		List<ProductModel> nonDeletedProducts = productRepository.findAllByDeletedFalse();
		return nonDeletedProducts.stream()
				.map(productMapper::mapToResponseProductDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseProductDto getProductById(int id) {
		productValidator.validateExistsAndNotDeleted(id);
		Optional<ProductModel> product = productRepository.findById(id);
		return productMapper.mapToResponseProductDto(product.get());
	}

	@Override
	public List<ResponseProductDto> getProductsByCustomerId(int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		List<ProductModel> products = productRepository.findAllByCustomerIdAndDeletedFalse(id);
		return products.stream()
				.map(productMapper::mapToResponseProductDto)
				.collect(Collectors.toList());
	}

	@Override
	public void updateProduct(UpdateProductDto newProductDetails, int id) {
		productValidator.validateExistsAndNotDeleted(id);
		Optional<ProductModel> oldProduct = productRepository.findById(id);
		ProductModel product = productMapper.mapToProductModel(newProductDetails, oldProduct.get().getCustomer());
		product.setId(id);
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(int id) {
		productValidator.validateExistsAndNotDeleted(id);
		productRepository.logicalDeleteById(id);;
	}
	
	@Override
	public void deleteCustomerProducts(int customerId) {
		List<ProductModel> nonDeletedProducts = productRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		for (ProductModel product : nonDeletedProducts) {
			productRepository.logicalDeleteById(product.getId());
		}
	}

}
