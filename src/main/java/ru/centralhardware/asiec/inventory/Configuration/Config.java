package ru.centralhardware.asiec.inventory.Configuration;

import org.springframework.stereotype.Component;

@Component
public class Config {

    /**
     * randomized string used for generate JWT
     */
    public final String secret = System.getenv("SECRET");
    /**
     * the user will be blocked for the specified number of minutes after reaching
     * the limit of unsuccessful login attempts
     */
    public final int preventBruteforceDelay = Integer.parseInt(System.getenv("PREVENT_BRUTEFORCE_DELAY"));
    /**
     * count of logging attempt before block
     */
    public final int maxLoginAttempt = Integer.parseInt(System.getenv("MAX_LOGIN_ATTEMPT"));
    public final String apiVersion = System.getenv("API_VERSION");
    public final boolean enableAuth = Boolean.parseBoolean(System.getenv("ENABLE_AUTH"));

}
