package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;

import java.util.List;

public interface EnvironmentDataService {

    List<EnvironmentMetaData> getAllEnvironments();

    EnvironmentMetaData getEnvironment(String title);
}
