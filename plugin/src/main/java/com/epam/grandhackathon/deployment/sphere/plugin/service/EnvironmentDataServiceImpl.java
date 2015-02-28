package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentData;
import com.google.common.collect.Lists;
import hudson.Extension;

import java.util.List;

@Extension
public class EnvironmentDataServiceImpl implements EnvironmentDataService {

    @Override
    public List<EnvironmentData> getAllEnvironments() {
        return Lists.newArrayList(new EnvironmentData("Production"), new EnvironmentData("QA"), new EnvironmentData(
                "Staging"));
    }

    @Override
    public EnvironmentData getEnvironment(final String title) {
        return null;
    }
}
