package ru.centralhardware.asiec.inventory.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "room")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int number;
    private int flour;
    private String description;
    private String appointment;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "building")
    private Building building;

    @Column(name = "is_deleted")
    private boolean isDeleted;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="created_by")
    private InventoryUser createdBy;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="updated_by")
    private InventoryUser updatedBy;

    @OneToMany(mappedBy = "room")
    private Set<Position> positions = new HashSet<>();
    @OneToMany(mappedBy = "room")
    private Set<Equipment> equipments = new HashSet<>();

}
