package ru.centralhardware.asiec.inventory.Configuration;

import org.springframework.stereotype.Component;

@Component
public class Config {

    public final String secret = System.getenv("SECRET");
    public final int preventBruteforceDelay = Integer.parseInt(System.getenv("PREVENT_BRUTEFORCE_DELAY"));
    public final int maxLoginAttempt = Integer.parseInt(System.getenv("MAX_LOGIN_ATTEMPT"));
    public final String apiVersion = System.getenv("API_VERSION");

}
