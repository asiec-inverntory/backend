package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "inventory_users")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Accessors(chain = true)
public class InventoryUser implements Deletable{

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


    @OneToMany(mappedBy = "responsible")
    private Set<Equipment> equipment = new HashSet<>();

    @OneToMany(mappedBy = "responsible")
    private Set<Room> rooms = new HashSet<>();
}