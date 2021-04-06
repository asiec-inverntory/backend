package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table(name = "repair_service")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RepairService implements Deletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String address;
    @Column(length = 11, nullable = false)
    private String telephone;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

}
