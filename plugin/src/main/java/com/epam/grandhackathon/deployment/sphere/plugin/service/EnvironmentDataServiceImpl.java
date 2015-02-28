package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.google.common.collect.Lists;
import hudson.Extension;

import java.util.List;

@Extension
public class EnvironmentDataServiceImpl implements EnvironmentDataService {

    @Override
    public List<EnvironmentMetaData> getAllEnvironments() {
        return Lists.newArrayList(new EnvironmentMetaData("Production"), new EnvironmentMetaData("QA"), new EnvironmentMetaData(
                "Staging"));
    }

    @Override
    public EnvironmentMetaData getEnvironment(final String title) {
        return null;
    }
}
