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
        handle.execute("create table deployments (identity long primary key, applicationName varchar(255))");
        handle.close();
    }

    public DBI database() {
        return DBI;
    }
}
