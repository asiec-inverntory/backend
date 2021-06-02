package ru.centralhardware.asiec.inventory.Web.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AttributeDto(
    String id,
    AttributeType valueType,
    Integer minimum,
    Integer maximum,
    String humanReadable,
    List<String> values,
    List<String> responsible
) { }
