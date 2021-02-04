package ru.centralhardware.asiec.inventory.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "characteristic")
@Entity
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "attribute", nullable = false)
    private Attribute attribute;
    @Column(nullable = false)
    private String value;
    @ManyToOne
    @JoinColumn(name = "unit")
    private Unit unit;
    @ManyToMany(mappedBy = "characteristics")
    private Set<Equipment> equipments = new HashSet<>();


}
