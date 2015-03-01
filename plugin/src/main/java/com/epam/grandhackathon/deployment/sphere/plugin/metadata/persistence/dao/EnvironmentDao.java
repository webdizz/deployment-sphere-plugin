package com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao;

import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.BuildMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.EnvironmentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Deployment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.domain.Environment;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.DeploymentQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.query.EnvironmentQuery;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.util.DateFormatUtil;
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
            for (Environment build : builds) {
                EnvironmentMetaData environmentMetaData = new EnvironmentMetaData(build.getTitle());
                loadDeployInfo(handle, build, environmentMetaData);

                environmentMetaData.setIdentity(build.getKey());
                environmentMetaDatas.add(environmentMetaData);

            }
            log.fine(format("There are builds buildNumber in database '%s'", builds.size()));
        }

        return environmentMetaDatas;
    }

    private void loadDeployInfo (final Handle handle, final Environment build,
            final EnvironmentMetaData environmentMetaData) {
        DeploymentQuery deploymentQuery = handle.attach(DeploymentQuery.class);
        List<Deployment> deploymentList = deploymentQuery.find(build.getKey());
        for (Deployment deployment : deploymentList) {
            DeploymentMetaData prodDeploy = new DeploymentMetaData();
            prodDeploy.setBuildVersion(deployment.getBuild().getBuildVersion());
            prodDeploy.setDeployedAt(DateFormatUtil.formatDate(deployment.getDeployedAt()));
            prodDeploy.setApplicationName(deployment.getBuild().getApplicationName());

            BuildMetaData cqBuild = new BuildMetaData();
            cqBuild.setApplicationName(deployment.getBuild().getApplicationName());
            cqBuild.setNumber(deployment.getBuild().getBuildNumber());
            cqBuild.setBuildVersion(deployment.getBuild().getBuildVersion());
            cqBuild.setBuiltAt(DateFormatUtil.formatDate(deployment.getBuild().getBuiltAt()));
            cqBuild.setJobName(deployment.getBuild().getApplicationName());

            prodDeploy.setBuild(cqBuild);
            environmentMetaData.getDeployments().add(prodDeploy);
        }
    }


}
