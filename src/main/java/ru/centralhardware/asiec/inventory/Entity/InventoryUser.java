package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "inventory_users")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class InventoryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false, unique = true)
    private String username;
    @Column(length = 128, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String surname;
    @Column(name = "last_name", length = 30)
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "last_login")
    private Date lastLogin;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="created_by", nullable = false)
    private InventoryUser createdBy                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  ;

    @OneToMany(mappedBy="createdBy")
    private Set<InventoryUser> createdInventoryUsers = new HashSet<>();

    @OneToMany(mappedBy="createdBy")
    private Set<Building> createdBuildings = new HashSet<>();
    @OneToMany(mappedBy="updatedBy")
    private Set<Building> updatedBuildings = new HashSet<>();

    @OneToMany(mappedBy="createdBy")
    private Set<Room> createdRooms = new HashSet<>();
    @OneToMany(mappedBy="updatedBy")
    private Set<Room> updatedRooms = new HashSet<>();

    @OneToMany(mappedBy="createdBy")
    private Set<Position> createdPositions = new HashSet<>();
    @OneToMany(mappedBy="updatedBy")
    private Set<Position> updatedPositions = new HashSet<>();

    @OneToMany(mappedBy = "responsible")
    private Set<Equipment> equipment = new HashSet<>();

    @OneToMany(mappedBy = "responsible")
    private Set<Room> rooms = new HashSet<>();
}