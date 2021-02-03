package ru.centralhardware.asiec.inventory.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "equipment")
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String inventory_code;
    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "child_equipment")
    private Equipment childEquipment;
    private boolean isAtomic;
    private String appointment;
    @ManyToOne
    @JoinColumn(name = "position")
    private Position position;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "characteristic2equipment",
            joinColumns = { @JoinColumn(name = "equipment_id") },
            inverseJoinColumns = { @JoinColumn(name = "characteristic_id") }
    )
    private Set<Characteristic> characteristics = new HashSet<>();

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
    private User createdBy;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="updated_by")
    private User updatedBy;

    @OneToMany(mappedBy = "equipment")
    private Set<Equipment> childEquipments = new HashSet<>();

}
