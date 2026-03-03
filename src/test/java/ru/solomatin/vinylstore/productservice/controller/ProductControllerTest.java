package ru.solomatin.vinylstore.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.solomatin.vinylstore.productservice.dto.VinylDTO;
import ru.solomatin.vinylstore.productservice.model.Vinyl;
import ru.solomatin.vinylstore.productservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    private Vinyl createTestVinyl(String id, String name) {
        return new Vinyl(id, name, "Description", new BigDecimal("19.99"),
                1, "Musician", "Label", "Song1, Song2");
    }

    @Test
    void createProduct_shouldReturn201() throws Exception {
        VinylDTO dto = new VinylDTO("Album", "Musician", "Label",
                "Desc", new BigDecimal("19.99"), 1, "Song1");

        mockMvc.perform(post("/api/product/vinyl/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(productService).saveNewVinyl(any(VinylDTO.class));
    }

    @Test
    void getAllProducts_shouldReturnListAndStatus200() throws Exception {
        List<Vinyl> products = List.of(
                createTestVinyl("id-1", "Album One"),
                createTestVinyl("id-2", "Album Two")
        );
        when(productService.getAllVinyl()).thenReturn(products);

        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Album One")))
                .andExpect(jsonPath("$[1].name", is("Album Two")));
    }

    @Test
    void getAllProducts_shouldReturnEmptyList() throws Exception {
        when(productService.getAllVinyl()).thenReturn(List.of());

        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getProductById_shouldReturnVinylAndStatus200() throws Exception {
        Vinyl vinyl = createTestVinyl("test-id", "Test Album");
        when(productService.getVinylById("test-id")).thenReturn(vinyl);

        mockMvc.perform(get("/api/product/vinyl/test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("test-id")))
                .andExpect(jsonPath("$.name", is("Test Album")))
                .andExpect(jsonPath("$.musician", is("Musician")));
    }

    @Test
    void getProductById_shouldReturn404WhenNotFound() throws Exception {
        when(productService.getVinylById("missing-id"))
                .thenThrow(new NoSuchElementException("Vinyl not found with id: missing-id"));

        mockMvc.perform(get("/api/product/vinyl/missing-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_shouldReturn204() throws Exception {
        doNothing().when(productService).deleteVinylById("test-id");

        mockMvc.perform(delete("/api/product/vinyl/test-id"))
                .andExpect(status().isNoContent());

        verify(productService).deleteVinylById("test-id");
    }

    @Test
    void deleteProduct_shouldReturn404WhenNotFound() throws Exception {
        doThrow(new NoSuchElementException("Vinyl not found with id: missing-id"))
                .when(productService).deleteVinylById("missing-id");

        mockMvc.perform(delete("/api/product/vinyl/missing-id"))
                .andExpect(status().isNotFound());
    }
}
