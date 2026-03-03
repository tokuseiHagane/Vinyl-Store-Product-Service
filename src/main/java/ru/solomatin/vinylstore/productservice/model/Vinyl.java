package ru.solomatin.vinylstore.productservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "vinyl_t")
public class Vinyl {
    @Id
    private String id;

    @Column(name = "name_of_album", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "count_of_discs", nullable = false)
    private Integer countOfDiscs;

    @Column(name = "musician", nullable = false)
    private String musician;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "list_of_songs", nullable = false)
    private String listOfSongs;

    public Vinyl() {
    }

    public Vinyl(String id,
                 String name,
                 String description,
                 BigDecimal price,
                 Integer countOfDiscs,
                 String musician,
                 String label,
                 String listOfSongs) {
        this.id = id;
        this.musician = musician;
        this.label = label;
        this.listOfSongs = listOfSongs;
        this.name = name;
        this.description = description;
        this.price = price;
        this.countOfDiscs = countOfDiscs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCountOfDiscs() {
        return countOfDiscs;
    }

    public void setCountOfDiscs(Integer countOfDiscs) {
        this.countOfDiscs = countOfDiscs;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getListOfSongs() {
        return listOfSongs;
    }

    public void setListOfSongs(String listOfSongs) {
        this.listOfSongs = listOfSongs;
    }
}
