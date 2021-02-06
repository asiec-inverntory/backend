package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "repair")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Repair implements Deletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

}
