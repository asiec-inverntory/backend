package ru.centralhardware.asiec.inventory.Security.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class JwtResponse {
    @Setter
    @Getter
    private String token;
    @Setter
    @Getter
    private String refreshToken;
}
