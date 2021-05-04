package ru.centralhardware.asiec.inventory.Web.Dto;

public record FilterRequest (
        ValueType type,
        String equipmentKey,
        String attributeName,
        String operation,
        String value
){ }
