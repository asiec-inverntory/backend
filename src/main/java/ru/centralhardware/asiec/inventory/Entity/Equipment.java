package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "equipment")
@Entity
@Getter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String inventory_code;
    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "child_equipment")
    private Equipment childEquipment;
    @Column(nullable = false)
    private boolean isAtomic;
    @Column(length = 50, nullable = false)
    private String appointment;
    @ManyToOne
    @JoinColumn(name = "position", nullable = false)
    private Position position;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "characteristic2equipment",
            joinColumns = { @JoinColumn(name = "equipment_id") },
            inverseJoinColumns = { @JoinColumn(name = "characteristic_id") }
    )
    private Set<Characteristic> characteristics = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "responsible")
    private InventoryUser responsible;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="created_by")
    @Setter
    private InventoryUser createdBy;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="updated_by")
    @Setter
    private InventoryUser updatedBy;

    @OneToMany(mappedBy = "childEquipment")
    private Set<Equipment> childEquipments = new HashSet<>();

}
