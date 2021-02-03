package ru.centralhardware.asiec.inventory.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "attribute")
@Entity
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String attribute;
    private String description;
    private boolean isDeleted;

    @OneToMany(mappedBy = "attribute")
    private Set<Characteristic> characteristics = new HashSet<>();
}
