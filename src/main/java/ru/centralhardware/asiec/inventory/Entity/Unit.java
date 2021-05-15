package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "unit")
@Entity
@Getter
public class Unit implements Deletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String unit;
    private String description;
    @Column(nullable = false)
    private final boolean isDeleted = false;


    @OneToMany(mappedBy = "unit")
    private final Set<Characteristic> characteristics = new HashSet<>();
}
