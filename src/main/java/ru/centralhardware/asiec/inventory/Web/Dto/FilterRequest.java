package ru.centralhardware.asiec.inventory.Web.Dto;

public record FilterRequest (
        String attributeName,
        String operation,
        String value
){ }
