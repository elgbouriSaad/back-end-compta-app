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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;

	@Mock
	private ProductValidator productValidator;

	@Mock
	private CustomerValidator customerValidator;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	@BeforeEach
	void setUp() {
		openMocks(this);
	}

	@AfterEach
	void tearDown() {
		verifyNoMoreInteractions(
				productRepository,
				productMapper,
				productValidator,
				customerValidator,
				customerRepository
		);
	}

	@Test
	public void testCreateProduct() {
		// GIVEN
		RequestProductDto productDto = mock(RequestProductDto.class);
		CustomerModel customer = mock(CustomerModel.class);
		ProductModel product = ProductModel.builder()
				.id(PRODUCT_ID_TEST__1006)
				.label(PRODUCT_LABEL_TEST)
				.reference(PRODUCT_REFERENCE_TEST)
				.priceExclTax(PRODUCT_PRICE_TEST)
				.unity(PRODUCT_UNITY_TEST)
				.qualification(PRODUCT_QUALIFICATION_TEST)
				.tax(PRODUCT_TAX_TEST)
				.customer(customer)
                .build();
        when(productDto.getCustomerId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(productDto.getReference()).thenReturn(PRODUCT_REFERENCE_TEST);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
        when(productMapper.mapToProductModel(productDto, customer)).thenReturn(product);

        // WHEN
        productService.createProduct(productDto);

        // THEN
        verify(productRepository).save(product);
        verify(productMapper).mapToProductModel(productDto, customer);
        verify(productValidator).validateNotExistByReferenceAndCustomerId(PRODUCT_REFERENCE_TEST, CUSTOMER_ID_TEST__1004);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
    }

	@Test
	public void testGetProducts() {
        // GIVEN
        ProductModel product1 = mock(ProductModel.class);
        ProductModel product2 = mock(ProductModel.class);
        List<ProductModel> mockProducts = Arrays.asList(product1, product2);
        when(productRepository.findAllByDeletedFalse()).thenReturn(mockProducts);
        ResponseProductDto productDto1 = mock(ResponseProductDto.class);
        ResponseProductDto productDto2 = mock(ResponseProductDto.class);
        when(productMapper.mapToResponseProductDto(product1)).thenReturn(productDto1);
        when(productMapper.mapToResponseProductDto(product2)).thenReturn(productDto2);

        // WHEN
        List<ResponseProductDto> result = productService.getProducts();

        // THEN
        assertEquals(2, result.size());
        assertEquals(productDto1, result.get(0));
        assertEquals(productDto2, result.get(1));

        verify(productRepository).findAllByDeletedFalse();
        verify(productMapper).mapToResponseProductDto(product1);
        verify(productMapper).mapToResponseProductDto(product2);
    }

    @Test
    public void getProductByIdTest() {
        // GIVEN
        ProductModel product = mock(ProductModel.class);
        when(productRepository.findById(PRODUCT_ID_TEST__1006)).thenReturn(Optional.of(product));
        ResponseProductDto productDto = mock(ResponseProductDto.class);
        when(productMapper.mapToResponseProductDto(product)).thenReturn(productDto);

        // WHEN
        ResponseProductDto result = productService.getProductById(PRODUCT_ID_TEST__1006);

        // THEN
        assertNotNull(result);
        assertEquals(productDto, result);
        verify(productRepository).findById(PRODUCT_ID_TEST__1006);
        verify(productMapper).mapToResponseProductDto(product);
        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
    }

    @Test
    public void testGetProductsByCustomerId() {
        // GIVEN
        ProductModel product1 = mock(ProductModel.class);
        ProductModel product2 = mock(ProductModel.class);
        List<ProductModel> mockProducts = Arrays.asList(product1, product2);
        when(productRepository.findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004)).thenReturn(mockProducts);
        ResponseProductDto productDto1 = mock(ResponseProductDto.class);
        ResponseProductDto productDto2 = mock(ResponseProductDto.class);
        when(productMapper.mapToResponseProductDto(product1)).thenReturn(productDto1);
        when(productMapper.mapToResponseProductDto(product2)).thenReturn(productDto2);

        // WHEN
        List<ResponseProductDto> result = productService.getProductsByCustomerId(CUSTOMER_ID_TEST__1004);

        // THEN
        assertEquals(2, result.size());
        assertEquals(productDto1, result.get(0));
        assertEquals(productDto2, result.get(1));

        verify(productRepository).findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004);
        verify(productMapper).mapToResponseProductDto(product1);
        verify(productMapper).mapToResponseProductDto(product2);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testDeleteProduct() {
        // WHEN
        productService.deleteProduct(PRODUCT_ID_TEST__1006);

        // THEN
        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
        verify(productRepository).logicalDeleteById(PRODUCT_ID_TEST__1006);
    }

    @Test
    public void testUpdateProduct() {
        // GIVEN
        CustomerModel customerModel = mock(CustomerModel.class);
        ProductModel product = ProductModel.builder()
				.id(PRODUCT_ID_TEST__1006)
				.label(PRODUCT_LABEL_TEST)
				.reference(PRODUCT_REFERENCE_TEST)
				.priceExclTax(PRODUCT_PRICE_TEST)
				.unity(PRODUCT_UNITY_TEST)
				.qualification(PRODUCT_QUALIFICATION_TEST)
				.tax(PRODUCT_TAX_TEST)
				.customer(customerModel)
                .build();
        UpdateProductDto updateProductDto = mock(UpdateProductDto.class);
        when(productMapper.mapToProductModel(updateProductDto, customerModel)).thenReturn(product);
        when(updateProductDto.getReference()).thenReturn(PRODUCT_REFERENCE_TEST);
        when(productRepository.findById(PRODUCT_ID_TEST__1006)).thenReturn(Optional.of(product));

        // WHEN
        productService.updateProduct(updateProductDto, PRODUCT_ID_TEST__1006);

        // THEN
        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
        verify(productMapper).mapToProductModel(updateProductDto, customerModel);
        verify(productRepository).findById(PRODUCT_ID_TEST__1006);
        verify(productRepository).save(product);
    }

}
