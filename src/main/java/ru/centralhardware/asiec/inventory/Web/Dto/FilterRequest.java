package ru.centralhardware.asiec.inventory.Web.Dto;

import ru.centralhardware.asiec.inventory.Filter.ValueType;

public record FilterRequest (
        ValueType type,
        String equipmentKey,
        String attributeName,
        String operation,
        String value
){ }
