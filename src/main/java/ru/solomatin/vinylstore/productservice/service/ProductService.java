package ru.solomatin.vinylstore.productservice.service;

import org.springframework.stereotype.Service;
import ru.solomatin.vinylstore.productservice.dto.VinylDTO;
import ru.solomatin.vinylstore.productservice.model.Vinyl;
import ru.solomatin.vinylstore.productservice.repository.VinylRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Logger;


@Service
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    private final VinylRepository vinylRepository;

    public ProductService(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public void saveNewVinyl(VinylDTO vinylDTO) {
        Vinyl product = new Vinyl(
                UUID.randomUUID().toString(),
                vinylDTO.name(),
                vinylDTO.description(),
                vinylDTO.price(),
                vinylDTO.countOfDiscs(),
                vinylDTO.musician(),
                vinylDTO.label(),
                vinylDTO.listOfSongs()
        );

        vinylRepository.save(product);
        logger.info(String.format("Saved product '%s' with id '%s'", product.getName(), product.getId()));
    }

    public List<Vinyl> getAllVinyl() {
        return (List<Vinyl>) vinylRepository.findAll();
    }

    public Vinyl getVinylById(String id) {
        return vinylRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vinyl not found with id: " + id));
    }

    public void deleteVinylById(String id) {
        if (!vinylRepository.existsById(id)) {
            throw new NoSuchElementException("Vinyl not found with id: " + id);
        }
        vinylRepository.deleteById(id);
        logger.info(String.format("Deleted product with id '%s'", id));
    }

}
