package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Deployment;

@Log
public class DeploymentMetaDataDao extends GenericDao {
    public void save(final DeploymentMetaData deploymentMetaData) {
//        Build build = findBuild(deploymentMetaData.getApplicationName(), deploymentMetaData.getBuildVersion());
//        Environment environment = findEnvironment(deploymentMetaData.getEnvironmentKey());

        Deployment deployment = getModelMapper().map(deploymentMetaData, Deployment.class);
//        deployment.setBuild(build);
//        deployment.setEnvironment(environment);
//        getEntityManager().persist(deployment);
    }

//    protected Environment findEnvironment(final String environmentKey) {
//        Environment environment = getEntityManager().find(Environment.class, environmentKey);
//        checkArgument(null != environment, format("Cannot find environment for given key %s", environmentKey));
//        return environment;
//    }

//    protected Build findBuild(final String applicationName, final String buildVersion) {
//        BuildPk buildPk = new BuildPk(applicationName, buildVersion);
//        TypedQuery<Build> query = getEntityManager().createQuery("SELECT b FROM Build AS b", Build.class);
//        List<Build> resultList = query.getResultList();
//        log.fine(format("There are builds buildNumber in database '%s'", resultList.size()));
//
//        Build build = getEntityManager().find(Build.class, buildPk);
//        checkArgument(null != build, format("Cannot find build for given application '%s' and build version '%s'", applicationName, buildVersion));
//        return build;
//    }

//    public DeploymentMetaData find(final String applicationName, final String buildVersion) {
//        Deployment foundDeployment = getEntityManager().find(Deployment.class, new DeploymentPk(applicationName, buildVersion));
//        return getModelMapper().map(foundDeployment, DeploymentMetaData.class);
//    }
}
