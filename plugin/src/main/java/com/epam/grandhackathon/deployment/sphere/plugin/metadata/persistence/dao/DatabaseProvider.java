package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import javax.sql.DataSource;

import jenkins.model.Jenkins;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;


public class DatabaseProvider {

    private static final DataSource DATA_SOURCE = JdbcConnectionPool
            .create("jdbc:h2:" + Jenkins.getInstance().getPluginManager().rootDir.getAbsolutePath() + "/../data-deployment-sphere",
                    "username",
                    "password");
    private static final DBI DBI = new DBI(DATA_SOURCE);

    static {
    	Handle handle = DBI.open();
        handle.execute("CREATE TABLE IF NOT EXISTS BUILDS (application_name varchar(255) NOT NULL, build_version varchar(255) NOT NULL, build_url varchar(255) NOT NULL, build_number long, built_at long, PRIMARY KEY(application_name, build_version))");

        handle.execute("CREATE TABLE IF NOT EXISTS DEPLOYMENTS (key long auto_increment, application_name varchar(255) NOT NULL, build_version varchar(255) NOT NULL, environment_key varchar(255) NOT NULL, deployed_at long, PRIMARY KEY(key))");

        handle.execute("CREATE TABLE IF NOT EXISTS ENVIRONMENTS (key varchar(255) NOT NULL primary key, title varchar(255))");
        
        handle.execute("CREATE TABLE IF NOT EXISTS APPLICATIONS (applicationName varchar(255) NOT NULL primary key)");

        handle.close();
    }

    public DBI database() {
        return DBI;
    }
}
