package com.gameStore.ernestasUrbonas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.model.enums.ItemCategory;
import com.gameStore.ernestasUrbonas.model.enums.ItemCondition;
import com.gameStore.ernestasUrbonas.model.enums.ItemTag;
import com.gameStore.ernestasUrbonas.security.JwtUtil;
import com.gameStore.ernestasUrbonas.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @TestConfiguration
    @EnableMethodSecurity
    static class TestSecurityConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtUtil jwtUtil;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(9.99);
        productDTO.setItemCondition(ItemCondition.NEW);
        productDTO.setAverageRating(4.5);
        productDTO.setImageUrl("http://example.com/image.jpg");
        productDTO.setItemCategory(ItemCategory.CONSOLE_GAME);
        productDTO.setItemTags(List.of(ItemTag.PS1, ItemTag.ADVENTURE));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAllProducts_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findProductById_shouldReturn200_whenProductExists() throws Exception {
        when(productService.findProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findProductById_shouldReturn404_whenProductDoesNotExist() throws Exception {
        when(productService.findProductById(1L))
                .thenThrow(new NotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_shouldReturn201_whenAdminCreatesProduct() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createProduct_shouldReturn403_whenUserCreatesProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
