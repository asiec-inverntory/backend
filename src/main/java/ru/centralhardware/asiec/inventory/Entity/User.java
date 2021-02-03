package ru.centralhardware.asiec.inventory.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "inventory_users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String surname;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
    private User created_by;

    @OneToMany(mappedBy="created_by")
    private Set<User> createdUsers = new HashSet<>();

    @OneToMany(mappedBy="created_by")
    private Set<Building> createdBuildings = new HashSet<>();
    @OneToMany(mappedBy="updated_by")
    private Set<Building> updatedBuildings = new HashSet<>();

    @OneToMany(mappedBy="created_by")
    private Set<Room> createdRooms = new HashSet<>();
    @OneToMany(mappedBy="updated_by")
    private Set<Room> updatedRooms = new HashSet<>();

    @OneToMany(mappedBy="created_by")
    private Set<Position> createdPositions = new HashSet<>();
    @OneToMany(mappedBy="updated_by")
    private Set<Position> updatedPositions = new HashSet<>();

}