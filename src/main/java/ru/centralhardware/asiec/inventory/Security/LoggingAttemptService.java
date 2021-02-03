package ru.centralhardware.asiec.inventory.Security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.centralhardware.asiec.inventory.Config;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Slf4j
public class LoggingAttemptService {

    private final Config config;
    private LoadingCache<String, Integer> attemptsCache;

    public LoggingAttemptService(Config config) {
        this.config = config;
    }

    /**
     * build attemptsCache
     */
    @PostConstruct
    public void init(){
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(Duration.ofMinutes(config.getPreventBruteForceMinutes())).build(new CacheLoader<>() {
            @Override
            public Integer load(@NotNull String key) {
                return 0;
            }
        });
    }

    /**
     * invalidate cache when client getting token successfully
     * @param ip client ip address
     */
    public void loginSucceeded(String ip) {
        log.info("invalidate cache for " + ip);
        attemptsCache.invalidate(ip);
    }

    /**
     * increase the number of login attempts
     * @param ip client ip address
     */
    public void loginFailed(String ip) {
        log.info("login failed: " + ip);
        int attempts;
        try {
            attempts = attemptsCache.get(ip);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(ip, attempts);
    }

    /**
     * check if this ip is blocked
     * @param ip client ip address
     * @return true if ip blocked
     */
    public boolean isBlocked(String ip) {
        try {
            return attemptsCache.get(ip) >= config.getMaxLoginAttempt();
        } catch (ExecutionException e) {
            return false;
        }
    }

}
