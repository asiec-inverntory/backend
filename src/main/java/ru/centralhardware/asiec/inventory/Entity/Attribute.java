package ru.centralhardware.asiec.inventory.Entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "attribute")
@Entity
@Getter
public class Attribute implements Deletable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AttributeType type;
    private Integer minimum;
    private Integer maximum;
    @Column(nullable = false)
    private String attribute;
    private String description;
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "attribute")
    private final Set<Characteristic> characteristics = new HashSet<>();
}
