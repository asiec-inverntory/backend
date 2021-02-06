package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "equipment")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
public class Equipment implements Deletable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String inventory_code;
    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "parent_equipment")
    private Equipment parentEquipment;
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
    @Column(name = "created_at", updatable = false)
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

    @OneToMany(mappedBy = "parentEquipment")
    private Set<Equipment> parentEquipments = new HashSet<>();

}
