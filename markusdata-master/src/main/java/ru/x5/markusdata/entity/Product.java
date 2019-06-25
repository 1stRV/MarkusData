package ru.x5.markusdata.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Product {
    @Id
    private Long id;
    private String pluId;
    private String gtin;

}
