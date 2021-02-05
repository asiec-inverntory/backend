package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "unit")
@Entity
@Getter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String unit;
    private String description;
    @Column(nullable = false)
    private boolean isDeleted = false;


    @OneToMany(mappedBy = "unit")
    private Set<Characteristic> characteristics = new HashSet<>();
}
