package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import javax.inject.Inject;

import org.joda.time.DateTime;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;

import com.epam.grandhackathon.deployment.sphere.plugin.TempConstants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDao;

@Log
public final class DeployVersionMetaDataCollector implements Collector<DeploymentMetaData> {

    @Inject
    private DeploymentMetaDataDao deploymentMetaDataDao;

    public DeployVersionMetaDataCollector() {
        Jenkins.getInstance().getInjector().injectMembers(this);
    }

    @Override
    public DeploymentMetaData collect(final AbstractBuild<?, ?> build, final TaskListener taskListener) {
        // next variables should be resolved from context
        String buildVersion = "0.0.30";
        String applicationName = TempConstants.APP_NAME;

        // persist data
        DeploymentMetaData deploymentMetaData = new DeploymentMetaData();
        deploymentMetaData.setApplicationName(applicationName);
        deploymentMetaData.setDeployedAt(new DateTime(build.due()));
        deploymentMetaData.setBuildVersion(buildVersion);
        deploymentMetaDataDao.save(deploymentMetaData);
        return deploymentMetaData;
    }

}
