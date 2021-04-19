package ru.centralhardware.asiec.inventory.support;

import com.google.common.base.Strings;
import com.playtika.test.common.spring.DockerPresenceBootstrapConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.util.LinkedHashMap;

import static com.playtika.test.common.utils.ContainerUtils.configureCommonsAndStart;
import static ru.centralhardware.asiec.inventory.support.PostgreSQLProperties.BEAN_NAME_EMBEDDED_POSTGRESQL;

@Slf4j
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@AutoConfigureAfter(DockerPresenceBootstrapConfiguration.class)
@ConditionalOnProperty(name = "embedded.postgresql.enabled", matchIfMissing = true)
@EnableConfigurationProperties(PostgreSQLProperties.class)
public class EmbeddedPostgreSQLBootstrapConfiguration {

    @Bean(name = BEAN_NAME_EMBEDDED_POSTGRESQL, destroyMethod = "stop")
    public ConcretePostgreSQLContainer postgresql(ConfigurableEnvironment environment,
                                                  PostgreSQLProperties properties) {
        log.info("Starting postgresql server. Docker image: {}", properties.dockerImage);

        ConcretePostgreSQLContainer postgresql =
                new ConcretePostgreSQLContainer(DockerImageName.parse(properties.dockerImage)
                        .asCompatibleSubstituteFor("postgres"))
                        .withUsername(properties.getUser())
                        .withPassword(properties.getPassword())
                        .withDatabaseName(properties.getDatabase());

        String startupLogCheckRegex = properties.getStartupLogCheckRegex();
        if (!Strings.isNullOrEmpty(startupLogCheckRegex)) {
            WaitStrategy waitStrategy = new LogMessageWaitStrategy()
                    .withRegEx(startupLogCheckRegex)
                    .withTimes(2);
            postgresql.setWaitStrategy(waitStrategy);
        }

        postgresql = (ConcretePostgreSQLContainer) configureCommonsAndStart(postgresql, properties, log);
        registerPostgresqlEnvironment(postgresql, environment, properties);
        return postgresql;
    }

    private void registerPostgresqlEnvironment(ConcretePostgreSQLContainer postgresql,
                                               ConfigurableEnvironment environment,
                                               PostgreSQLProperties properties) {
        Integer mappedPort = 49189;
        String host = postgresql.getContainerIpAddress();

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("embedded.postgresql.port", mappedPort);
        map.put("embedded.postgresql.host", host);
        map.put("embedded.postgresql.schema", properties.getDatabase());
        map.put("embedded.postgresql.user", properties.getUser());
        map.put("embedded.postgresql.password", properties.getPassword());

        String jdbcURL = "jdbc:postgresql://{}:{}/{}";
        log.info("Started postgresql server. Connection details: {}, " +
                "JDBC connection url: " + jdbcURL, map, host, mappedPort, properties.getDatabase());

        MapPropertySource propertySource = new MapPropertySource("embeddedPostgreInfo", map);
        environment.getPropertySources().addFirst(propertySource);
    }

    private static class ConcretePostgreSQLContainer extends PostgreSQLContainer<ConcretePostgreSQLContainer> {
        public ConcretePostgreSQLContainer(DockerImageName dockerImageName) {
            super(dockerImageName);
        }
    }

}
