package ru.centralhardware.asiec.inventory.support;

import com.playtika.test.common.properties.CommonContainerProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties
public class PostgreSQLProperties extends CommonContainerProperties {
    static final String BEAN_NAME_EMBEDDED_POSTGRESQL = "embeddedPostgreSql";
    String dockerImage = "postgres:latest";

    String user = "postgresql";
    String password = "letmein";
    String database = "test_db";
    String startupLogCheckRegex = ".*database system is ready to accept connections.*";


}
