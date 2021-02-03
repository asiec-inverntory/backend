package ru.centralhardware.asiec.inventory.Entity;

import javax.persistence.*;

@Table(name = "inventory_order")
@Entity
public class InventoryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
