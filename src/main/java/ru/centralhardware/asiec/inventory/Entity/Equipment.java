package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentVariant;

import javax.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "type")
    private EquipmentType equipmentType;
    @Column(length = 50, nullable = false)
    private String inventoryCode;
    private String serialCode;
    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "parent_equipment")
    private Equipment parentEquipment;
    @Column(nullable = false)
    private boolean isAtomic;
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
    @Enumerated(EnumType.STRING)
    private EquipmentVariant equipmentVariant;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "parentEquipment")
    private Set<Equipment> parentEquipments = new HashSet<>();

}
