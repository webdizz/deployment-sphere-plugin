package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import org.skife.jdbi.v2.DBI;

import com.epam.grandhackathon.deployment.sphere.plugin.PluginInjector;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DatabaseProvider {
    
	@Inject
	private DataSourceProvider dataSourceProvider;
	
	@Inject
	private DatabaseMigrator databaseMigrator;
	
	private DBI dbi;

	public DatabaseProvider() {
		PluginInjector.injectMembers(this);
		if (null != dataSourceProvider){
			this.dbi = new DBI(dataSourceProvider.get());
			databaseMigrator.migrate(dataSourceProvider.get());
		}
	}

	public DBI database() {
		return dbi;
	}
}
