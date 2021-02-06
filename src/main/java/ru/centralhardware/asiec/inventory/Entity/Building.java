package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "building")
@Entity
@Getter
@Setter
public class Building implements Deletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, nullable = false)
    private String address;
    @Column(length = 50, nullable = false)
    private String building_identifier;

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


    @OneToMany(mappedBy = "building")
    private Set<Room> rooms = new HashSet<>();
}
