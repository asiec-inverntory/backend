package ru.centralhardware.asiec.inventory.Web.Dto;

import java.util.List;

public record ReceiveEquipment (
        String type,
        String serial_code,
        List<Pair> properties
) { }
