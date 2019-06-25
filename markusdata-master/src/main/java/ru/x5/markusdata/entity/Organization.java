package ru.x5.markusdata.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Organization {
    @Id
    private Long id;
    private String mdmId;
    private String name;
    private String inn;
}
