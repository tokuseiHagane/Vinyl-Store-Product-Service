package ru.solomatin.vinylstore.productservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solomatin.vinylstore.productservice.dto.VinylDTO;
import ru.solomatin.vinylstore.productservice.model.Vinyl;
import ru.solomatin.vinylstore.productservice.repository.VinylRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private VinylRepository vinylRepository;

    @InjectMocks
    private ProductService productService;

    private VinylDTO createTestDTO() {
        return new VinylDTO(
                "Dark Side of the Moon",
                "Pink Floyd",
                "Harvest",
                "Progressive rock masterpiece",
                new BigDecimal("29.99"),
                1,
                "Speak to Me, Breathe, Time"
        );
    }

    private Vinyl createTestVinyl(String id) {
        return new Vinyl(
                id,
                "Dark Side of the Moon",
                "Progressive rock masterpiece",
                new BigDecimal("29.99"),
                1,
                "Pink Floyd",
                "Harvest",
                "Speak to Me, Breathe, Time"
        );
    }

    @Test
    void saveNewVinyl_shouldSaveWithGeneratedUUID() {
        VinylDTO dto = createTestDTO();

        productService.saveNewVinyl(dto);

        ArgumentCaptor<Vinyl> captor = ArgumentCaptor.forClass(Vinyl.class);
        verify(vinylRepository).save(captor.capture());

        Vinyl saved = captor.getValue();
        assertNotNull(saved.getId());
        assertEquals(dto.name(), saved.getName());
        assertEquals(dto.musician(), saved.getMusician());
        assertEquals(dto.label(), saved.getLabel());
        assertEquals(dto.description(), saved.getDescription());
        assertEquals(dto.price(), saved.getPrice());
        assertEquals(dto.countOfDiscs(), saved.getCountOfDiscs());
        assertEquals(dto.listOfSongs(), saved.getListOfSongs());
    }

    @Test
    void getAllVinyl_shouldReturnAllProducts() {
        List<Vinyl> expected = List.of(
                createTestVinyl("id-1"),
                createTestVinyl("id-2")
        );
        when(vinylRepository.findAll()).thenReturn(expected);

        List<Vinyl> result = productService.getAllVinyl();

        assertEquals(2, result.size());
        verify(vinylRepository).findAll();
    }

    @Test
    void getAllVinyl_shouldReturnEmptyListWhenNoProducts() {
        when(vinylRepository.findAll()).thenReturn(List.of());

        List<Vinyl> result = productService.getAllVinyl();

        assertTrue(result.isEmpty());
    }

    @Test
    void getVinylById_shouldReturnVinylWhenFound() {
        Vinyl expected = createTestVinyl("test-id");
        when(vinylRepository.findById("test-id")).thenReturn(Optional.of(expected));

        Vinyl result = productService.getVinylById("test-id");

        assertEquals(expected, result);
        assertEquals("Dark Side of the Moon", result.getName());
    }

    @Test
    void getVinylById_shouldThrowWhenNotFound() {
        when(vinylRepository.findById("missing-id")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.getVinylById("missing-id")
        );
        assertTrue(exception.getMessage().contains("missing-id"));
    }

    @Test
    void deleteVinylById_shouldDeleteWhenExists() {
        when(vinylRepository.existsById("test-id")).thenReturn(true);

        productService.deleteVinylById("test-id");

        verify(vinylRepository).deleteById("test-id");
    }

    @Test
    void deleteVinylById_shouldThrowWhenNotExists() {
        when(vinylRepository.existsById("missing-id")).thenReturn(false);

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.deleteVinylById("missing-id")
        );
        assertTrue(exception.getMessage().contains("missing-id"));
        verify(vinylRepository, never()).deleteById(any());
    }
}
