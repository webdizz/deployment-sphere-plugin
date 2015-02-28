package com.epam.grandhackathon.deployment.sphere.plugin.service;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;

public interface BuildDataService {

    BuildMetaData getBuild(String number);
}
