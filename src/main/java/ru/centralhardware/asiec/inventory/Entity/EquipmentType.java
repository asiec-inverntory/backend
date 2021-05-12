package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String typeName;
    private String humanReadable;

    @OneToMany(mappedBy = "equipmentType")
    private Set<Equipment> equipment;

}
