package ru.centralhardware.asiec.inventory.Security.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class JwtRefreshToken {

    @Getter
    @Setter
    private String token;

}
