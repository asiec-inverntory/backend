package ru.centralhardware.asiec.inventory.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "unit")
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String unit;
    private String description;
    private boolean isDeleted;


    @OneToMany(mappedBy = "unit")
    private Set<Characteristic> characteristics = new HashSet<>();
}
