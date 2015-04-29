package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.util.Collection;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;

public class EnvironmentDaoMock extends EnvironmentDao {

    public Environment find(final String evnKey) {
    	Environment env = new Environment();
    	env.setKey("env_key");
        return env;
    }

    public Collection<EnvironmentMetaData> findAll() {
    	return null;
    }

}
