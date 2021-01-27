package ru.centralhardware.asiec.inventory.Entity;

import javax.persistence.*;

@Table(name = "inventory_users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
