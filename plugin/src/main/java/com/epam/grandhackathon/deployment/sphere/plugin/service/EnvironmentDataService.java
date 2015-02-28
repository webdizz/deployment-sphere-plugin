package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentData;

import java.util.List;

public interface EnvironmentDataService {

    List<EnvironmentData> getAllEnvironments();

    EnvironmentData getEnvironment(String title);
}
