package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.BuildMetadataDao;
import com.google.common.collect.Lists;
import jenkins.model.Jenkins;

import javax.inject.Inject;
import java.util.List;


public class EnvironmentDataService {

    @Inject
    private BuildMetadataDao buildMetadataDao;

    public EnvironmentDataService () {
        Jenkins.getInstance().getInjector().injectMembers(this);

    }

    public List<EnvironmentMetaData> getAllEnvironments () {
        return Lists.newArrayList(new EnvironmentMetaData("Production"), new EnvironmentMetaData("QA"), new EnvironmentMetaData(
                "Staging"));
    }


    public EnvironmentMetaData getEnvironment (final String title) {
        return null;
    }
}
