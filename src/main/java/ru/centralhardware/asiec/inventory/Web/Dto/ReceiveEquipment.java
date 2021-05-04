package ru.centralhardware.asiec.inventory.Web.Dto;

import java.util.List;

public record ReceiveEquipment (
        String type,
        String serialCode,
        List<Pair> properties
) { }
