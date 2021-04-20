package ru.centralhardware.asiec.inventory.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AttributeDto(
    String id,
    AttributeType valueType,
    Integer minimum,
    Integer maximum,
    List<?> values
) { }
