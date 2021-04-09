package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "room")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Room implements Deletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private int number;
    @Column(nullable = false)
    private int flour;
    private String description;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "building", nullable = false)
    private Building building;
    @ManyToOne
    @JoinColumn(name = "responsible")
    private InventoryUser responsible;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "room")
    private final Set<Equipment> equipments = new HashSet<>();

}
