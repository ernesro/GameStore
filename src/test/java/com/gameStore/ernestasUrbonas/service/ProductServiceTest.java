package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.mapper.ProductMapper;
import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.enums.ItemCategory;
import com.gameStore.ernestasUrbonas.model.enums.ItemCondition;
import com.gameStore.ernestasUrbonas.model.enums.ItemTag;
import com.gameStore.ernestasUrbonas.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {

        this.product = new Product();
        this.product.setId(1L);
        this.product.setName("Test Product");
        this.product.setDescription("Test Description");
        this.product.setPrice(9.99);
        this.product.setItemCondition(ItemCondition.NEW);
        this.product.setAverageRating(4.5);
        this.product.setImageUrl("http://example.com/image.jpg");
        this.product.setItemCategory(ItemCategory.CONSOLE_GAME);
        this.product.setItemTags(List.of(ItemTag.PS1, ItemTag.ADVENTURE));

        this.productDTO = new ProductDTO();
        this.productDTO.setName("Test Product");
        this.productDTO.setDescription("Test Description");
        this.productDTO.setPrice(9.99);
        this.productDTO.setItemCondition(ItemCondition.NEW);
        this.productDTO.setAverageRating(4.5);
        this.productDTO.setImageUrl("http://example.com/image.jpg");
        this.productDTO.setItemCategory(ItemCategory.CONSOLE_GAME);
        this.productDTO.setItemTags(List.of(ItemTag.PS1, ItemTag.ADVENTURE));
    }


    // ===============================================================================
    // createProduct
    // ===============================================================================

    @Test
    public void createProduct_ShouldCreateAndReturnProductDTO() {

        //ARRANGE---------------------------------------------------------------------

        // We use any(Product.class) Because the product is created inside the mapper
        when(this.productRepository.save(any(Product.class))).thenReturn(this.product);
        when(this.productMapper.mapDTOToEntity(this.productDTO)).thenReturn(this.product);
        when(this.productMapper.mapEntityToDTO(this.product)).thenReturn(this.productDTO);

        //ACT-------------------------------------------------------------------------

        ProductDTO createdProductDTO = this.productService.createProduct(this.productDTO);

        //ASSERT----------------------------------------------------------------------

        assertThat(createdProductDTO).isNotNull();
        assertThat(createdProductDTO.getName()).isEqualTo(this.productDTO.getName());

        verify(this.productRepository).save(any(Product.class));
    }


    // ===============================================================================
    // findProductById
    // ===============================================================================

    @Test
    public void findProductById_WithNotExistingId_ShouldThrowNotFoundException() {

        //ARRANGE---------------------------------------------------------------------

        when(this.productRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> this.productService.findProductById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product not found with identifier: 99");

    }

    @Test
    public void findProductById_WithExistingId_ShouldReturnProductDTO() {

        //ARRANGE---------------------------------------------------------------------

        when(this.productRepository.findById(1L)).thenReturn(Optional.of(this.product));
        when(this.productMapper.mapEntityToDTO(this.product)).thenReturn(this.productDTO);

        //ACT-------------------------------------------------------------------------

        ProductDTO result = this.productService.findProductById(1L);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(this.product.getName());

        verify(this.productRepository).findById(1L);
    }


    // ===============================================================================
    // findAllProducts
    // ===============================================================================

    @Test
    public void findAllProducts_ShouldReturn_A_ListOfProductsDTO() {

        //ARRANGE---------------------------------------------------------------------

        when(this.productRepository.findAll()).thenReturn(List.of(this.product));
        when(this.productMapper.mapEntityToDTO(this.product)).thenReturn(this.productDTO);

        //ACT-------------------------------------------------------------------------

        List<ProductDTO> result = this.productService.findAllProducts();

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo(this.productDTO.getName());

        verify(this.productRepository).findAll();
    }


    // ===============================================================================
    // updateProduct
    // ===============================================================================

    @Test
    public void updateProductById_WithExistingId_ShouldReturnProductDTO() {

        //ARRANGE---------------------------------------------------------------------

        ProductDTO updateRequest = new ProductDTO();
        updateRequest.setId(1L);
        updateRequest.setPrice(29.99);
        updateRequest.setName("Updated");
        updateRequest.setDescription("Product updated");

        when(this.productRepository.findById(1L)).thenReturn(Optional.of(this.product));
        when(this.productMapper.mapEntityToDTO(this.product)).thenReturn(updateRequest);
        when(this.productRepository.save(any(Product.class))).thenReturn(this.product);

        //ACT-------------------------------------------------------------------------

        ProductDTO result = this.productService.updateProduct(updateRequest);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated");

        verify(this.productRepository).save(any(Product.class));
    }

    @Test
    public void updateProductById_WithNotExistingId_ShouldThrowNotFoundException() {

        //ARRANGE---------------------------------------------------------------------

        this.productDTO.setId(99L);
        when(this.productRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> this.productService.updateProduct(productDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product not found with identifier: " + productDTO.getId().toString());

    }


    // ===============================================================================
    // deleteProduct
    // ===============================================================================

    @Test
    public void deleteProductById_WithExistingId_ShouldRespondOk() {

        //ARRANGE---------------------------------------------------------------------

        when(this.productRepository.findById(1L)).thenReturn(Optional.of(this.product));

        //ACT-------------------------------------------------------------------------

        this.productService.deleteProduct(1L);

        //ASSERT----------------------------------------------------------------------

        verify(this.productRepository).delete(this.product);

    }

    @Test
    public void deleteProductById_WithNotExistingId_ShouldThrowNotFoundException() {

        //ARRANGE---------------------------------------------------------------------

        when(this.productRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> this.productService.deleteProduct(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product not found with identifier: 99");
    }
}