package ru.centralhardware.asiec.inventory.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HumanReadableHolder {

    @Getter
    private int id;
    @Getter
    private String humanReadable;

}
