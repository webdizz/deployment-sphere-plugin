package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class DatabaseProvider {

    private static final DataSource DATA_SOURCE = JdbcConnectionPool
            .create("jdbc:h2:.data-deployment-sphere",
                    "username",
                    "password");
    private static final DBI DBI = new DBI(DATA_SOURCE);

    static {
        Handle handle = DBI.open();
        handle.execute("CREATE TABLE IF NOT EXISTS BUILDS (application_name varchar(255) NOT NULL, build_version varchar(255) NOT NULL, build_number long, built_at long, PRIMARY KEY(application_name, build_version))");

        handle.execute("CREATE TABLE IF NOT EXISTS DEPLOYMENTS (identity long primary key, applicationName varchar(255))");
        handle.execute("CREATE TABLE IF NOT EXISTS ENVIRONMENTS (identity long primary key, title varchar(255))");

        handle.execute("INSERT INTO  ENVIRONMENTS (identity,title ) VALUES (1, 'ci')");
        handle.execute("INSERT INTO  ENVIRONMENTS (identity,title ) VALUES (2, 'qa')");
        handle.execute("INSERT INTO  ENVIRONMENTS (identity,title ) VALUES (3, 'st')");
        handle.execute("INSERT INTO  ENVIRONMENTS (identity,title ) VALUES (4, 'pre-prod')");
        handle.execute("INSERT INTO  ENVIRONMENTS (identity,title ) VALUES (5, 'prod')");

        handle.close();
    }

    public DBI database() {
        return DBI;
    }
}
