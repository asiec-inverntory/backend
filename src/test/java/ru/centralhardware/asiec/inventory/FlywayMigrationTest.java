package ru.centralhardware.asiec.inventory;

import org.flywaydb.core.Flyway;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

public class FlywayMigrationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @Test
    public void testMigration() {
        Flyway.configure()
                .dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()).
                locations("classpath:/db/migration,classpath:/dev/db/migration")
                .load()
                .migrate();
    }

}
