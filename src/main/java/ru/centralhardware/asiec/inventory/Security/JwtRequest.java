package ru.centralhardware.asiec.inventory.Security;

import lombok.Getter;
import lombok.Setter;

public class JwtRequest {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
}
