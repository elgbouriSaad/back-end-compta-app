package dot.compta.backend.controllers.product;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.services.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static dot.compta.backend.utils.TestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProductControllerTest extends BaseProductControllerTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void testCreateProduct() throws Exception {
        RequestProductDto requestProductDto = buildRequestProductDto();
        String content = objectMapper.writeValueAsString(requestProductDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.PRODUCTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(productService).createProduct(requestProductDto);
    }

    @Test
    public void testGetProducts() throws Exception {
        ResponseProductDto responseProductDto = buildResponseProductDto();
        List<ResponseProductDto> products = Collections.singletonList(responseProductDto);
        given(productService.getProducts()).willReturn(products);
        mvc.perform(get(APIConstants.PRODUCTS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ProductController_getProducts.json"), true));
        verify(productService).getProducts();
    }

    @Test
    public void testGetProductById() throws Exception {
        ResponseProductDto responseProductDto = buildResponseProductDto();
        given(productService.getProductById(PRODUCT_ID_TEST__1006)).willReturn(responseProductDto);
        mvc.perform(get(APIConstants.PRODUCTS_URL + "/" + PRODUCT_ID_TEST__1006)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ProductController_getProductById.json"), true));
        verify(productService).getProductById(PRODUCT_ID_TEST__1006);
    }

    @Test
    public void testGetProductsByCustomerId() throws Exception {
        ResponseProductDto responseProductDto = buildResponseProductDto();
        List<ResponseProductDto> products = Collections.singletonList(responseProductDto);
        given(productService.getProductsByCustomerId(CUSTOMER_ID_TEST__1004)).willReturn(products);
        mvc.perform(get(APIConstants.PRODUCTS_URL + APIConstants.CUSTOMER_PRODUCTS_URL + CUSTOMER_ID_TEST__1004)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ProductController_getProductsByCustomer.json"), true));
        verify(productService).getProductsByCustomerId(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.PRODUCTS_URL + "/" + PRODUCT_ID_TEST__1006))
                .andExpect(status().isOk());
        verify(productService).deleteProduct(PRODUCT_ID_TEST__1006);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        UpdateProductDto updateProductDto = buildUpdateProductDto("");
        String content = objectMapper.writeValueAsString(updateProductDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.PRODUCTS_URL + "/" + PRODUCT_ID_TEST__1006)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(productService).updateProduct(updateProductDto, PRODUCT_ID_TEST__1006);
    }

}
