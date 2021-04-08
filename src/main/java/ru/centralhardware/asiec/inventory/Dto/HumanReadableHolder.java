package ru.centralhardware.asiec.inventory.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HumanReadableHolder {

    @Getter
    private final int id;
    @Getter
    private final String humanReadable;

}
