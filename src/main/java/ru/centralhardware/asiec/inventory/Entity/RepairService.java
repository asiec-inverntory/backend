package ru.centralhardware.asiec.inventory.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "repair_service")
@Entity
public class RepairService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String telephone;

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

}