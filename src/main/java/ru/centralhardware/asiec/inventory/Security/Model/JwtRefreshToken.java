package ru.centralhardware.asiec.inventory.Security.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class JwtRefreshToken {

    @Getter
    @Setter
    private String token;

}
