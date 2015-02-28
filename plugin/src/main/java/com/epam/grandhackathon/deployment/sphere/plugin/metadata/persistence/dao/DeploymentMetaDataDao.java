package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Deployment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.DeploymentPk;

@Log
public class DeploymentMetaDataDao extends GenericDao {
    public void save(final DeploymentMetaData deploymentMetaData) {
        Deployment deployment = getModelMapper().map(deploymentMetaData, Deployment.class);
        getEntityManager().persist(deployment);
    }

    public DeploymentMetaData find(final String applicationName, final String buildVersion) {
        Deployment foundDeployment = getEntityManager().find(Deployment.class, new DeploymentPk(applicationName, buildVersion));
        return getModelMapper().map(foundDeployment, DeploymentMetaData.class);
    }
}
