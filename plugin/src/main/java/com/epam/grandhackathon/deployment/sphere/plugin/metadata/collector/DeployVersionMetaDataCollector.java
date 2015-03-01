package com.epam.grandhackathon.deployment.sphere.plugin.metadata.collector;

import com.epam.grandhackathon.deployment.sphere.plugin.TempConstants;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.model.DeploymentMetaData;
import com.epam.grandhackathon.deployment.sphere.plugin.metadata.persistence.dao.DeploymentMetaDataDao;
import com.epam.grandhackathon.deployment.sphere.plugin.utils.DateFormatUtil;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;
import lombok.extern.java.Log;
import org.joda.time.DateTime;

import javax.inject.Inject;

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
        String buildVersion = "0.0." + (build.getNumber());
        String applicationName = TempConstants.APP_NAME;

        // persist data
        DeploymentMetaData deploymentMetaData = new DeploymentMetaData();
        deploymentMetaData.setApplicationName(applicationName);
        deploymentMetaData.setDeployedAt(DateFormatUtil.formatDate(new DateTime(build.due())));
        deploymentMetaData.setBuildVersion(buildVersion);
        deploymentMetaData.setEnvironmentKey("2");
        deploymentMetaDataDao.save(deploymentMetaData);
        return deploymentMetaData;
    }

}
