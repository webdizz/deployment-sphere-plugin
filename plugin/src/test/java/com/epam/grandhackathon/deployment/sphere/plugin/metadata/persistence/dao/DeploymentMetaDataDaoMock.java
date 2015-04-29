package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import java.util.List;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;

public class DeploymentMetaDataDaoMock extends DeploymentMetaDataDao {

    public void save(final DeploymentMetaData deploymentMetaData) {
        return;
    }

    public DeploymentMetaData find(final String applicationName, final String buildVersion, final String environmentKey) {
        return null;
    }

    public List<DeploymentMetaData> find(final String applicationName, final String environmentKey) {
        return null;
    }

}
