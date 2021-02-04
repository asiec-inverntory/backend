package ru.centralhardware.asiec.inventory.Entity;

import ru.centralhardware.asiec.inventory.Entity.Enum.EventType;

import javax.persistence.*;

@Table(name = "event")
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private EventType type;

}
