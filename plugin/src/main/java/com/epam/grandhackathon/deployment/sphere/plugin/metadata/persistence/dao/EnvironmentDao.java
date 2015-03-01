package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Build;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Deployment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.BuildQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.DeploymentQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.EnvironmentQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.utils.DateFormatUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.extern.java.Log;
import org.skife.jdbi.v2.Handle;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Log
public class EnvironmentDao extends GenericDao {


    public Collection<EnvironmentMetaData> findAll () {

        List<EnvironmentMetaData> environmentMetaDatas = Lists.newArrayList();

        try (Handle handle = database().open()) {
            EnvironmentQuery query = handle.attach(EnvironmentQuery.class);
            List<Environment> builds = query.all();
            for (Environment environment : builds) {
                EnvironmentMetaData environmentMetaData = new EnvironmentMetaData(environment.getTitle());
                loadDeployInfo(handle, environment, environmentMetaData);

                environmentMetaData.setIdentity(environment.getKey());
                environmentMetaDatas.add(environmentMetaData);

            }
            log.fine(format("There are builds buildNumber in database '%s'", builds.size()));
        }

        return environmentMetaDatas;
    }

    private void loadDeployInfo (final Handle handle, final Environment environment,
            final EnvironmentMetaData environmentMetaData) {
        final DeploymentQuery deploymentQuery = handle.attach(DeploymentQuery.class);
        List<Deployment> deploymentList = deploymentQuery.find(environment.getKey());
        Deployment deployment = Iterables.getFirst(deploymentList, null);
        if(deployment != null) {
            final DeploymentMetaData prodDeploy = new DeploymentMetaData();
            prodDeploy.setBuildVersion(deployment.getBuild().getBuildVersion());
            prodDeploy.setDeployedAt(DateFormatUtil.formatDate(deployment.getDeployedAt()));
            prodDeploy.setApplicationName(deployment.getBuild().getApplicationName());

            final BuildQuery query = handle.attach(BuildQuery.class);
            Build foundBuild = query.find(deployment.getBuild().getBuildVersion());

            final BuildMetaData cqBuild = new BuildMetaData();
            cqBuild.setApplicationName(foundBuild.getApplicationName());
            cqBuild.setNumber(foundBuild.getBuildNumber());
            cqBuild.setBuildVersion(foundBuild.getBuildVersion());
            cqBuild.setBuiltAt(DateFormatUtil.formatDate(foundBuild.getBuiltAt()));
            //Added Job Name
            cqBuild.setJobName(foundBuild.getApplicationName());

            prodDeploy.setBuild(cqBuild);
            environmentMetaData.getDeployments().add(prodDeploy);
        }
    }


}
